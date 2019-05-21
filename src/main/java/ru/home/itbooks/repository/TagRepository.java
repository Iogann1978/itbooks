package ru.home.itbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
