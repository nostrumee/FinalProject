package com.leverx.tradingview.controller;

import com.leverx.tradingview.model.dto.CommentCreateDto;
import com.leverx.tradingview.model.dto.CommentDto;
import com.leverx.tradingview.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/articles/{id}/comments")
    public List<CommentDto> getComments(@PathVariable Long id) {
        return commentService.getAllCommentsByArticleId(id);
    }

    @GetMapping("/articles/{articleId}/comments/{commentId}")
    public CommentDto getComment(@PathVariable Long articleId, @PathVariable Long commentId) {
        return commentService.getCommentByIdAndArticleId(commentId, articleId);
    }

    @PostMapping("/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long articleId, @RequestBody CommentCreateDto comment) throws URISyntaxException {
        CommentDto commentDto = commentService.createComment(articleId, comment);
        return ResponseEntity.created(getCommentUri(articleId, commentDto.getId())).body(commentDto);
    }

    private URI getCommentUri(Long articleId, Long id) throws URISyntaxException {
        return new URI(String.format("/articles/%d/comments/%d", articleId, id));
    }

    @DeleteMapping("/articles/{articleId}/comments/{commentId}")
    public CommentDto deleteComment(@PathVariable Long commentId, @PathVariable Long articleId) {
        return commentService.deleteComment(commentId, articleId);
    }

}
