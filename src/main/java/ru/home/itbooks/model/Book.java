package ru.home.itbooks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private int pages;
    private String author;
    private String publisher;
    private BookRate rate;
    private int year;
    private boolean studied;
    @OneToMany
    private List<Tag> tags;
    @OneToOne
    private Descript descript;
    private String contents;
}
