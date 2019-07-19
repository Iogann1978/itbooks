package ru.home.itbooks.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.itbooks.model.form.ItemForm;

import java.util.Optional;

public interface ItemService<T0, T1 extends ItemForm, T2 extends JpaRepository<T0, Long>> {
    Optional<T0> findById(Long id);
    Iterable<T0> findAll();
    void deleteById(Long id);
    boolean existsById(Long id);
    long count();
    T0 save(T0 entity);
    T0 save(T1 itemform);
    Iterable<T0> saveAll(Iterable<T0> entities);
}
