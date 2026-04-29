package com.preventhub.identity.controller;

import com.preventhub.identity.dto.AuthResponse;
import com.preventhub.identity.dto.LoginRequest;
import com.preventhub.identity.dto.UserView;
import com.preventhub.identity.service.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @GetMapping("/me")
    public UserView currentUser(@RequestHeader(value = "X-Auth-User", required = false) String proxiedUser,
                                @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        String email = proxiedUser != null ? proxiedUser : extractEmailFromBasicAuth(authorization);
        return authService.findByEmail(email);
    }

    @GetMapping("/users")
    public List<UserView> users() {
        return authService.findAll();
    }

    private String extractEmailFromBasicAuth(String authorization) {
        if (authorization == null || authorization.isBlank()) {
            throw new IllegalArgumentException("Missing user context");
        }
        return authorization;
    }
}
