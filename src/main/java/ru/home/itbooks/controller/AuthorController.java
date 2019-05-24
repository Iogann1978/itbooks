package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.home.itbooks.service.AuthorService;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/author")
@Slf4j
public class AuthorController {
    private AuthorService authorService;
    private static final Map<String, String> htmls = new HashMap<String, String>() {
        {
            put("authors", "authors.html");
        }
    };

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getTags(Model model) {
        val count = authorService.count();
        model.addAttribute("count", count);
        if(count > 0) {
            val authors = authorService.findAll();
            model.addAttribute("authors", authors);
        }
        return htmls.get("authors");
    }
}
