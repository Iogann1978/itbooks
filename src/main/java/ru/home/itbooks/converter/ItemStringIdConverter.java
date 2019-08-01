package ru.home.itbooks.converter;

public interface ItemStringIdConverter<T> {
    T getNewItem(String s);
}
