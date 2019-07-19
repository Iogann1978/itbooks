package ru.home.itbooks.controller;

import lombok.val;
import org.springframework.ui.Model;
import ru.home.itbooks.model.Item;
import ru.home.itbooks.model.form.ItemForm;
import ru.home.itbooks.service.AbstractService;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController<T0 extends Item<T1>, T1 extends ItemForm<T0>, T2 extends AbstractService> {
    private T2 service;
    private final Map<String, String> htmls = new HashMap<String, String>();

    public AbstractController(T2 service) {
        this.service = service;
        htmls.put("error", "error.html");
    }

    protected void setViewHtml(String file) {htmls.put("view", file);}
    protected void setListHtml(String file) {htmls.put("list", file);}
    protected void setEditHtml(String file) {htmls.put("edit", file);}
    protected void setAddHtml(String file) {htmls.put("add", file);}
    protected void setDelHtml(String file) {htmls.put("del", file);}
    abstract protected void itemFormModel(Model model, T1 itemForm);
    abstract protected void itemModel(Model model, T0 item);
    abstract protected void listModel(Model model, Iterable list);

    protected String get(String text_error, Model model, Long id) {
        val item = service.findById(id);
        if(item.isPresent()) {
            itemModel(model, (T0) item.get());
            return htmls.get("view");
        } else {
            model.addAttribute("error", text_error);
            return htmls.get("error");
        }
    }

    protected String getList(Model model) {
        val count = service.count();
        model.addAttribute("count", count);
        if(count > 0) {
            val list = service.findAll();
            listModel(model, list);
        }
        return htmls.get("list");
    }

    protected String edit(Model model, Long id) {
        val item = service.findById(id);
        if(item.isPresent()) {
            val i = (T0) item.get();
            val itemForm = i.toItemForm();
            itemFormModel(model, itemForm);
            return htmls.get("edit");
        } else {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get("error");
        }
    }

    protected String add(Model model, T1 itemForm) {
        itemFormModel(model, itemForm);
        return htmls.get("add");
    }

    protected String save(T1 itemForm) {
        service.save(itemForm);
        return "redirect:list";
    }

    protected String del(Model model, Long id) {
        val item = service.findById(id);
        if(item.isPresent()) {
            val i = (T0) item.get();
            val itemForm = i.toItemForm();
            itemFormModel(model, itemForm);
            return htmls.get("del");
        } else {
            model.addAttribute("error", "Книга не найдена!");
            return htmls.get("error");
        }
    }

    protected String del(T1 itemForm) {
        service.deleteById(itemForm.getId());
        return "redirect:list";
    }

    protected T2 getService() {
        return service;
    }
}
