package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
