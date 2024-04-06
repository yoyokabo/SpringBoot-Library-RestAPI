package com.maids.Library.Management.services.impl;

import com.maids.Library.Management.composite.BorrowingId;
import com.maids.Library.Management.entites.BorrowingEntity;
import com.maids.Library.Management.entites.Borrowing;
import com.maids.Library.Management.repositories.BorrowingRepo;
import com.maids.Library.Management.services.implementation.BorrowingServiceImpl;
import com.maids.Library.Management.utils.TestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BorrowingServiceImplTest {

    @Mock
    private BorrowingRepo borrowingRepo;

    @InjectMocks
    private BorrowingServiceImpl borrowingServiceTest;

    @Test
    public void testBorrowingSavedIfNoReturn(){
        final Borrowing borrowing = TestData.testBorrowing(TestData.testBookEntity(),TestData.testPatronEntity());
        final BorrowingEntity borrowingEntity = TestData.testBorrowingEntity(TestData.testBookEntity(),TestData.testPatronEntity());


        when(borrowingRepo.findById(eq(borrowing.getId()))).thenReturn(Optional.of(borrowingEntity));


        final Borrowing result = borrowingServiceTest.findById(borrowing.getId());
        assertEquals(borrowing,result);
    }

    @Test
    public void testGetBorrowingByPatronAndBookId(){
        final Borrowing borrowing = TestData.testBorrowing(TestData.testBookEntity(),TestData.testPatronEntity());
        final BorrowingEntity borrowingEntity = TestData.testBorrowingEntity(TestData.testBookEntity(),TestData.testPatronEntity());

        when(borrowingRepo.findById(eq(borrowing.getId()))).thenReturn(Optional.of(borrowingEntity));

        BorrowingId id = new BorrowingId(TestData.testPatron().getId(),TestData.testBook().getId());
        final Borrowing result = borrowingServiceTest.findById(borrowing.getId());
        assertEquals(borrowing,result);
    }



}
