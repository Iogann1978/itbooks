package ru.home.itbooks.model;

import lombok.*;
import ru.home.itbooks.model.form.BookFileForm;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookFile implements Item<BookFileForm> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String filename;
    private Long size;
    @OneToOne(mappedBy="file", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    private Book book;

    @Override
    public BookFileForm toItemForm() {
        return BookFileForm.builder()
                .id(id)
                .filename(filename)
                .size(size)
                .build();
    }
}
