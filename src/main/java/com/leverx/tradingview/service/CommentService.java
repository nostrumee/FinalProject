package com.leverx.tradingview.service;

import com.leverx.tradingview.model.dto.CommentCreateDto;
import com.leverx.tradingview.model.dto.CommentDto;

import java.util.List;

public interface CommentService {

    List<CommentDto> getAllCommentsByArticleId(Long articleId);

    CommentDto getCommentByIdAndArticleId(Long id, Long articleId);

    CommentDto createComment(Long articleId, CommentCreateDto commentCreateDto);

    CommentDto deleteComment(Long id, Long articleId);

}
