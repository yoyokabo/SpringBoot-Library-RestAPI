package com.maids.Library.Management.services.implementation;


import com.maids.Library.Management.entites.Book;
import com.maids.Library.Management.entites.BookEntity;
import com.maids.Library.Management.services.BookService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.maids.Library.Management.repositories.BookRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class BookServiceImpl implements BookService {

    private Book bookEntityToBook(BookEntity book) {
        return Book.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .pubyear(book.getPubyear())
                .build();
    }

    private BookEntity bookToBookEntity(Book book) {
        return BookEntity.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .pubyear(book.getPubyear())
                .build();
    }




    private final BookRepo bookRepo;  //Access database with repo object

    @Autowired   //Spring Creates books and injects it here
    public BookServiceImpl(final BookRepo bookRepo){
        this.bookRepo = bookRepo;
    }

    @Override
    public boolean isBookExists(Long id) {
        return bookRepo.existsById(id);
    }

    @Override
    public Book create(final Book book) {
        final BookEntity bookEntity = bookToBookEntity(book);
        final BookEntity savedBook = bookRepo.save(bookEntity);
        return  bookEntityToBook(savedBook);
    }

    @Override
    public Optional<Book> findById(Long id) {
        final Optional<BookEntity> foundBook = bookRepo.findById(id);
        return foundBook.map(book -> bookEntityToBook(book));
    }
    @Override
    public List<Book> listBooks() {
        final List<BookEntity> foundBooks = bookRepo.findAll();
        return foundBooks.stream().map(book -> bookEntityToBook(book)).collect(Collectors.toList());

    }


    @Override
    public void deleteBook(Long id) {
       try{
           bookRepo.deleteById(id);
       } catch (final EmptyResultDataAccessException ex) {
           log.debug("Deletion in Books where Book doesn't exist");
       }
    }


}
