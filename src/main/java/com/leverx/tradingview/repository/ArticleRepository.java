package com.leverx.tradingview.repository;

import com.leverx.tradingview.model.jpa.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findById(Long id);

    List<Article> findByUserId(Long id);

    List<Article> findAll();

    void deleteById(Long id);

}
