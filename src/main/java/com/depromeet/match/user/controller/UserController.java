package com.depromeet.match.user.controller;

import com.depromeet.match.core.jwt.JwtAuthentication;
import com.depromeet.match.user.User;
import com.depromeet.match.user.User.UserBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/users")
@RestController
public class UserController {


    @GetMapping("/all")
    public ResponseEntity<?> all(
        @AuthenticationPrincipal JwtAuthentication jwtAuthentication
    ){
        User user = UserBuilder.anUser()
            .id(jwtAuthentication.getId())
            .nickName(jwtAuthentication.getNickName())
            .profileImageUrl(jwtAuthentication.getProfileImageUrl())
            .build();
        return ResponseEntity.ok(user);
    }

}
