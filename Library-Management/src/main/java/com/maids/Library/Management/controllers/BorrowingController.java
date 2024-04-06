package com.maids.Library.Management.controllers;

import com.maids.Library.Management.composite.BorrowingId;
import com.maids.Library.Management.entites.Borrowing;
import com.maids.Library.Management.services.BookService;
import com.maids.Library.Management.services.BorrowingService;
import com.maids.Library.Management.services.PatronService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class BorrowingController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(), error.getDefaultMessage());
        });
        return errorMap;
    }

    private final BorrowingService borrowingService;


    private final BookService bookService;

    private final PatronService patronService;



    @Autowired
    public BorrowingController(final BorrowingService borrowingService,final BookService bookService, final PatronService patronService){
        this.borrowingService = borrowingService;
        this.patronService = patronService;
        this.bookService = bookService;
    }

    @PostMapping(path = "api/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<Borrowing> borrowBook(@PathVariable final Long bookId, @PathVariable final Long patronId,@RequestBody @Valid final Borrowing borrowing){
        if (bookService.isBookExists(bookId) && patronService.isPatronExist(patronId)){
            borrowing.setBookId(bookId);
            borrowing.setPatronId(patronId);
            return new ResponseEntity<Borrowing>(borrowingService.createBorrowing(borrowing), HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<Borrowing>(HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping(path = "api/borrow/{bookID}/patron/{patronId}")
    public ResponseEntity<Borrowing> returnBook(@PathVariable final Long bookID, @PathVariable final Long patronId){
        BorrowingId id = new BorrowingId(patronId,bookID);
        if (borrowingService.isExistBorrowing(id)){
            Borrowing borrowing = borrowingService.findById(id);
            borrowing.setReturndate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));

            borrowingService.createBorrowing(borrowing);
            return new ResponseEntity<Borrowing>(borrowing,HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
