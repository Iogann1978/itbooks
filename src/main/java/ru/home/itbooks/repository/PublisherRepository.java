package ru.home.itbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.Publisher;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
