package me.heeyun.blog.service;

import lombok.RequiredArgsConstructor;
import me.heeyun.blog.domain.Article;
import me.heeyun.blog.dto.AddArticleRequest;
import me.heeyun.blog.dto.UpdateArticleRequest;
import me.heeyun.blog.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor // final이 붙거나 @NotNull이 붙은 필드의 생성자 추가 - 빈을 생성자로 생성함 (롬북)
@Service // 빈으로 서블릿 컨테이너에 등록
public class BlogService {
    private final BlogRepository blogRepository;

    // 블로그 글 추가 메서드
    public Article save(AddArticleRequest request, String userName){
        return blogRepository.save(request.toEntity(userName));
    }

    // 데이터베이스에 저장되어 있는 모든 글 불러오는 메서드
    public List<Article> findAll(){
        return blogRepository.findAll();
    }

    // 글 하나를 조회하는 메서드
    // ID를 받아 엔티티를 조회하고 없으면 IllegalArgumentException 예외를 발생 시킨다.
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: "+ id));
    }

    // 글 삭제
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authorizeArticleAuthor(article);
        blogRepository.deleteById(id);
    }

    // 글 수정
    @Transactional // 매칭한 메서드를 하나의 트랜잭션으로 묶는 역할
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : "+ id));

        article.update(request.getTitle(), request.getContent());

        return article;
    }

    // 게시글을 작성한 유저인지 확인
    // 글을 수정하거나 삭제할 때 요청 헤더에 토큰을 전달하므로 토큰을 사용해 자신이 작성한 글인지 검증할 수 있다.
    // 본인 글이 아닌데 수정, 삭제 시도하는 경우에 예외를 발생시킨다
    private static void  authorizeArticleAuthor(Article article){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        if(!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}