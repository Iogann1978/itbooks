package ru.home.itbooks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookForm {
    private String title;
    private int pages;
    private String authors;
    private Long publisher;
    private BookRate rate;
    private int year;
    private BookState state;
    private String tags;
    private MultipartFile fileHtml;
    private MultipartFile fileXml;
}
