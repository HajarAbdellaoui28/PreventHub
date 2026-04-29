package com.preventhub.identity.service;

import com.preventhub.identity.domain.UserAccount;
import com.preventhub.identity.dto.AuthResponse;
import com.preventhub.identity.dto.LoginRequest;
import com.preventhub.identity.dto.UserView;
import com.preventhub.identity.repository.UserAccountRepository;
import com.preventhub.identity.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserAccountRepository repository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse login(LoginRequest request) {
        UserAccount userAccount = repository.findByEmailIgnoreCase(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.password(), userAccount.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return new AuthResponse(
                jwtService.generate(userAccount),
                "Bearer",
                jwtService.getExpirationSeconds(),
                new AuthResponse.UserSummary(
                        userAccount.getId(),
                        userAccount.getFullName(),
                        userAccount.getEmail(),
                        userAccount.getOrganization(),
                        userAccount.getRoles().stream().map(Enum::name).sorted().toList()
                )
        );
    }

    public UserView findByEmail(String email) {
        UserAccount userAccount = repository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toView(userAccount);
    }

    public List<UserView> findAll() {
        return repository.findAll().stream().map(this::toView).toList();
    }

    private UserView toView(UserAccount userAccount) {
        return new UserView(
                userAccount.getId(),
                userAccount.getFullName(),
                userAccount.getEmail(),
                userAccount.getOrganization(),
                userAccount.getRoles().stream().map(Enum::name).sorted().toList()
        );
    }
}
