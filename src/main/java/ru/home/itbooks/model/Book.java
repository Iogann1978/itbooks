package ru.home.itbooks.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    protected String title;
    protected int pages;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_AUTHOR", joinColumns = {@JoinColumn(name = "BOOK_ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTHOR_ID")})
    @EqualsAndHashCode.Exclude
    protected Set<Author> authors = new HashSet<>();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    @EqualsAndHashCode.Exclude
    protected Publisher publisher;
    protected BookRate rate;
    protected int year;
    protected BookState state;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_TAG", joinColumns = {@JoinColumn(name = "BOOK_ID")}, inverseJoinColumns = {@JoinColumn(name = "TAG_ID")})
    @EqualsAndHashCode.Exclude
    protected Set<Tag> tags = new HashSet<>();
    @OneToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    protected Descript descript;
    @Lob
    @Column(length = 100000)
    protected byte[] contents;
    @OneToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    protected BookFile file;

    public void addAuthor(Author author) {
        if(authors == null) {
            authors = new HashSet<>();
        }
        authors.add(author);
    }

    public void addTag(Tag tag) {
        if(tags == null) {
            tags = new HashSet<>();
        }
        tags.add(tag);
    }

    public String strAuthors() {
        return authors.stream().map(Author::getName).collect(Collectors.joining(","));
    }
}
