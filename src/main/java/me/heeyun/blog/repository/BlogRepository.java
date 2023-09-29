package me.heeyun.blog.repository;

import me.heeyun.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> { // Long은 PK 타입
    
}