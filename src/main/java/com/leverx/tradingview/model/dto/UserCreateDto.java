package com.leverx.tradingview.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {

    private String firstName;

    private String lastName;

    private String password;

    private String email;

}
