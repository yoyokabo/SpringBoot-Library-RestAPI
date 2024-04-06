package com.maids.Library.Management.entites;


import com.maids.Library.Management.composite.BorrowingId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(BorrowingId.class)
@Table(name="borrowing")
public class BorrowingEntity {

    @Id
    private Long patronId;

    @Id
    private Long bookId;

    private Date borrowingdate;

    private Date duedate;

    private Date returndate;

    private BorrowingId id = new BorrowingId(this.patronId,this.bookId);





    }

