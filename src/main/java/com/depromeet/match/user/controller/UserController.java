package com.depromeet.match.user.controller;

import com.depromeet.match.core.jwt.JwtAuthentication;
import com.depromeet.match.core.response.ApiResult;
import com.depromeet.match.user.User;
import com.depromeet.match.user.dto.UpdateUserRequest;
import com.depromeet.match.user.repository.UserRepository;
import com.depromeet.match.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PutMapping
    public ResponseEntity<ApiResult<User>> update(
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication,
            @RequestBody final UpdateUserRequest request
    ) {
        User update = userService.update(jwtAuthentication.getId(), request);
        return ResponseEntity.ok(new ApiResult<>(update));
    }

    /**
     * test 용도
     */
    @GetMapping("/all")
    public ResponseEntity<ApiResult<List<User>>> all(
            @AuthenticationPrincipal JwtAuthentication jwtAuthentication
    ) {
        log.info("{}", jwtAuthentication);
        List<User> all = userRepository.findAll();
        return ResponseEntity.ok(new ApiResult<>(all));
    }

}
