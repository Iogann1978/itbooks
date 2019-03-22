package ru.home.itbooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.Tag;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
}
