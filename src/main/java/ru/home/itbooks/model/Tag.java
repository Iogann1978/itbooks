package ru.home.itbooks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import ru.home.itbooks.model.form.TagForm;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tag implements Item<TagForm> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tag;
    @ManyToMany(mappedBy = "tags")
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
    public TagForm toItemForm() {
        return TagForm.builder()
                .id(id)
                .name(tag)
                .build();
    }
}
