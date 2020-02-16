package ru.home.itbooks.controller;

import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.RolesAllowed;

@Controller
public class LoginController {
    @GetMapping("/login")
    public ModelAndView login() {
        val model = new ModelAndView();
        model.setViewName("login.html");
        return model;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/")
    public String listBook() {
        return "redirect:book/list";
    }
}
