package ru.home.itbooks.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.itbooks.model.Author;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorForm implements ItemForm<Author> {
    private Long id;
    private String name;

    @Override
    public Author toItem() {
        return Author.builder()
                .id(id)
                .name(name)
                .normalizedName(Author.normalizeName(name))
                .build();
    }
}
