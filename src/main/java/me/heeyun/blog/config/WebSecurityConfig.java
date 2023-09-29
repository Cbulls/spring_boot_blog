
// OAuth 사용을 위해 모두 주석 처리
//package me.heeyun.blog.config;
//
//import lombok.RequiredArgsConstructor;
//import me.heeyun.blog.service.UserDetailService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
//
//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig {
//
//    private final UserDetailService userService;
//
//    // 스프링 시큐리티 기능 비활성화
//    @Bean
//    public WebSecurityCustomizer configure() {
//        return (web -> web.ignoring()
//                    .requestMatchers(toH2Console())
//                    .requestMatchers("/static/**"));
//    }
//
//    // 특정 HTTP 요청에 대한 웹 기반 보안 구성
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeRequests()
//                .requestMatchers("/login", "/signup", "/user").permitAll() // /login, /signup, /user로 요청이 오면 인증, 인가 없이도 접근할 수 있다.
//                .anyRequest().authenticated()
//                .and()
//                .formLogin() // 폼 기반 로그인 설정
//                .loginPage("/login") // 로그인 페이지 경로 설정
//                .defaultSuccessUrl("/articles") // 로그인이 완료되었을 때 이동할 경로 설정
//                .and()
//                .logout() // 로그아웃 설정
//                .logoutSuccessUrl("/login") // 로그아웃이 완료되었을 때 이동할 경로 설정
//                .invalidateHttpSession(true) // 로그아웃 이후에 세션을 전체 삭제할지 여부를 설정
//                .and().build();
//    }
//
//    // 인증 관리자 관련 설정
//    @Bean
//    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder,
//    UserDetailService userDetailService) throws Exception {
//        return http.getSharedObject(AuthenticationManagerBuilder.class)
//                .userDetailsService(userService) // 사용자 정보 서비스 설정
//                .passwordEncoder(bCryptPasswordEncoder) // 패스워드 암호화
//                .and().build();
//    }
//
//    // 패스워드 인코더를 빈으로 등록
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
