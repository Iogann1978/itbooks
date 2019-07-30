package ru.home.itbooks.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.*;
import ru.home.itbooks.model.form.FindForm;
import ru.home.itbooks.repository.BookRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookService extends AbstractService<Book, BookRepository> {
    private static final Sort sort = new Sort(Sort.Direction.ASC, "title");
    private DescriptService descriptService;
    private AuthorService authorService;
    private TagService tagService;
    private PublisherService publisherService;
    private BookFileService bookFileService;
    private ContentsService contentsService;

    @Autowired
    public BookService(BookRepository repository,
                       DescriptService descriptService,
                       AuthorService authorService,
                       TagService tagService,
                       PublisherService publisherService,
                       BookFileService bookFileService,
                       ContentsService contentsService) {
        super(repository);
        this.descriptService = descriptService;
        this.authorService = authorService;
        this.tagService = tagService;
        this.publisherService = publisherService;
        this.bookFileService = bookFileService;
        this.contentsService = contentsService;
    }

    @Override
    protected Sort getSort() {
        return sort;
    }

    @SneakyThrows
    public Collection<Book> findBook(FindForm findForm, String action) {
        Collection<Book> books = null;
        switch (action) {
            case "state":
                books = getRepository().findBooksByState(findForm.getState());
                break;
            case "title":
                books = getRepository().findBooksByTitleContains(findForm.getTitle());
                break;
            case "author":
                val author = authorService.findById(findForm.getAuthorId())
                        .orElseThrow(() -> new Exception(String.format("Автор %s не найден!", findForm.getAuthorId())));
                books = author.getBooks();
                break;
            case "publisher":
                val publisher = publisherService.findById(findForm.getPublisherId())
                        .orElseThrow(() -> new Exception(String.format("Издатель %s не найден!", findForm.getPublisherId())));
                books = publisher.getBooks();
                break;
            case "tag":
                val tag = tagService.findById(findForm.getTagId())
                        .orElseThrow(() -> new Exception(String.format("Тэг %s не найден!", findForm.getTagId())));
                books = tag.getBooks();
                break;
            case "descript":
                books = descriptService.findByHtml(findForm.getDescript())
                        .stream().filter(d -> d.getBook() != null)
                        .map(d -> d.getBook()).collect(Collectors.toList());
                break;
            case "contents":
                books = getRepository().findAll().stream()
                        .filter(book -> {
                            if(book.getContents() == null || book.getContents().length == 0) {
                                return false;
                            } else {
                                val root = ContentsService.fromBytes(book.getContents());
                                return contentsService.containsXml(root, findForm.getContents());
                            }
                        }).collect(Collectors.toList());
                break;
            default:
                break;
        }
        return books;
    }
}
