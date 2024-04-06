package com.maids.Library.Management.entites;


import com.maids.Library.Management.composite.BorrowingId;
import com.maids.Library.Management.entites.BookEntity;
import com.maids.Library.Management.entites.PatronEntity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.message.Message;


import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
@Builder
public class Borrowing {


    private Long patronId;

    private Long bookId;

    private Date borrowingdate;

    private Date duedate;

    private Date returndate;

    private BorrowingId id = new BorrowingId(this.patronId,this.bookId);




}


