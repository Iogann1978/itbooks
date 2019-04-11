package ru.home.itbooks.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookForm {
    private String title;
    private int pages;
    private String authors;
    private Publisher publisher;
    private BookRate rate;
    private int year;
    private BookState state;
    private List<Tag> tags;
    private String fileHtml;
    private String fileXml;
}
