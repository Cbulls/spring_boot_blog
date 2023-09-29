package me.heeyun.blog.controller;

import lombok.RequiredArgsConstructor;
import me.heeyun.blog.dto.CreateAccessTokenRequest;
import me.heeyun.blog.dto.CreateAccessTokenResponse;
import me.heeyun.blog.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// /api/token POST 요청이 오면 토큰 서비스에서 리프레시 토큰을 기반으로 새로운 액세스 토큰을 생성
@RequiredArgsConstructor
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(
            @RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateAccessTokenResponse(newAccessToken));
    }
}
