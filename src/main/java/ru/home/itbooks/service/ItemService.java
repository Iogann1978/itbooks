package ru.home.itbooks.service;

import ru.home.itbooks.model.Item;
import ru.home.itbooks.model.form.ItemForm;

import java.util.Optional;

public interface ItemService<T0 extends Item<T1>, T1 extends ItemForm<T0>> {
    Optional<T0> findById(Long id);
    Iterable<T0> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    long count();
    T0 save(T0 entity);
    T0 save(T1 itemForm);
    Iterable<T0> saveAll(Iterable<T0> entities);
}
