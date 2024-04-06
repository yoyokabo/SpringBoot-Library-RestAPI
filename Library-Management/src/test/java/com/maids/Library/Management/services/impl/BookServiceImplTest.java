package com.maids.Library.Management.services.impl;

import com.maids.Library.Management.entites.Book;
import com.maids.Library.Management.entites.BookEntity;
import com.maids.Library.Management.services.implementation.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.maids.Library.Management.repositories.BookRepo;

import java.util.List;
import java.util.Optional;

import static com.maids.Library.Management.utils.TestData.testBook;
import static com.maids.Library.Management.utils.TestData.testBookEntity;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private BookRepo bookRepo;

    @InjectMocks  // Creates and injects BookService Object (Unit Test)
    private BookServiceImpl bookServiceTest;

    @Test
    public void testBookSaved(){    // Create Test with Post
        final Book book = testBook();

        final BookEntity bookEntity = testBookEntity();

        when(bookRepo.save(eq(bookEntity))).thenReturn(bookEntity);

        final Book result =  bookServiceTest.create(book);
        assertEquals(book, result);

    }

    @Test
    public void testThatFindByIdReturnEmptyWhenNoBook(){  // ID not found Case
        final Long id = 12L;
        when(bookRepo.findById(eq(id))).thenReturn(Optional.empty());

        final Optional<Book> result = bookServiceTest.findById(id);
        assertEquals(Optional.empty(),result);
    }

    @Test
    public void testThatFindByIdReturnsBookWhenExists(){ // ID found Case
        final Book book = testBook();
        final BookEntity bookEntity = testBookEntity();

        when(bookRepo.findById(eq(book.getId()))).thenReturn(Optional.of(bookEntity));

        final Optional<Book> result = bookServiceTest.findById(book.getId());
        assertEquals(Optional.of(book),result);
    }

    @Test
    public void testListBookReturnsEmptyListWhenNoBooks(){
        final List<Book> result = bookServiceTest.listBooks();
        assertEquals(0,result.size());

    }

    @Test
    public void testListBooksReturnsBooksWhenBooks(){
        final BookEntity bookEntity = testBookEntity();
        when(bookRepo.findAll()).thenReturn(List.of(bookEntity));
        final List<Book> result = bookServiceTest.listBooks();
        assertEquals(1, result.size());
    }

    @Test
    public void testDeleteBookById(){
        final Long id = 1L;
        bookRepo.deleteById(id);
        verify(bookRepo,times(1)).deleteById(eq(id));

    }

}
