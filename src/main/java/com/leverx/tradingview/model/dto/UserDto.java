package com.leverx.tradingview.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;

    private String firstName;

    private String lastName;

    private String password;

    private String email;

    private Double rating;

    private Date createdAt;

    private String role;

}
