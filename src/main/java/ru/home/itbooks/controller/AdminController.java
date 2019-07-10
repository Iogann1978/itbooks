package ru.home.itbooks.controller;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    @GetMapping("/auth_norm")
    public String auth_norm(Model model) {
        val authors = adminService.getBadAuthors();
        if(authors != null) {
            model.addAttribute("authors", authors);
            model.addAttribute("count", authors.size());
        }
        return "admin_authors.html";
    }
}
