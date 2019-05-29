package ru.home.itbooks.controller;

import lombok.val;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.home.itbooks.model.form.ItemForm;
import ru.home.itbooks.service.AbstractService;

import javax.annotation.security.RolesAllowed;
import java.util.HashMap;
import java.util.Map;

public class AbstractController<T1 extends ItemForm, T2 extends AbstractService> {
    private T2 service;
    private static final Map<String, String> htmls = new HashMap<String, String>();

    public AbstractController(T2 service) {
        this.service = service;
        htmls.put("error", "error.html");
    }

    protected void setViewHtml(String file) {htmls.put("view", file);}
    protected void setListHtml(String file) {htmls.put("list", file);}
    protected void setEditHtml(String file) {htmls.put("edit", file);}
    protected void setDelHtml(String file) {htmls.put("del", file);}

}
