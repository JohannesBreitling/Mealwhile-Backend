package de.johannesbreitling.mealwhile.auth;

import de.johannesbreitling.mealwhile.business.model.exceptions.BadRequestException;
import de.johannesbreitling.mealwhile.business.model.exceptions.EntityNotFoundException;
import de.johannesbreitling.mealwhile.business.repositories.UserGroupRepository;
import de.johannesbreitling.mealwhile.config.JwtService;
import de.johannesbreitling.mealwhile.business.model.user.Role;
import de.johannesbreitling.mealwhile.business.model.user.User;
import de.johannesbreitling.mealwhile.business.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final UserGroupRepository userGroupRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    public IAuthResponse register(RegisterRequest request) {

        // Try to get the user group from the repository
        var userGroup = userGroupRepository.findGroupById(request.getUserGroupId());

        if (userGroup.isEmpty()) {
            throw new EntityNotFoundException("User group with id " + request.getUserGroupId());
        }

        User user = User
                .builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .group(userGroup.get())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);

        return AuthResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse authenticate(AuthRequest request) {

        try {
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            // User is now authenticated
            var user = userRepository.findUserByUsername(request.getUsername());

            var jwtToken = jwtService.generateToken(user.get());

            return AuthResponse
                    .builder()
                    .token(jwtToken)
                    .build();
        } catch (Exception e) {
            // Username or password is wrong
            var user = userRepository.findUserByUsername(request.getUsername());

            if (user.isEmpty()) {
                throw new EntityNotFoundException("User with username " + request.getUsername());
            }
            throw new BadRequestException("Password for user " + user.get().getUsername() + " is wrong");
        }
    }

}
