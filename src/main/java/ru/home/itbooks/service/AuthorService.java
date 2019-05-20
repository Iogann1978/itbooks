package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Author;
import ru.home.itbooks.repository.AuthorRepository;

import java.util.Optional;

@Service
public class AuthorService extends AbstractService<Author, AuthorRepository> {
    @Autowired
    public AuthorService(AuthorRepository repository) {
        super(repository);
    }

    public Optional<Author> findByName(String name) {
        return repository.findAuthorByName(name);
    }
}
