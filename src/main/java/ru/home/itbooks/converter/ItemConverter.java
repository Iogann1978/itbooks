package ru.home.itbooks.converter;

public interface ItemConverter<T0, T1> {
    T0 getItem(T1 s);
}
