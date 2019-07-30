package ru.home.itbooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.repository.BookFileRepository;

import java.util.List;

@Service
public class BookFileService extends AbstractService<BookFile, BookFileRepository> {
    private static final Sort sort = new Sort(Sort.Direction.ASC, "filename");

    @Autowired
    public BookFileService(BookFileRepository repository) {
        super(repository);
    }

    public List<BookFile> getFreeFiles() {return getRepository().getFreeFiles();}

    @Override
    protected Sort getSort() {
        return sort;
    }
}
