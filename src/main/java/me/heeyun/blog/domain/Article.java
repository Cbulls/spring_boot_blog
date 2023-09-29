package me.heeyun.blog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 엔티티로 지정
@Getter // get 메서드 대체
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected 대체
@Entity
public class Article {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "title", nullable = false) // 'title'이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate // 엔티티가 생성될 때 처음 생성 시간 저장
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate // 엔티티가 수정될 때 마지막으로 수정된 시간 저장
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder // 빌더 패턴으로 객체 생성 - 롬복에서 지원
    public Article(String author, String title, String content){
        this.author = author;
        this.title = title;
        this.content = content;
    }
// 만약 빌더 패턴을 쓰지 않았을 때
//    protected Article(){ // 기본 생성자
//
//    }
//
//    public Long getId(){
//        return id;
//    }
//
//    public String getTitle(){
//        return title;
//    }
//
//    public String getContent(){
//        return content;
//    }

    // 글 수정
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}