package ru.home.itbooks.service;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public abstract class AbstractService<T0, T1 extends JpaRepository<T0, Long>>
    implements ItemService<T0> {
    private T1 repository;

    public AbstractService(T1 repository) {
        this.repository = repository;
    }

    public Optional<T0> findById(Long id) {
        return repository.findById(id);
    }

    public Iterable<T0> findAll() {
        return repository.findAll(getSort());
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

    public Iterable<T0> saveAll(Iterable<T0> entities) {
        return repository.saveAll(entities);
    }

    protected T1 getRepository() {
        return repository;
    }

    protected abstract Sort getSort();
}
