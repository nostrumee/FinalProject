package com.leverx.tradingview.service;

import com.leverx.tradingview.model.dto.ArticleCreateDto;
import com.leverx.tradingview.model.dto.ArticleDto;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getAllArticles();

    List<ArticleDto> getMyArticles();

    ArticleDto createArticle(ArticleCreateDto articleCreateDto);

    void deleteArticle(Long id);

    ArticleDto updateArticle(Long id, ArticleDto articleDto);

}
