package ru.home.itbooks.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String filename;
    private Long size;
    @OneToOne(mappedBy="file")
    @EqualsAndHashCode.Exclude
    private Book book;
}
