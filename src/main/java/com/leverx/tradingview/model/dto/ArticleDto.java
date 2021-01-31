package com.leverx.tradingview.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDto {

    private Long id;

    private String title;

    private String text;

    private Long userId;

    private Date createdAt;

    private Date updatedAt;

}
