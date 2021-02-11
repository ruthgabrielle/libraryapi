package com.curso.libraryapi.service;

import com.curso.libraryapi.model.entity.Book;
import com.curso.libraryapi.model.repository.BookRepository;
import com.curso.libraryapi.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class BookServiceTest {

    BookService service;

    @MockBean
    private BookRepository repository;

    @BeforeEach
    public void setUp(){
        this.service = new BookServiceImpl( repository );
    };

    @Test
    @DisplayName("Deve salvar um livro")
    public void saveBookTest(){

        Book book = Book.builder().isbn("123").author("JK Rowling").title("Harry Potter").build();
        Mockito.when( repository.save(book)).thenReturn(
                Book.builder().id(1l)
                        .isbn("123")
                        .title("Harry Potter")
                        .author("JK Rowling")
                        .build());

        Book savedBook = (Book) service.save(book);

        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getIsbn()).isEqualTo("123");
        assertThat(savedBook.getTitle()).isEqualTo("Harry Potter");
        assertThat(savedBook.getAuthor()).isEqualTo("JK Rowling");

    }
}
