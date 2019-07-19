package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Author;
import ru.home.itbooks.model.form.AuthorForm;
import ru.home.itbooks.repository.AuthorRepository;

import java.util.Optional;

@Service
public class AuthorService extends AbstractService<Author, AuthorForm, AuthorRepository> {
    private static final Sort sort = new Sort(Sort.Direction.ASC, "name");

    @Autowired
    public AuthorService(AuthorRepository repository) {
        super(repository);
    }

    public Optional<Author> findByName(String name) {
        return getRepository().findAuthorByNormalizedName(Author.normalizeName(name));
    }

    @Override
    protected Sort getSort() {
        return sort;
    }
}
