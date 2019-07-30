package ru.home.itbooks.converter;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.home.itbooks.model.Author;
import ru.home.itbooks.service.AuthorService;

import java.util.HashSet;
import java.util.Set;

@Component
public class AuthorsConverter implements Converter<String, Set<Author>> {
    private AuthorService authorService;

    @Override
    public Set<Author> convert(String s) {
        Set<Author> listAuthors = new HashSet<>();
        if(s != null && !s.isEmpty()) {
            val authors = s.split(",");
            for(val author : authors) {
                val auth = authorService.findByName(author).orElse(Author.builder().name(author).build());
                listAuthors.add(auth);
            }
        }
        return listAuthors;
    }

    @Autowired
    public void setAuthorService(AuthorService authorService) {
        this.authorService = authorService;
    }
}
