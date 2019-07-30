package ru.home.itbooks.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String filename;
    private Long size;
    @OneToOne(mappedBy="file", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Book book;

    @Override
    public String toString() {
        return filename;
    }
}
