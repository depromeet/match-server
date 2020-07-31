package com.depromeet.match.core.auth;

import com.depromeet.match.core.auth.request.AuthRequest;
import com.depromeet.match.core.response.ApiResult;
import com.depromeet.match.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userRepository){
        this.userService = userRepository;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResult<String>> signIn(
        @RequestBody AuthRequest req
    ) {
        String jwt = userService.createJwt(req.getId(), req.getNickName(), req.getProfileImageUrl());
        return ResponseEntity.ok(new ApiResult<>(jwt));
    }
}
