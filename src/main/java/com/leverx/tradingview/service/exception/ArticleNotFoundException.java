package com.leverx.tradingview.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Article not found")
public class ArticleNotFoundException extends RuntimeException{
}
