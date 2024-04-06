package com.maids.Library.Management.entites;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {   //Book Representation for the service layer abstraction for later extensions


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
