package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
            put("edit", "edit_tag.html");
            put("error", "error.html");
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
    @GetMapping("/edit/{id}")
    public String editBook(Model model, @PathVariable Long id) {
        val tag = tagService.findById(id);
        val result = tag.map(t -> {
            val tagForm = TagForm.builder()
                    .id(t.getId())
                    .name(t.getTag())
                    .build();
            model.addAttribute("tagForm", tagForm);
            return htmls.get("edit");
        }).orElseGet(() -> {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get("error");
        });
        return result;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String addTag(Model model) {
        val tagForm = new TagForm();
        model.addAttribute("tagForm", tagForm);
        return htmls.get("add");
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String saveTag(@ModelAttribute("tagForm") TagForm tagForm) {
        tagService.save(tagForm);
        return "redirect:list";
    }
}
