package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.model.form.TagForm;
import ru.home.itbooks.service.TagService;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping(value = "/tag")
@Slf4j
public class TagController extends AbstractController<Tag, TagForm, TagService> {

    @Autowired
    public TagController(TagService tagService) {
        super(tagService);
        setViewHtml("tag.html");
        setListHtml("tags.html");
        setAddHtml("add_tag.html");
        setEditHtml("edit_tag.html");
        setDelHtml("del_tag.html");
    }

    @Override
    protected void itemFormModel(Model model, TagForm tagForm) {
        model.addAttribute("tagForm", tagForm);
    }

    @Override
    protected void itemModel(Model model, Tag tag) {
        model.addAttribute("tag", tag);
    }

    @Override
    protected void listModel(Model model, Iterable tags) {
        model.addAttribute("tags", tags);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getTag(Model model, @PathVariable Long id) {
        return get("Тэг не найден!", model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getTags(Model model) {
        return getList(model);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/edit/{id}")
    public String editTag(Model model, @PathVariable Long id) {
        return edit(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String addTag(Model model) {
        val tagForm = new TagForm();
        return add(model, tagForm);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String saveTag(@ModelAttribute("tagForm") TagForm tagForm) {
        return save(tagForm);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/del/{id}")
    public String delTag(Model model, @PathVariable Long id) {
        return del(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/del")
    public String delTag(@ModelAttribute("tagForm") TagForm tagForm) {
        return del(tagForm);
    }
}
