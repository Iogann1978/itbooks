package ru.home.itbooks.service;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.home.itbooks.model.form.ItemForm;

import java.util.Optional;

public abstract class AbstractService<T0, T1 extends ItemForm, T2 extends JpaRepository<T0, Long>> {
    protected T2 repository;

    public AbstractService(T2 repository) {
        this.repository = repository;
    }

    public Optional<T0> findById(Long id) {
        return repository.findById(id);
    }

    public Iterable<T0> findAll() {
        return repository.findAll();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public boolean existsById(Long id) {
        return repository.existsById(id);
    }

    public long count() {
        return repository.count();
    }

    public T0 save(T0 entity) {
        return repository.save(entity);
    }

    public abstract T0 save(T1 itemform);

    public Iterable<T0> saveAll(Iterable<T0> entities) {
        return repository.saveAll(entities);
    }
}
