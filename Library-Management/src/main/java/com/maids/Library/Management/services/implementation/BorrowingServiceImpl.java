package com.maids.Library.Management.services.implementation;

import com.maids.Library.Management.composite.BorrowingId;
import com.maids.Library.Management.entites.BorrowingEntity;
import com.maids.Library.Management.entites.Borrowing;
import com.maids.Library.Management.repositories.BorrowingRepo;
import com.maids.Library.Management.services.BorrowingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class BorrowingServiceImpl implements BorrowingService {

    private BorrowingEntity borrowingToBorrowingEntity(Borrowing borrowing){
        return BorrowingEntity.builder()
                .bookId(borrowing.getBookId())
                .patronId(borrowing.getPatronId())
                .borrowingdate(borrowing.getBorrowingdate())
                .duedate(borrowing.getDuedate())
                .returndate(borrowing.getReturndate())
                .build();
    }

    private Borrowing borrowingEntityToBorrowing(BorrowingEntity borrowing){
        return Borrowing.builder()
                .bookId(borrowing.getBookId())
                .patronId(borrowing.getPatronId())
                .borrowingdate(borrowing.getBorrowingdate())
                .duedate(borrowing.getDuedate())
                .returndate(borrowing.getReturndate())
                .build();
    }

    private final BorrowingRepo borrowingRepo;

    @Autowired
    public BorrowingServiceImpl(BorrowingRepo borrowingRepo){
        this.borrowingRepo = borrowingRepo;
    }


    @Override
    public Borrowing findById(BorrowingId id) {

        Optional<BorrowingEntity> borrowing = borrowingRepo.findById(id);
        BorrowingEntity borrowings = borrowing.orElseThrow(() -> new RuntimeException("Borrowing not found"));
        return borrowingEntityToBorrowing(borrowings);
    }


    @Override
    public Borrowing createBorrowing(final Borrowing borrowing) {
        BorrowingEntity borrowingEntity = borrowingToBorrowingEntity(borrowing);
        BorrowingEntity savedBorrow = borrowingRepo.save(borrowingEntity);
        return borrowingEntityToBorrowing(savedBorrow);
    }

    @Override
    public boolean isExistBorrowing(BorrowingId id) {
        return borrowingRepo.existsById(id);
    }
}
