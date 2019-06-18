package ru.home.itbooks.controller;

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
    @GetMapping("/vacuum")
    public void vacuum(Model model) {
        adminService.vacuumLob();
    }
}
