package ru.home.itbooks.controller;

import lombok.val;
import org.springframework.ui.Model;
import ru.home.itbooks.service.ItemService;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController<T0, T1 extends ItemService<T0>> {
    enum Views {
        View, List, Edit, Add, Del, Error;
    }

    private T1 service;
    private final Map<Views, String> htmls = new HashMap<>();

    public AbstractController(T1 service) {
        this.service = service;
        htmls.put(Views.Error, "error.html");
    }

    protected void setViewHtml(String file) {
        htmls.put(Views.View, file);
    }
    protected void setListHtml(String file) {
        htmls.put(Views.List, file);
    }
    protected void setEditHtml(String file) {
        htmls.put(Views.Edit, file);
    }
    protected void setAddHtml(String file) {
        htmls.put(Views.Add, file);
    }
    protected void setDelHtml(String file) {
        htmls.put(Views.Del, file);
    }
    abstract protected void itemModel(Model model, T0 item);
    abstract protected void listModel(Model model, Iterable list);

    protected String get(String text_error, Model model, Long id) {
        val item = service.findById(id);
        if(item.isPresent()) {
            itemModel(model, item.get());
            return htmls.get(Views.View);
        } else {
            model.addAttribute("error", text_error);
            return htmls.get(Views.Error);
        }
    }

    protected String getList(Model model) {
        val count = service.count();
        model.addAttribute("count", count);
        if(count > 0) {
            val list = service.findAll();
            listModel(model, list);
        }
        return htmls.get(Views.List);
    }

    protected String edit(Model model, Long id) {
        val item = service.findById(id);
        if(item.isPresent()) {
            itemModel(model, item.get());
            return htmls.get(Views.Edit);
        } else {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get(Views.Error);
        }
    }

    protected String add(Model model, T0 item) {
        itemModel(model, item);
        return htmls.get(Views.Add);
    }

    protected String save(T0 item) {
        service.save(item);
        return "redirect:list";
    }

    protected String del(Model model, Long id) {
        val item = service.findById(id);
        if(item.isPresent()) {
            itemModel(model, item.get());
            return htmls.get(Views.Del);
        } else {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get(Views.Error);
        }
    }

    protected String del(Long id) {
        service.deleteById(id);
        return "redirect:list";
    }

    protected T1 getService() {
        return service;
    }
}
