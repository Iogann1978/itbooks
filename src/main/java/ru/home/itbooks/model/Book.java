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
    @OneToMany
    private List<Author> authors;
    private String publisher;
    private BookRate rate;
    private int year;
    private BookState state;
    @OneToMany
    private List<Tag> tags;
    @OneToOne
    private Descript descript;
    @Lob
    @Column(length=100000)
    private byte[] contents;
}
