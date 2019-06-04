package ru.home.itbooks.model;

import lombok.*;
import org.springframework.mock.web.MockMultipartFile;
import ru.home.itbooks.model.form.BookForm;

import javax.persistence.*;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Item<BookForm> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private int pages;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_AUTHOR", joinColumns = {@JoinColumn(name = "BOOK_ID")}, inverseJoinColumns = {@JoinColumn(name = "AUTHOR_ID")})
    @EqualsAndHashCode.Exclude
    private Set<Author> authors = new HashSet<>();
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BOOK_ID")
    @EqualsAndHashCode.Exclude
    private Publisher publisher;
    private BookRate rate;
    private int year;
    private BookState state;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "BOOK_TAG", joinColumns = {@JoinColumn(name = "BOOK_ID")}, inverseJoinColumns = {@JoinColumn(name = "TAG_ID")})
    @EqualsAndHashCode.Exclude
    private Set<Tag> tags = new HashSet<>();
    @OneToOne
    private Descript descript;
    @Lob
    @Column(length = 100000)
    private byte[] contents;
    @OneToOne
    @EqualsAndHashCode.Exclude
    private BookFile file;

    public void addAuthor(Author author) {
        if(authors == null) {
            authors = new HashSet<>();
        }
        authors.add(author);
    }

    public void addTag(Tag tag) {
        if(tags == null) {
            tags = new HashSet<>();
        }
        tags.add(tag);
    }

    @Override
    public BookForm toItemForm() {
        val bookForm = BookForm.builder()
                .id(id)
                .title(title)
                .authors(String.join(";", authors.stream().map(a -> a.getName()).toArray(String[]::new)))
                .tags(String.join(";", tags.stream().map(t -> t.getTag()).toArray(String[]::new)))
                .pages(pages)
                .year(year)
                .rate(rate)
                .state(state)
                .fileXml(new MockMultipartFile("fileXml", contents))
                .build();
        if(descript != null) {
            bookForm.setFileHtml(new MockMultipartFile("fileHtml", descript.getText()));
            bookForm.setDescript(descript.getId());
        }
        if(contents != null) {
            bookForm.setContents(new String(contents, StandardCharsets.UTF_8));
        }
        if(file != null) {
            bookForm.setFile(file.getId());
        }
        if(publisher != null) {
            bookForm.setPublisher(publisher.getId());
        }
        return bookForm;
    }
}
