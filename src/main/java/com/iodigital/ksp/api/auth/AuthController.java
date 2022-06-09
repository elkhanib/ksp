package com.iodigital.ksp.api.auth;

import com.iodigital.ksp.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/sign-in")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Object> signIn(Object request) {
        return new ResponseEntity<>(authService.signIn(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/auth/sign-out")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void signOut(@RequestHeader String token) {
        authService.signOut(token);
    }

    @PutMapping("/auth/refresh")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Object> refreshToken(@RequestHeader String token) {
        return ResponseEntity.ok(authService.refreshToken(token));
    }
}
