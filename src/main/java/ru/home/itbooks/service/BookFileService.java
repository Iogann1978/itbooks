package ru.home.itbooks.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.model.form.BookFileForm;
import ru.home.itbooks.repository.BookFileRepository;

import java.util.List;

@Service
public class BookFileService extends AbstractService<BookFile, BookFileForm, BookFileRepository> {
    private static final Sort sort = new Sort(Sort.Direction.ASC, "filename");

    @Autowired
    public BookFileService(BookFileRepository repository) {
        super(repository);
    }

    public List<BookFile> getFreeFiles() {return getRepository().getFreeFiles();}

    public BookFile save(BookFileForm fileForm) {
        val file = BookFile.builder()
                .id(fileForm.getId())
                .filename(fileForm.getFilename())
                .size(fileForm.getSize())
                .build();
        return getRepository().save(file);
    }

    @Override
    protected Sort getSort() {
        return sort;
    }
}
