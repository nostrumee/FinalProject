package com.leverx.tradingview.service.impl;

import com.leverx.tradingview.model.dto.ArticleCreateDto;
import com.leverx.tradingview.model.dto.ArticleDto;
import com.leverx.tradingview.model.jpa.Article;
import com.leverx.tradingview.repository.ArticleRepository;
import com.leverx.tradingview.repository.UserRepository;
import com.leverx.tradingview.service.ArticleService;
import com.leverx.tradingview.service.exception.AccessDeniedException;
import com.leverx.tradingview.service.exception.ArticleNotFoundException;
import com.leverx.tradingview.service.exception.UserNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleServiceImpl.class);

    private final ArticleRepository articleRepository;
    private final ModelMapper mapper;
    private final UserRepository userRepository;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper mapper, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.mapper = mapper;
        this.userRepository = userRepository;
    }

    @Override
    public List<ArticleDto> getAllArticles() {
        LOGGER.info("Retrieving all public articles");
        return articleRepository.findAll()
                .stream()
                .map(article -> mapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<ArticleDto> getMyArticles() {
        LOGGER.info("Retrieving articles of user");
        return articleRepository.findByUserId(getCurrentUserId())
                .stream()
                .map(article -> mapper.map(article, ArticleDto.class))
                .collect(Collectors.toList());
    }



    @Override
    public ArticleDto createArticle(ArticleCreateDto articleCreateDto) {
        LOGGER.info("Creating article");
        Article article = mapper.map(articleCreateDto, Article.class);
        article.setUserId(getCurrentUserId());
        return mapper.map(articleRepository.save(article), ArticleDto.class);
    }

    @Override
    public void deleteArticle(Long id) {
        LOGGER.info("Deleting article");
        checkPermission(id);
        articleRepository.deleteById(id);
    }

    @Override
    public ArticleDto updateArticle(Long id, ArticleDto article) {
        LOGGER.info("Updating article");
        checkPermission(id);
        articleRepository.findById(id).orElseThrow(ArticleNotFoundException::new);
        article.setId(id);
        articleRepository.save(mapper.map(article, Article.class));
        return article;
    }

    private void checkPermission(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(ArticleNotFoundException::new);
        if (!getCurrentUserId().equals(article.getUserId())) {
            throw new AccessDeniedException();
        }
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return userRepository.findByEmail(authentication.getName())
                    .orElseThrow(UserNotFoundException::new)
                    .getId();
        }
        throw new UserNotFoundException();
    }
}