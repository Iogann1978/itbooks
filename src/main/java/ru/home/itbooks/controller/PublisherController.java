package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.form.PublisherForm;
import ru.home.itbooks.service.PublisherService;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/publisher")
@Slf4j
public class PublisherController {
    private PublisherService publisherService;

    private static final Map<String, String> htmls = new HashMap<String, String>() {
        {
            put("view", "publisher.html");
            put("list", "publishers.html");
            put("add", "add_publisher.html");
            put("edit", "edit_publisher.html");
            put("del", "del_publisher.html");
            put("error", "error.html");
        }
    };

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getPublisher(Model model, @PathVariable Long id) {
        val publisher = publisherService.findById(id);
        val result = publisher.map(p -> {
            model.addAttribute("publisher", p);
            return htmls.get("view");
        }).orElseGet(() -> {
            model.addAttribute("error", "Издатель не найден!");
            return htmls.get("error");
        });
        return result;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getPublishers(Model model) {
        val count = publisherService.count();
        model.addAttribute("count", count);
        if(count > 0) {
            val publishers = publisherService.findAll();
            model.addAttribute("publishers", publishers);
        }
        return htmls.get("list");
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/edit/{id}")
    public String editPublisher(Model model, @PathVariable Long id) {
        val publisher = publisherService.findById(id);
        val result = publisher.map(p -> {
            val publisherForm = PublisherForm.builder()
                    .id(p.getId())
                    .name(p.getName())
                    .build();
            model.addAttribute("publisherForm", publisherForm);
            return htmls.get("edit");
        }).orElseGet(() -> {
            model.addAttribute("error", "Издатель не найден!");
            return htmls.get("error");
        });
        return result;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String addPublisher(Model model) {
        val publisherForm = new PublisherForm();
        model.addAttribute("publisherForm", publisherForm);
        return htmls.get("add");
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String savePublisher(@ModelAttribute("publisherForm") PublisherForm publisherForm) {
        publisherService.save(publisherForm);
        return "redirect:list";
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/del/{id}")
    public String delPublisher(Model model, @PathVariable Long id) {
        val publisher = publisherService.findById(id);
        val result = publisher.filter(p -> (p.getBooks() == null || p.getBooks().size() == 0))
                .map(p -> {
                    val publisherForm = PublisherForm.builder()
                            .id(p.getId())
                            .name(p.getName())
                            .build();
                    model.addAttribute("publisherForm", publisherForm);
                    return htmls.get("del");
                }).orElseGet(() -> {
                    model.addAttribute("error", "Издатель не найден или на него ссылаются книги!");
                    return htmls.get("error");
                });
        return result;
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/del")
    public String delPublisher(@ModelAttribute("publisherForm") PublisherForm publisherForm) {
        publisherService.deleteById(publisherForm.getId());
        return "redirect:list";
    }
}
