package ru.home.itbooks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import ru.home.itbooks.model.form.AuthorForm;
import ru.home.itbooks.model.form.ItemForm;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author implements Item<AuthorForm> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "authors")
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        if(books == null) {
            books = new HashSet<>();
        }
        books.add(book);
    }

    @Override
    public AuthorForm toItemForm() {
        return AuthorForm.builder()
                .id(id).name(name)
                .build();
    }
}
