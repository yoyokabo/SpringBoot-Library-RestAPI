package com.maids.Library.Management.services;

import com.maids.Library.Management.entites.Book;

import java.util.List;
import java.util.Optional;

public interface BookService { //Interface for extensibility

    Book create(Book book);

    boolean isBookExists(Long id);

    Optional<Book> findById(Long id);

    List<Book> listBooks();

    void deleteBook(Long id);


}
