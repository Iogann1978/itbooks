package ru.home.itbooks.model;

import lombok.*;
import ru.home.itbooks.model.form.PublisherForm;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publisher implements Item<PublisherForm> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Book> books = new HashSet<>();

    public void addBook(Book book) {
        if(books == null) {
            books = new HashSet<>();
        }
        books.add(book);
    }

    @Override
    public PublisherForm toItemForm() {
        return PublisherForm.builder()
                .id(id)
                .name(name)
                .build();
    }
}
