package com.leverx.tradingview.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long id;

    private String message;

    private Long articleId;

    private Long authorId;

    private Date createdAt;

    private Boolean approved;

}
