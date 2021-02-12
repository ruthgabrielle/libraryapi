package com.curso.libraryapi.service.impl;

import com.curso.libraryapi.exception.BussinessExcetpion;
import com.curso.libraryapi.model.entity.Book;
import com.curso.libraryapi.model.repository.BookRepository;
import com.curso.libraryapi.service.BookService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository repository;

    public BookServiceImpl(BookRepository repository) {
        this.repository = repository;
    }

    @Override
    public Book save(Book book) {
        if(repository.existsByIsbn(book.getIsbn() )){
            throw new BussinessExcetpion("Isbn já cadastrado");
        }
        return (Book) repository.save(book);
    }

    @Override
    public Optional<Book> getById(Long id) {
        return Optional.empty();
    }
}
