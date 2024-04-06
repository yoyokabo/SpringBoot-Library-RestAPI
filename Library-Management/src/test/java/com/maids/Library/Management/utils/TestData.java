package com.maids.Library.Management.utils;

import com.maids.Library.Management.entites.BorrowingEntity;
import com.maids.Library.Management.entites.Book;
import com.maids.Library.Management.entites.BookEntity;
import com.maids.Library.Management.entites.Borrowing;
import com.maids.Library.Management.entites.Patron;
import com.maids.Library.Management.entites.PatronEntity;

import java.time.LocalDate;
import java.util.Date;

public final class TestData {
    private TestData(){

    }
    public static Book testBook(){
        return Book.builder()
                .id(1L)
                .title("Harry Potter")
                .author("J.K")
                .pubyear(2004)
                .isbn("9781234567897")
                .build();

    }
    public static BookEntity testBookEntity(){
        return BookEntity.builder()
                .id(1L)
                .title("Harry Potter")
                .author("J.K")
                .pubyear(2004)
                .isbn("9781234567897")
                .build();

    }
    public static PatronEntity testPatronEntity(){
        return PatronEntity.builder()
                .id(1L)
                .name("Youssef Kabodan")
                .address("8 Ahmed Amin")
                .phonenumber("+201005006393")
                .build();
    }
    public static Patron testPatron(){
        return Patron.builder()
                .id(1L)
                .name("Youssef Kabodan")
                .address("8 Ahmed Amin")
                .phonenumber("+201005006393")
                .build();
    }
    public static BorrowingEntity testBorrowingEntity(BookEntity book,PatronEntity patron){
        return BorrowingEntity.builder()
                .patronId(patron.getId())
                .bookId(book.getId())
                .borrowingdate(new Date(2023,1,27))
                .duedate(new Date(2023,3,27))
                .build();


    }

    public static Borrowing testBorrowing(BookEntity book,PatronEntity patron){
        return Borrowing.builder()
                .patronId(patron.getId())
                .bookId(book.getId())
                .borrowingdate(new Date(2023,1,27))
                .duedate(new Date(2023,3,27))
                .build();

    }
}
