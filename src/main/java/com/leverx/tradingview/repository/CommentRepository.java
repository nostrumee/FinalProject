package com.leverx.tradingview.repository;

import com.leverx.tradingview.model.jpa.Article;
import com.leverx.tradingview.model.jpa.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticleId(Article article);

    Optional<Comment> findById(Long id);

    void deleteById(Long id);

}
