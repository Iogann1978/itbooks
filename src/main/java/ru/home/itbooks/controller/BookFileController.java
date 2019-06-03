package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.form.BookFileForm;
import ru.home.itbooks.service.BookFileService;

import javax.annotation.security.RolesAllowed;
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
        log.info("id: {}", id);
        val file = fileService.findById(id);
        val result = file.map(f -> {
            model.addAttribute("file", f);
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
    @GetMapping("/edit/{id}")
    public String editFile(Model model, @PathVariable Long id) {
        val file = fileService.findById(id);
        val result = file.map(f -> {
            val fileForm = BookFileForm.builder()
                    .id(f.getId())
                    .filename(f.getFilename())
                    .size(f.getSize())
                    .build();
            model.addAttribute("fileForm", fileForm);
            return htmls.get("edit");
        }).orElseGet(() -> {
            model.addAttribute("error", "Файл не найден!");
            return htmls.get("error");
        });
        return result;
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String saveFile(@ModelAttribute("fileForm") BookFileForm fileForm) {
        fileService.save(fileForm);
        return "redirect:list";
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/del/{id}")
    public String delFile(Model model, @PathVariable Long id) {
        val file = fileService.findById(id);
        val result = file.filter(f -> f.getBook() == null).map(f -> {
            val fileForm = BookFileForm.builder()
                    .id(f.getId())
                    .filename(f.getFilename())
                    .size(f.getSize())
                    .build();
            model.addAttribute("fileForm", fileForm);
            return htmls.get("del");
        }).orElseGet(() -> {
            model.addAttribute("error", "Файл не найден или нан него ссылается книга!");
            return htmls.get("error");
        });
        return result;
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/del")
    public String delFile(@ModelAttribute("fileForm") BookFileForm fileForm) {
        fileService.deleteById(fileForm.getId());
        return "redirect:list";
    }
}
