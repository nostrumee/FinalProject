package com.leverx.tradingview.controller;

import com.leverx.tradingview.model.dto.ArticleCreateDto;
import com.leverx.tradingview.model.dto.ArticleDto;
import com.leverx.tradingview.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/articles")
    public List<ArticleDto> getPublicArticles() {
        return articleService.getAllArticles();
    }

    @GetMapping("/my")
    public List<ArticleDto> getMyArticles() {
        return articleService.getMyArticles();
    }

    @PostMapping("/articles")
    public ResponseEntity<ArticleDto> createArticle(@RequestBody ArticleCreateDto article) {
        ArticleDto articleDto = articleService.createArticle(article);
        return ResponseEntity.ok(articleDto);
    }

    @DeleteMapping("/articles/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }

    @PutMapping("/articles/{id}")
    public ArticleDto updateArticle(@PathVariable Long id, @RequestBody ArticleDto article) {
        return articleService.updateArticle(id, article);
    }

}