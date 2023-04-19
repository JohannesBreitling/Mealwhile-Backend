package de.johannesbreitling.mealwhile.auth;

import de.johannesbreitling.mealwhile.config.JwtService;
import de.johannesbreitling.mealwhile.business.model.user.Role;
import de.johannesbreitling.mealwhile.business.model.user.User;
import de.johannesbreitling.mealwhile.business.model.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public IAuthResponse register(RegisterRequest request) {

        User user = User
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                //.group()
                .role(Role.USER)
                .build();

        repository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public IAuthResponse authenticate(AuthRequest request) {

        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );

        // User is now authenticated
        var user = repository
                .findUserByUsername(request.getUsername())
                // TODO throw an appropriate exception
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse
                .builder()
                .token(jwtToken)
                .build();
    }

}
