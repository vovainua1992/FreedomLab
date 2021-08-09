package com.freedom.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.freedom.services.dommain.Image;
import com.freedom.services.dommain.Publish;
import com.freedom.services.dommain.User;
import com.freedom.services.repos.PublicationRepos;
import com.freedom.services.service.ImageService;
import com.freedom.services.service.PublicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;


/**
 * Контроллер публікацій.
 */
@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class PublicationController {
    private final ImageService imageService;
    private final PublicationRepos publicationRepos;
    private final PublicationService publicationService;

    /**
     * Всі публікації.
     *
     * @param model
     * @return Сторінка публікацій
     */
    @GetMapping()
    public String getAll(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                         Model model) {
        model.addAttribute("news", publicationRepos.findAllByActiveTrueAndTypeEqualsCustom(pageable));
        model.addAttribute("title", "Публікації");
        model.addAttribute("url","/news");
        Page p = publicationRepos.findAllByActiveTrueAndTypeEqualsCustom(pageable);
        return "publish/publishes_view";
    }

    /**
     * Всі публікації користувача.
     *
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/my")
    public String getMyAll(@AuthenticationPrincipal User user,
                           @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                           Model model) {
        model.addAttribute("news", publicationRepos.findAllByAuthor(user,pageable));
        model.addAttribute("title", "Мої публікації");
        model.addAttribute("url","/news/my");
        return "publish/publishes_view";
    }

    /**
     * Сторінка перегляду публікації.
     *
     * @param id -індифікатор публікації
     * @param model - контекст сторінки
     * @return
     */
    @GetMapping("{id}")
    public String getPublishViewById(@PathVariable String id,
                                     @AuthenticationPrincipal User user,
                                     Model model) {
        Publish publish = publicationRepos.findById(Long.parseLong(id));
        boolean isEdit = publish.isEdit(user);
        model.addAttribute("publish", publish);
        model.addAttribute("isEdit", isEdit);
        return "publish/publish_view";
    }

    /**
     * Створення публікації.
     *
     * @return
     */
    @GetMapping("/add")
    public String addPublish(@AuthenticationPrincipal User user,
                             Model model) {
        Publish publish = Publish.EMPTY(user);
        publicationRepos.save(publish);
        model.addAttribute("publish", publish);
        return "publish/create_publish";
    }

    /**
     * Редагування постеру публікації.
     *
     * @param user
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/{id}")
    public String edit(@AuthenticationPrincipal User user,
                       @PathVariable String id,
                       Model model) {
        Publish publish = publicationRepos.findById(Long.parseLong(id));
        if (!publish.isEdit(user))
           throw new AccessDeniedException("403 returned");
        model.addAttribute("publish", publish);
        return "publish/create_publish";
    }

    /**
     * Оновлення постеру новини
     *
     * @param id
     * @param title
     * @param imageTitle
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping("/update_poster")
    public String updatePoster(@RequestParam(name = "checkVisible", defaultValue = "") String visible,
                               @RequestParam("publishId") long id,
                               @RequestParam("title") String title,
                               @RequestParam("file") MultipartFile imageTitle,
                               Model model) throws IOException {
        Publish publish = publicationRepos.findById(id);
        publicationService.updatePoster(publish, visible, title);
        if (!imageTitle.isEmpty()) {
            Image image = imageService.saveImage(imageTitle);
            publish.setImage(image);
        }
        publicationRepos.save(publish);
        model.addAttribute("publish", publish);
        return "publish/create_publish";
    }

    /**
     * Перемикання активності(видимості публікації)
     *
     * @param id
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/activate/{id}")
    public String inverseActive(@PathVariable String id,
                                @AuthenticationPrincipal User user,
                                Model model) {
        Publish publish = publicationRepos.findById(Long.parseLong(id));
        boolean isEdit = publish.isEdit(user);
        if (isEdit) {
            publish.setActive(!publish.isActive());
            publicationRepos.save(publish);
        }
        model.addAttribute("publish", publish);
        model.addAttribute("isEdit", isEdit);
        return "publish_view";
    }

    /**
     * Видалення публікації.
     *
     * @param id
     * @param user
     * @return
     */
    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable String id,
                               @AuthenticationPrincipal User user,
                               RedirectAttributes attributes) {
        Publish publish = publicationRepos.findById(Long.parseLong(id));
        if (!publicationService.delete(publish, user)) {
            attributes.addFlashAttribute("dang", "У вас немає прав видалити публікацію");
            return new RedirectView("/news");
        } else
            attributes.addFlashAttribute("succ", "Пулікацію видаленно");
        if (publish.isAuthor(user))
            return new RedirectView("/news/my");
        else
            return new RedirectView("/news");
    }

    /**
     * Оновлення вмісту публікації (через редактор ContentTools)
     *
     * @param id
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateContent(@PathVariable String id,
                                @AuthenticationPrincipal User user,
                                @RequestBody String json) throws JsonProcessingException {
        Publish publish = publicationRepos.findById(Long.parseLong(id));
        if (publish.isEdit(user)) {
            publicationService.updateContent(json, publish);
            return "ok";
        } else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
    }
}
