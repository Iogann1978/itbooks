package ru.home.itbooks.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.home.itbooks.service.AdminService;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/checkpoint")
    public String checkpoint() {
        adminService.checkpoint();
        return "books.html";
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/bad_authors")
    public String bad_authors(Model model) {
        val authors = adminService.getBadAuthors();
        if(authors != null) {
            model.addAttribute("authors", authors);
            model.addAttribute("count", authors.size());
        }
        return "admin_authors.html";
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/author_correct/{id}")
    public String author_correct(@PathVariable Long id) {
        adminService.author_correct(id);
        return "redirect:/admin/bad_authors";
    }
}
