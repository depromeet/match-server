package com.depromeet.match.core.auth;

import com.depromeet.match.core.auth.request.AuthRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationManager am;

    public AuthenticationController(AuthenticationManager am) {
        this.am = am;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(
        @RequestBody AuthRequest req
    ) {
        return ResponseEntity.ok().build();
    }
}
