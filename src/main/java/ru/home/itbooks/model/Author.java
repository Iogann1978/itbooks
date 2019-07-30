package ru.home.itbooks.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String normalizedName;
    @ManyToMany(mappedBy = "authors")
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private Set<Book> books = new HashSet<>();

    public Author(Long id, String name, Set<Book> books) {
        this.id = id;
        this.name = name;
        this.normalizedName = normalizeName(name);
        this.books = books;
    }

    public void addBook(Book book) {
        if(books == null) {
            books = new HashSet<>();
        }
        books.add(book);
    }

    public static String normalizeName(String name) {
        val arr = name.split(" ");
        if(arr.length > 1) {
            Arrays.sort(arr);
        }
        String normName = "";
        for(int i = 0; i < arr.length; i++) {
            normName += arr[i].toUpperCase();
            if(i < arr.length - 1) {
                normName += " ";
            }
        }
        return normName;
    }

    @Override
    public String toString() {
        return name;
    }
}
