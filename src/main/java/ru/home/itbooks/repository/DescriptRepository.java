package ru.home.itbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.Descript;

@Repository
public interface DescriptRepository extends JpaRepository<Descript, Long> {
}
