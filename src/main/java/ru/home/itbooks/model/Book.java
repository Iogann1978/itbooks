package ru.home.itbooks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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
    private List<Author> authors;
    @ManyToOne(cascade = CascadeType.ALL)
    private Publisher publisher;
    private BookRate rate;
    private int year;
    private BookState state;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_TAG", joinColumns = {@JoinColumn(name = "BOOK_ID")}, inverseJoinColumns = {@JoinColumn(name = "TAG_ID")})
    private List<Tag> tags;
    @OneToOne
    private Descript descript;
    @Lob
    @Column(length=100000)
    private byte[] contents;
    @OneToOne
    private BookFile file;
}
