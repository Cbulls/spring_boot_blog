package me.heeyun.blog.repository;

import me.heeyun.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // email로 사용자 정보를 가져옴

    // 스프링 데이터 JPA는 메서드 규칙에 맞춰 메서드를 선언하면 이름을 분석해 자동으로 쿼리를 생성한다
    // findByEmail 메서드는 FROM users WHERE email = #{email} 쿼리를 실행한다
}