package ru.home.itbooks.service;

import java.util.Optional;

public interface ItemService<T0> {
    Optional<T0> findById(Long id);
    Iterable<T0> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    long count();
    T0 save(T0 entity);
    Iterable<T0> saveAll(Iterable<T0> entities);
}
