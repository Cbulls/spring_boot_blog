package me.heeyun.blog.dto;
// DTO : Data Transfer Object - 계층끼리 데이터를 교환하기 위해 사용하는 객체
// DAO : Data Access Object - 데이터베이스와 연결되고 데이터를 조회하고 수정하는데 사용하는 객체

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.heeyun.blog.domain.Article;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor // 모든 필드 값을 파라미터로 받는 생성자 추가
@Getter
public class AddArticleRequest {

    private String author;
    private String title;
    private String content;

    public Article toEntity(String author){ // DTO를 엔티티로 만들어주는 메서드
        return Article.builder().author(author).title(title).content(content).build();
    }
}