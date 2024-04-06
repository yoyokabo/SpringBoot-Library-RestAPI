package com.maids.Library.Management.repositories;

import com.maids.Library.Management.entites.BookEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepo extends JpaRepository<BookEntity,Long> { //Implements CRUD persistance layer
}
