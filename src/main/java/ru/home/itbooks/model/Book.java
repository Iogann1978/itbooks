package ru.home.itbooks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private int pages;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_AUTHOR", joinColumns = {@JoinColumn(name = "BOOK_ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTHOR_ID")})
    @EqualsAndHashCode.Exclude
    private Set<Author> authors = new HashSet<>();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    @EqualsAndHashCode.Exclude
    private Publisher publisher;
    private BookRate rate;
    private int year;
    private BookState state;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_TAG", joinColumns = {@JoinColumn(name = "BOOK_ID")}, inverseJoinColumns = {@JoinColumn(name = "TAG_ID")})
    @EqualsAndHashCode.Exclude
    private Set<Tag> tags = new HashSet<>();
    @OneToOne
    private Descript descript;
    @Lob
    @Column(length = 100000)
    private byte[] contents;
    @OneToOne
    private BookFile file;

    public void addAuthor(Author author) {
        authors.add(author);
    }
}
