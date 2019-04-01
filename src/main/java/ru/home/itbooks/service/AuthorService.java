package ru.home.itbooks.service;

import org.springframework.stereotype.Service;
import ru.home.itbooks.model.Author;
import ru.home.itbooks.repository.AuthorRepository;

@Service
public class AuthorService extends AbstractService<Author, AuthorRepository> {
}
