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
                .filter(auth -> (auth.getBooks() == null || auth.getNormalizedName() == null
                || (auth.getBooks() != null && auth.getBooks().isEmpty())))
                .collect(Collectors.toList());

    }

    public void author_correct(Long id) {
        authorRepository.findById(id).ifPresent(author -> {
            if(author.getBooks() == null) {
                authorRepository.deleteById(id);
            } else if(author.getBooks().isEmpty()) {
                authorRepository.deleteById(id);
            } else if(author.getNormalizedName() == null || author.getNormalizedName().isEmpty()) {
                author.setNormalizedName(Author.normalizeName(author.getName()));
                authorRepository.save(author);
            }
        });
    }
}
