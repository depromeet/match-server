package com.depromeet.match.user.controller;

import com.depromeet.match.core.jwt.JwtAuthentication;
import com.depromeet.match.core.response.ApiResult;
import com.depromeet.match.user.User;
import com.depromeet.match.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResult<List<User>>> all(
        @AuthenticationPrincipal JwtAuthentication jwtAuthentication
    ){
        log.info("{}", jwtAuthentication);
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok(new ApiResult<>(all));
    }

}
