package me.heeyun.blog.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.heeyun.blog.config.jwt.TokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 토큰 필터는 실제로 각종 요청을 처리하는 서비스 로직에 가기 전후에 URL 패턴에 맞는 모든 요청을 처리
// 요청이 오면 헤더값을 비교해서 토큰의 유무를 확인
// 유효 토큰이라면 시큐리티 콘텍스트 홀더에 인증 정보를 저당
// 시큐리티 컨텍스트(Security Context) : 인증 객체가 저장되는 보관소로 인증 정보가 필요하면 언제든 꺼내 사용 가능
// 이 클래스는 스레드마다 공간을 할당하는 즉, 스레드 로컬(thread local)에 저장되기 때문에 코드의 아무 곳에서 사용 가능하고,
// 다른 스레드와 공유하지 않아서 독립적으로 사용 가능하다.
// 이러한 시큐리티 컨택스트 객체를 저장하는 객체가 시큐리티 컨택스트 홀더
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;
    private final static String HEADER_AUTHORIZATION = "Authorization";
    private final static String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain ) throws ServletException, IOException {
        // 요청 헤더의 Authorization 키의 값 조회
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        // 가져온 값에서 접두사 Bearer 제거
        String token = getAccessToken(authorizationHeader);
        // 가져온 토큰이 유효한지 확인하고, 유효한 때는 인증 정보 설정
        if(tokenProvider.validToken(token)){
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    // 만약 값이 null이거나 Bearer로 시작하지 않으면 null을 반환한다.
    private String getAccessToken(String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)){
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
