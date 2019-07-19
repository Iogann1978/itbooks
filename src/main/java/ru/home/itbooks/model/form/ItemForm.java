package ru.home.itbooks.model.form;

import ru.home.itbooks.model.Item;

public interface ItemForm<T extends Item> {
    Long getId();
    T toItem();
}
