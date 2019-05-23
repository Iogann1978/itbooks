package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.model.form.TagForm;
import ru.home.itbooks.service.TagService;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/tag")
@Slf4j
public class TagController {
    private TagService tagService;
    private static final Map<String, String> htmls = new HashMap<String, String>() {
        {
            put("tags", "tags.html");
            put("add", "add_tag.html");
        }
    };

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getTags(Model model) {
        val count = tagService.count();
        model.addAttribute("count", count);
        if(count > 0) {
            val tags = tagService.findAll();
            model.addAttribute("tags", tags);
        }
        return htmls.get("tags");
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String formAddBook(Model model) {
        val tagForm = new TagForm();
        model.addAttribute("tagForm", tagForm);
        return htmls.get("add");
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/add")
    public String addBook(@ModelAttribute("tagForm") TagForm tagForm) {
        val tag = Tag.builder().tag(tagForm.getName()).build();
        tagService.save(tag);
        return "redirect:list";
    }
}
