package com.leverx.tradingview.service.impl;

import com.leverx.tradingview.model.dto.CommentCreateDto;
import com.leverx.tradingview.model.dto.CommentDto;
import com.leverx.tradingview.model.jpa.Article;
import com.leverx.tradingview.model.jpa.Comment;
import com.leverx.tradingview.repository.ArticleRepository;
import com.leverx.tradingview.repository.CommentRepository;
import com.leverx.tradingview.service.CommentService;
import com.leverx.tradingview.service.exception.CommentNotFoundException;
import com.leverx.tradingview.service.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceImpl.class);

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final ModelMapper mapper;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, ArticleRepository articleRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.articleRepository = articleRepository;
        this.mapper = mapper;
    }

    @Override
    public List<CommentDto> getAllCommentsByArticleId(Long articleId) {
        LOGGER.info("Retrieving all comments by article_id {}", articleId);
        Article article = articleRepository.findById(articleId).orElseThrow(UserNotFoundException::new);
        return commentRepository.findAllByArticleId(article)
                .stream()
                .map(comment -> mapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public CommentDto getCommentByIdAndArticleId(Long id, Long articleId) {
        LOGGER.info("Retrieving comment by id {} and article_id {}", id, articleId);
        articleRepository.findById(articleId).orElseThrow(UserNotFoundException::new);
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        return mapper.map(comment, CommentDto.class);
    }

    @Override
    public CommentDto createComment(Long articleId, CommentCreateDto commentCreateDto) {
        LOGGER.info("Creating comment by article_id {}", articleId);
        Article article = articleRepository.findById(articleId).orElseThrow(UserNotFoundException::new);
        Comment comment = createComment(article, commentCreateDto);
        Comment createdComment = commentRepository.save(comment);
        return mapper.map(createdComment, CommentDto.class);
    }

    private Comment createComment(Article article, CommentCreateDto commentCreateDto) {
        Comment comment = new Comment();
        comment.setArticleId(article);
        comment.setMessage(commentCreateDto.getMessage());
        return comment;
    }

    @Override
    public CommentDto deleteComment(Long id, Long articleId) {
        LOGGER.info("Deleting comment by id {} and article_id {}", id, articleId);
        CommentDto commentDto = getCommentByIdAndArticleId(id, articleId);
        commentRepository.deleteById(id);
        return commentDto;
    }

}