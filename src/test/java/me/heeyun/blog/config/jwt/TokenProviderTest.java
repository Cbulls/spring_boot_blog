package me.heeyun.blog.config.jwt;

import io.jsonwebtoken.Jwts;
import me.heeyun.blog.domain.User;
import me.heeyun.blog.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProperties jwtProperties;

    // generateToken 검증 테스트
    @DisplayName("generateToken : 유저 정보와 만료 기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given : 테스트 유저 생성
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        // when : 토큰 생성
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then : jwt 라이브러리를 사용해 토큰을 복호화하고, 토큰을 만들 때 클레임으로 넣어둔 id 값이 테스트 유저 생성할 때 만든 유저 ID와 동일한지 확인
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @DisplayName("validToken(): 만료된 토큰일 때 유효성 검증에 실패한다.")
    @Test
    void validToken_invalidToken(){
        // given : jwt 라이브러리를 사용해 이미 만료된 토큰을 생성
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build().createToken(jwtProperties);

        // when : validToken 메서드를 호출해서 token 유효성 검사를 한 뒤 결과값을 반환받는다.
        boolean result = tokenProvider.validToken(token);

        // then : 반환값이 false(유효한 토큰이 아님)인 것을 확인
        assertThat(result).isFalse();
    }

    @DisplayName("validToken(): 유효한 토큰인 때에 유효성 검증에 성공한다.")
    @Test
    void validToken_validToken() {
        // given : jwt 라이브러리를 사용해 만료되지 않은 토큰을 생성
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        // when : 토큰 제공자의 validToken 메서드를 호출해 유효한 토큰인지 검증한 뒤 결과값을 반환
        boolean result = tokenProvider.validToken(token);

        // then : 반환값이 true(유효한 토큰)인 것을 확인
        assertThat(result).isTrue();
    }

    // 토큰을 전달받아 인증 정보를 담은 객체 Authentication을 반환하는 메서드인 getAuthentication을 테스트
    @DisplayName("getAuthentication : 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }
}