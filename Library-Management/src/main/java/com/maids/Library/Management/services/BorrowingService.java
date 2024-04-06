package com.maids.Library.Management.services;

import com.maids.Library.Management.composite.BorrowingId;
import com.maids.Library.Management.entites.Borrowing;

public interface BorrowingService {

    Borrowing createBorrowing(Borrowing borrowing);

    boolean isExistBorrowing(BorrowingId id);


    Borrowing findById(BorrowingId id);


}
