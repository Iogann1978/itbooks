package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.Author;
import ru.home.itbooks.service.AuthorService;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping(value = "/author")
@Slf4j
public class AuthorController extends AbstractController<Author, AuthorService> {

    @Autowired
    public AuthorController(AuthorService authorService) {
        super(authorService);
        setViewHtml("author.html");
        setListHtml("authors.html");
        setEditHtml("edit_author.html");
        setDelHtml("del_author.html");
    }

    @Override
    protected void itemModel(Model model, Author author) {
        model.addAttribute("author", author);
    }

    @Override
    protected void listModel(Model model, Iterable authors) {
        model.addAttribute("authors", authors);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getAuthor(Model model, @PathVariable Long id) {
        return get("Автор не найден!", model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getAuthors(Model model) {
        return getList(model);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/edit/{id}")
    public String editAuthor(Model model, @PathVariable Long id) {
        return edit(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String saveAuthor(@ModelAttribute("author") Author author) {
        return save(author);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/del/{id}")
    public String delAuthor(Model model, @PathVariable Long id) {
        return del(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/del")
    public String delAuthor(@ModelAttribute("author") Author author) {
        return del(author.getId());
    }
}
