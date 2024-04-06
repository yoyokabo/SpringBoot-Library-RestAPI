package com.maids.Library.Management.controllers;

import com.maids.Library.Management.entites.Book;
import com.maids.Library.Management.services.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import org.springframework.validation.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
public class BookController {  //Actually exposes service layer

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }
    private final BookService bookService;

    @Autowired
    public BookController(final BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping(path = "api/books/")
    public ResponseEntity<Book> createBook(@RequestBody @Valid Book book){

        final ResponseEntity<Book> response = new ResponseEntity<Book>(bookService.create(book), HttpStatus.CREATED);
        return response;

    }

    @GetMapping(path = "api/books/{id}")
    public ResponseEntity<Book> retrieveBook(@PathVariable final Long id){
        final Optional<Book> foundBook = bookService.findById(id);
        return foundBook.map(book -> new ResponseEntity<Book>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<Book>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "api/books")
    public ResponseEntity<List<Book>> listBooks(){
        final List<Book> foundBooks = bookService.listBooks();
        return new ResponseEntity<List<Book>>(foundBooks,HttpStatus.OK);
    }

    @PutMapping(path = "api/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable final Long id,@RequestBody @Valid final Book book) {
        if (bookService.isBookExists(id)) {
            return new ResponseEntity<Book>(bookService.create(book), HttpStatus.OK);
        } else {
            return new ResponseEntity<Book>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping(path = "api/books/{id}")
    public ResponseEntity deleteBook(@PathVariable final Long id){
            bookService.deleteBook(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);


        }


}
