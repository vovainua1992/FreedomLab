package com.freedom.web.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.freedom.services.dommain.Publish;
import com.freedom.services.dommain.User;
import com.freedom.services.dommain.dto.PublishDto;
import com.freedom.services.dommain.dto.PublishesFilterDto;
import com.freedom.services.repos.PublicationRepos;
import com.freedom.services.service.CategoryService;
import com.freedom.services.service.PublicationService;
import com.freedom.services.utils.RedirectUrlBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/publish")
@RequiredArgsConstructor
public class PublicationController {
    private final PublicationRepos publicationRepos;
    private final PublicationService publicationService;
    private final CategoryService categoryService;


//TODO add filters
    @GetMapping("/all")
    public String getAll(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                         @AuthenticationPrincipal User user,
                         Model model) {
        model.addAttribute("isMy",false);
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("publishes", publicationRepos.findAllByActiveTrueAndTypeCustom(pageable,user));
        model.addAttribute("title", "Публікації");
        model.addAttribute("url","/news");
        return "publish/publishes_view";
    }

    @GetMapping("/filter")
    public String getFilter(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                         @AuthenticationPrincipal User user,
                         Model model) {
        model.addAttribute("isMy",false);
        List<String> tags = new ArrayList<>();
        tags.add("freedom");
        tags.add("test");
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("publishes",
                publicationRepos.findByFilter(pageable,tags,tags.size()));
        model.addAttribute("title", "Публікації");
        model.addAttribute("url","/news");
        return "publish/publishes_view";
    }

    @PostMapping("/filter")
    public String filter(@PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam(name="filter") PublishesFilterDto category_id,
                         @AuthenticationPrincipal User user,
                         Model model) {
        model.addAttribute("isMy",false);
        List<String> tags = new ArrayList<>();
        tags.add("freedom");
        tags.add("test");
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("publishes",
                publicationRepos.findByFilter(pageable,tags,tags.size()));
        model.addAttribute("title", "Публікації");
        model.addAttribute("url","/news");
        return "publish/publishes_view";
    }
//TODO add filters
    @GetMapping("/my")
    public String getMyAll(@AuthenticationPrincipal User user,
                           @PageableDefault(sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                           Model model) {
        model.addAttribute("isMy",true);
        model.addAttribute("categories",categoryService.getAll());
        model.addAttribute("publishes", publicationRepos.findAllByAuthor(user,user,pageable));
        model.addAttribute("title", "Мої публікації");
        model.addAttribute("url","/news/my");
        return "publish/publishes_view";
    }

    @GetMapping("/{id}")
    public String getPublishViewById(@PathVariable String id,
                                     @AuthenticationPrincipal User user,
                                     Model model) {
        PublishDto publish = publicationRepos.findById(Long.parseLong(id),user);
        model.addAttribute("publish", publish);
        model.addAttribute("isEdit", publish.isEdit(user));
        return "publish/publish_view";
    }

    //TODO change creations and publication
    @PostMapping("/add")
    public String addPublish(@AuthenticationPrincipal User user,
                             @RequestParam(name = "name") String name,
                             Model model) {
        model.addAttribute("publish", publicationService.createPublish(user,name));
        model.addAttribute("isEdit", true);
        return "publish/publish_view";
    }

    @GetMapping("/like/{id}")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long id,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = publicationRepos.findById(id).get().getLikes();
        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }
        return RedirectUrlBuilder.buildRedirect(referer,redirectAttributes);
    }

    @GetMapping("/edit/{id}")
    public String edit(@AuthenticationPrincipal User user,
                       @PathVariable long id,
                       Model model) {
        model.addAttribute("publish", publicationService.getPublishForEditing(id,user));
        return "publish/edit_publish_poster";
    }


    @PostMapping("/update_poster")
    public String updatePoster(@RequestParam("publishId") long id,
                               @RequestParam("title") String title,
                               @RequestParam("file") MultipartFile imageTitle) throws IOException {
        publicationService.publication(id,title,imageTitle);
        return "redirect:/publish/"+id;
    }

    @GetMapping("/activate/{id}")
    public String inverseActive(@PathVariable long id,
                                @AuthenticationPrincipal User user) {
        publicationService.inverseActivePublish(id,user);
        return "redirect:/publish/{id}";
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable long id,
                               @AuthenticationPrincipal User user,
                               RedirectAttributes attributes) {
        Publish publish = publicationRepos.findById(id);
        if (!publicationService.delete(publish, user)) {
            attributes.addFlashAttribute("dang", "У вас немає прав видалити публікацію");
            return new RedirectView("/publish");
        } else
            attributes.addFlashAttribute("succ", "Пулікацію видаленно");
        if (publish.isAuthor(user))
            return new RedirectView("/publish/my");
        else
            return new RedirectView("/publish");
    }

    @PostMapping(value = "/update/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateContent(@PathVariable long id,
                                @AuthenticationPrincipal User user,
                                @RequestBody String json) throws JsonProcessingException {
        Publish publish = publicationRepos.findById(id);
        if (publish.isEdit(user)) {
            publicationService.updateContent(json, publish);
            return "ok";
        } else
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
    }
}
