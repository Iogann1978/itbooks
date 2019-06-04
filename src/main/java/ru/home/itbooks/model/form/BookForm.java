package ru.home.itbooks.model.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import ru.home.itbooks.model.BookRate;
import ru.home.itbooks.model.BookState;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookForm implements ItemForm {
    private Long id;
    private String title;
    private int pages;
    private String authors;
    private Long publisher;
    private Long file;
    private Long descript;
    private String contents;
    private BookRate rate;
    private int year;
    private BookState state;
    private String tags;
    private MultipartFile fileHtml;
    private MultipartFile fileXml;
}
