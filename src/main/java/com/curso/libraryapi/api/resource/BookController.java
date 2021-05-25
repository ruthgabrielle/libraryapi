package com.curso.libraryapi.api.resource;

import com.curso.libraryapi.api.dto.BookDTO;
import com.curso.libraryapi.api.exception.ApiErros;
import com.curso.libraryapi.exception.BussinessExcetpion;
import com.curso.libraryapi.model.entity.Book;
import com.curso.libraryapi.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService service;
    private ModelMapper modelMapper;

    public BookController(BookService service, ModelMapper mapper) {

        this.service = service;
        this.modelMapper = mapper;

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDTO create (@RequestBody @Valid BookDTO dto){
        Book entity = modelMapper.map( dto, Book.class);
        entity = (Book) service.save(entity);
        return modelMapper.map(entity, BookDTO.class);
    }

    @GetMapping("{id}")
    public BookDTO get( @PathVariable Long id ){

        return service.getById(id)
                .map( book -> modelMapper.map(book, BookDTO.class ))
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND) );

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Long id){
        Book book = service.getById(id).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        service.delete(book);
    }

    @PutMapping("{id}")
    public BookDTO update( @PathVariable Long id, BookDTO dto){
        return service.getById(id).map(
                book -> {
                    book.setAuthor(dto.getAuthor());
                    book.setTitle(dto.getTitle());
                    book = service.update(book);
                    return modelMapper.map(book, BookDTO.class);
            }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleValidationExceptions(MethodArgumentNotValidException ex){

        BindingResult bindingResult = ex.getBindingResult();

        return new ApiErros(bindingResult);
    }

    @ExceptionHandler(BussinessExcetpion.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErros handleBussinessExceptions(BussinessExcetpion ex){
        return new ApiErros(ex);
    }
}
