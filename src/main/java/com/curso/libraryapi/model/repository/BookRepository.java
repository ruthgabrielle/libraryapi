package com.curso.libraryapi.model.repository;

import com.curso.libraryapi.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository <Book, Long>{


}
