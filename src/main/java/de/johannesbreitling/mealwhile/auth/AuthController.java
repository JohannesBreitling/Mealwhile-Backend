package de.johannesbreitling.mealwhile.auth;

import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.services.AdminService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    private final AdminService adminService;

    @PostMapping("/register")
    public ResponseEntity<IAuthResponse> register(
            @RequestBody RegisterRequest request
    ) {
        if (request.getUsername() == null
                || request.getFirstname() == null
                || request.getLastname() == null
                || request.getPassword() == null
                || request.getUserGroupId() == null
        ) {
            throw new BadRequestException("Missing arguments to authenticate.");
        }

        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/checkUsername/{username}")
    public ResponseEntity<Boolean> getUsernameAvailability(@PathVariable String username) {
        if (username == null) {
            throw new BadRequestException("Provide a username you want to check");
        }

        return ResponseEntity.ok(adminService.checkUsername(username));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody AuthRequest request
    ) {
        if (request.getUsername() == null || request.getPassword() == null) {
            throw new BadRequestException("Missing arguments to authenticate.");
        }

        var token = service.authenticate(request);
        return ResponseEntity.ok(token);

    }

}
