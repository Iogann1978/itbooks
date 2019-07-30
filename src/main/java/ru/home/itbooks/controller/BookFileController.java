package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.service.BookFileService;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping(value = "/file")
@Slf4j
public class BookFileController extends AbstractController<BookFile, BookFileService> {

    @Autowired
    public BookFileController(BookFileService fileService) {
        super(fileService);
        setViewHtml("file.html");
        setListHtml("files.html");
        setAddHtml("add_file.html");
        setEditHtml("edit_file.html");
        setDelHtml("del_file.html");
    }

    @Override
    protected void itemModel(Model model, BookFile file) {
        model.addAttribute("file", file);
    }

    @Override
    protected void listModel(Model model, Iterable files) {
        model.addAttribute("files", files);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getFile(Model model, @PathVariable Long id) {
        return get("Файл не найден!", model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getFiles(Model model) {
        return getList(model);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String addFile(Model model) {
        val bookFile = new BookFile();
        return add(model, bookFile);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/edit/{id}")
    public String editFile(Model model, @PathVariable Long id) {
        return edit(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String saveFile(@ModelAttribute("bookFile") BookFile bookFile) {
        return save(bookFile);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/del/{id}")
    public String delFile(Model model, @PathVariable Long id) {
        return del(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/del")
    public String delFile(@ModelAttribute("bookFile") BookFile bookFile) {
        return del(bookFile.getId());
    }
}
