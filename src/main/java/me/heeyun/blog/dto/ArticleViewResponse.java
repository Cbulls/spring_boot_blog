package me.heeyun.blog.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.heeyun.blog.domain.Article;

import java.time.LocalDateTime;

// Thymeleaf로 글 상세 페이지 뷰에서 사용할 DTO
@NoArgsConstructor
@Getter
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String author;

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.author = article.getAuthor();;
    }
}