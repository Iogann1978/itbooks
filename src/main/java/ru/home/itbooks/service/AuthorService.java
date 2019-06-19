package ru.home.itbooks.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Author;
import ru.home.itbooks.model.form.AuthorForm;
import ru.home.itbooks.repository.AuthorRepository;

import java.util.Optional;

@Service
public class AuthorService extends AbstractService<Author, AuthorForm, AuthorRepository> {
    @Autowired
    public AuthorService(AuthorRepository repository) {
        super(repository);
    }

    public Optional<Author> findByName(String name) {
        return getRepository().findAuthorByName(name);
    }

    public Author save(AuthorForm authorForm) {
        val author = Author.builder()
                .id(authorForm.getId())
                .name(authorForm.getName()).build();
        return getRepository().save(author);
    }
}
