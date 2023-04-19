package de.johannesbreitling.mealwhile.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/register")
    public ResponseEntity<IAuthResponse> register(
            @RequestBody RegisterRequest request
    ) {

        if (request.getUsername() == null
                || request.getFirstname() == null
                || request.getLastname() == null
                || request.getPassword() == null
        ) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new AuthError("Missing arguments to register."));
        }

        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<IAuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {

        if (request.getUsername() == null
                || request.getPassword() == null
        ) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new AuthError("Missing arguments to authenticate."));
        }

        return ResponseEntity.ok(service.authenticate(request));
    }



}
