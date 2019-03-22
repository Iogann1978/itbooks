package ru.home.itbooks.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.home.itbooks.model.Descript;

@Repository
public interface DescriptRepository extends CrudRepository<Descript, Long> {
}
