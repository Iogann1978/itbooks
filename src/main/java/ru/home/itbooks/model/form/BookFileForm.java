package ru.home.itbooks.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.home.itbooks.model.BookFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookFileForm implements ItemForm<BookFile> {
    private Long id;
    private String filename;
    private Long size;

    @Override
    public BookFile toItem() {
        return BookFile.builder()
                .id(id)
                .filename(filename)
                .size(size)
                .build();
    }
}
