package ru.home.itbooks.converter;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Author;
import ru.home.itbooks.service.AuthorService;

@Component
@Slf4j
public class AuthorsConverter extends AbstractStringSetConverter<Author, AuthorService> {
    @Autowired
    public AuthorsConverter(AuthorService authorService) {
        super(authorService, "Автор");
    }

    @Override
    public Author getNewItem(String s) {
        val normName = Author.normalizeName(s);
        val auth = getService().findByName(normName).orElse(Author.builder().name(s).normalizedName(normName).build());
        return auth;
    }
}
