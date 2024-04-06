package com.maids.Library.Management.repositories;

import com.maids.Library.Management.composite.BorrowingId;
import com.maids.Library.Management.entites.BookEntity;
import com.maids.Library.Management.entites.BorrowingEntity;
import com.maids.Library.Management.entites.PatronEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowingRepo extends JpaRepository<BorrowingEntity, BorrowingId> {

}
