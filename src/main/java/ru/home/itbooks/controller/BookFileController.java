package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.form.BookFileForm;
import ru.home.itbooks.model.form.TagForm;
import ru.home.itbooks.service.BookFileService;
import ru.home.itbooks.service.TagService;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/file")
@Slf4j
public class BookFileController {
    private BookFileService fileService;
    private static final Map<String, String> htmls = new HashMap<String, String>() {
        {
            put("view", "file.html");
            put("list", "files.html");
            put("add", "add_file.html");
            put("edit", "edit_file.html");
            put("del", "del_file.html");
            put("error", "error.html");
        }
    };

    @Autowired
    public BookFileController(BookFileService fileService) {
        this.fileService = fileService;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getFile(Model model, @PathVariable Long id) {
        val file = fileService.findById(id);
        val result = file.map(f -> {
            model.addAttribute("file", file);
            return htmls.get("view");
        }).orElseGet(() -> {
            model.addAttribute("error", "Файл не найден!");
            return htmls.get("error");
        });
        return result;
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getFiles(Model model) {
        val count = fileService.count();
        model.addAttribute("count", count);
        if(count > 0) {
            val files = fileService.findAll();
            model.addAttribute("files", files);
        }
        return htmls.get("list");
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String addFile(Model model) {
        val fileForm = new BookFileForm();
        model.addAttribute("fileForm", fileForm);
        return htmls.get("add");
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String saveFile(@ModelAttribute("fileForm") BookFileForm fileForm) {
        fileService.save(fileForm);
        return "redirect:list";
    }
}
