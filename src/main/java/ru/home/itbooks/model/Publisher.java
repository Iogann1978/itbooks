package ru.home.itbooks.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Set<Book> books = new HashSet<>();
}
