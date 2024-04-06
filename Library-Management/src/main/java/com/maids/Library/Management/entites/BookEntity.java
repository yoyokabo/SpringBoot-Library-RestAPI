package com.maids.Library.Management.entites;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="books")
public class BookEntity { //Representation of Data in object form and is used for Hibernate

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Title can not be empty")
    private String title;

    @NotNull(message = "Author can not be empty")
    private String author;

    @NotNull(message = "Isbn can not be empty")
    @Size(max = 17,min = 10,message = "Invalid Isbn")
    private String isbn;

    private Integer pubyear;







}
