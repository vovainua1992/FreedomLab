package com.example.springmvc.controller;

import com.example.springmvc.dommain.Publish;
import com.example.springmvc.dommain.User;
import com.example.springmvc.dommain.dto.PublishContent;
import com.example.springmvc.repos.PublicationRepos;
import com.example.springmvc.service.ImageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/news")
/**
 * Контроллер публікацій.
 */
public class PublicationController {
    /**
     * Сервіс зображень.
     */
    private final ImageService imageService;
    /**
     * Репозиторій публікацій.
     */
    private final PublicationRepos newsRepos;
    private Logger logger = LogManager.getLogger("NewsController");

    public PublicationController(ImageService imageService, PublicationRepos newsRepos) {
        this.imageService = imageService;
        this.newsRepos = newsRepos;
    }


    /**
     * Всі публікації.
     * @param model
     * @return Сторінка публікацій
     */
    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("news", newsRepos.findAllByActiveTrue());
        model.addAttribute("title","Публікації");
        return "publish/titles";
    }

    /**
     * Всі публікації користувача.
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/my")
    public String getMyAll(@AuthenticationPrincipal User user,
                           Model model){
        model.addAttribute("news",newsRepos.findAllByAuthor(user));
        model.addAttribute("title","Мої публікації");
        return "publish/titles";
    }

    /**
     * Сторінка публікації.
     * @param publish
     * @param model
     * @return
     */
    @GetMapping("{publish}")
    public String getNew(@PathVariable Publish publish,
                         @AuthenticationPrincipal User user,
                         Model model) {
        boolean isEdit = user!=null&&(user.equals(publish.getAuthor())||user.isAdmin());
        model.addAttribute("publish", publish);
        model.addAttribute("isEdit",isEdit);
        return "publish/news_view";
    }



    /**
     * Створення публікації.
     * @return
     */
    @GetMapping("/add")
    public String newAdd(@AuthenticationPrincipal User user,
                         Model model) {
        String defaultContent = "<p>Текст публікації</p>";
        Publish publish= new Publish();
        publish.setActive(false);
        publish.setAuthor(user);
        publish.setTitleNames("Назва публікації");
        publish.setTitleImages(null);
        publish.setTextHtml(defaultContent);
        newsRepos.save(publish);
        model.addAttribute("publish", publish);
        return "publish/create_publish";
    }

    /**
     * Редагування постеру публікації.
     * @param user
     * @param publish
     * @param model
     * @return
     */
    @GetMapping("/edit/{publish}")
    public String edit(@AuthenticationPrincipal User user,
                         @PathVariable Publish publish,
                         Model model) {
        if (!user.equals(publish.getAuthor())&& !user.isAdmin())
            return "errors/not_avtorithy";
        model.addAttribute("publish", publish);
        return "publish/create_publish";
    }

    /**
     * Оновлення постеру новини
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

        Publish publish = newsRepos.findById(id);
        publish.setTitleNames(title);

        if (!visible.isEmpty())
            publish.setActive(true);
        else
            publish.setActive(false);

        if (!imageTitle.isEmpty()) {
            String urlImage = imageService.saveImage(imageTitle);
            publish.setTitleImages(urlImage);
        }

        newsRepos.save(publish);
        model.addAttribute("publish", publish);
        return "publish/create_publish";
    }


    /**
     * Перемикання активності(видимості публікації)
     * @param publish
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/activate/{publish}")
    public String setActive(@PathVariable Publish publish,
                            @AuthenticationPrincipal User user,
                            Model model){
        if (user.equals(publish.getAuthor())) {
            publish.setActive(!publish.isActive());
            newsRepos.save(publish);
        }
        boolean isEdit = user!=null&&(user.equals(publish.getAuthor())||user.isAdmin());
        model.addAttribute("publish", publish);
        model.addAttribute("isEdit",isEdit);
        return "publish/news_view";
    }

    /**
     * Видалення публікації.
     * @param publish
     * @param user
     * @return
     */
    @GetMapping("/delete/{publish}")
    public String delete(@PathVariable Publish publish,
                         @AuthenticationPrincipal User user) {
        boolean isEdit = user!=null&&(user.equals(publish.getAuthor())||user.isAdmin());
        if(isEdit){
            newsRepos.delete(publish);
        }
        return "redirect:/news/my";
    }

    /**
     * Оновлення вмісту публікації (через редактор ContentTools)
     * @param publish
     * @param json
     * @param model
     * @return
     * @throws JsonProcessingException
     */
    @PostMapping(value = "/update/{publish}",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String updateNews(@PathVariable Publish publish,
                             @RequestBody String json,
                             Model model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        PublishContent publishContent = objectMapper.readValue(json, PublishContent.class);
        publishContent.getRegions().forEach((r) -> {
            System.out.println(r.toString());
        });
        publish.setTextHtml(publishContent.getHtml());
        newsRepos.save(publish);
        return "ok";
    }


}
