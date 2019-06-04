package ru.home.itbooks.model;

import ru.home.itbooks.model.form.ItemForm;

public interface Item<T extends ItemForm> {
    Long getId();
    T toItemForm();
}
