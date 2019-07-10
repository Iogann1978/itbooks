package ru.home.itbooks.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Author;
import ru.home.itbooks.repository.AuthorRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminService {
    private EntityManager em;
    private AuthorRepository authorRepository;

    @Autowired
    public AdminService(EntityManager em, AuthorRepository authorRepository) {
        this.em = em;
        this.authorRepository = authorRepository;
    }

    public void checkpoint() {
        //em.createStoredProcedureQuery("CHECKPOINT").execute();
        em.createStoredProcedureQuery("SYSTEM_LOBS.MERGE_EMPTY_BLOCKS").execute();
    }

    public List<Author> getBadAuthors() {
        return authorRepository.findAll().stream()
                .filter(auth -> (auth.getBooks() == null || auth.getNormalizedName() == null))
                .collect(Collectors.toList());

    }
}
