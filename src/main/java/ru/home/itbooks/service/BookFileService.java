package ru.home.itbooks.service;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.home.itbooks.model.BookFile;
import ru.home.itbooks.model.Tag;
import ru.home.itbooks.model.form.BookFileForm;
import ru.home.itbooks.model.form.TagForm;
import ru.home.itbooks.repository.BookFileRepository;

import java.util.List;

@Service
public class BookFileService extends AbstractService<BookFile, BookFileForm, BookFileRepository> {
    @Autowired
    public BookFileService(BookFileRepository repository) {
        super(repository);
    }

    public List<BookFile> findBookFilesByBookIsNull() {
        return getRepository().findBookFilesByBookIsNull();
    }

    public BookFile save(BookFileForm fileForm) {
        val file = BookFile.builder()
                .id(fileForm.getId())
                .filename(fileForm.getFilename())
                .size(fileForm.getSize())
                .build();
        return getRepository().save(file);
    }
}
