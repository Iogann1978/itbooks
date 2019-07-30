package ru.home.itbooks.controller;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.home.itbooks.model.Publisher;
import ru.home.itbooks.service.PublisherService;

import javax.annotation.security.RolesAllowed;

@Controller
@RequestMapping(value = "/publisher")
@Slf4j
public class PublisherController extends AbstractController<Publisher, PublisherService> {

    @Autowired
    public PublisherController(PublisherService publisherService) {
        super(publisherService);
        setViewHtml("publisher.html");
        setListHtml("publishers.html");
        setAddHtml("add_publisher.html");
        setEditHtml("edit_publisher.html");
        setDelHtml("del_publisher.html");
    }

    @Override
    protected void itemModel(Model model, Publisher publisher) {
        model.addAttribute("publisher", publisher);
    }

    @Override
    protected void listModel(Model model, Iterable publishers) {
        model.addAttribute("publishers", publishers);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/{id}")
    public String getPublisher(Model model, @PathVariable Long id) {
        return get("Издатель не найден!", model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/list")
    public String getPublishers(Model model) {
        return getList(model);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/edit/{id}")
    public String editPublisher(Model model, @PathVariable Long id) {
        return edit(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/add")
    public String addPublisher(Model model) {
        val publisher = new Publisher();
        return add(model, publisher);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/save")
    public String savePublisher(@ModelAttribute("publisher") Publisher publisher) {
        return save(publisher);
    }

    @RolesAllowed("USER,ADMIN")
    @GetMapping("/del/{id}")
    public String delPublisher(Model model, @PathVariable Long id) {
        return del(model, id);
    }

    @RolesAllowed("USER,ADMIN")
    @PostMapping("/del")
    public String delPublisher(@ModelAttribute("publisher") Publisher publisher) {
        return del(publisher.getId());
    }
}
