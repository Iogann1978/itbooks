package ru.home.itbooks.service;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public abstract class AbstractService<T1, T2 extends JpaRepository<T1, Long>> {
    protected T2 repository;

    public AbstractService(T2 repository) {
        this.repository = repository;
    }

    public Optional<T1> findById(Long id) {
        return repository.findById(id);
    }

    public Iterable<T1> findAll() {
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

    public T1 save(T1 entity) {
        return repository.save(entity);
    }

    public Iterable<T1> saveAll(Iterable<T1> entities) {
        return repository.saveAll(entities);
    }
}
