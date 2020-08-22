package com.depromeet.match.login.controller;

import com.depromeet.match.core.response.ApiResult;
import com.depromeet.match.login.service.KakaoOAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    @Value("${kakao.provider.login-url}")
    private String kakaoLoginUrl;

    private final KakaoOAuthService kakaoOAuthService;

    public LoginController(KakaoOAuthService kakaoOAuthService){
        this.kakaoOAuthService = kakaoOAuthService;
    }

    /**
     * 카카오 로그인 url
     */
    @GetMapping("/login")
    public ResponseEntity<ApiResult<String>> login() {
        log.info("url : {}", kakaoLoginUrl);
        return ResponseEntity.ok(new ApiResult<>(kakaoLoginUrl));
    }

    /**
     * jwt
     */
    @GetMapping("/kakao/redirect")
    public ResponseEntity<ApiResult<String>> jwt(@RequestParam("code") String code) {
        log.info("code : {}", code);
        String jwt = kakaoOAuthService.jwt(code);
        log.info("controller jwt : {}", jwt);

        return ResponseEntity.ok(new ApiResult<>(jwt));
    }

}

