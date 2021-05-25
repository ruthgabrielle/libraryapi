package com.curso.libraryapi.service;

import com.curso.libraryapi.model.entity.Book;

import java.util.Optional;

public interface BookService {

    Object save(Book any);

    Optional<Book> getById(Long id);

    void delete(Book book);

    Book update(Book book);

}
