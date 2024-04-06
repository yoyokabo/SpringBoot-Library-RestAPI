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
public class Patron {

    private Long id;

    @NotNull(message = "Name Can Not be Empty")
    private String name;

    @Size(min = 10,max = 15,message = "Invalid Phone Number")
    private String phonenumber;


    private String address;

}
