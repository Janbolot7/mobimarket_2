package kg.neo.mobimarket_2.service;

import kg.neo.mobimarket_2.configuration.JwtService;
import kg.neo.mobimarket_2.model.Role;
import kg.neo.mobimarket_2.model.User;
import kg.neo.mobimarket_2.repository.UserRepository;
import kg.neo.mobimarket_2.request.AuthRequest;
import kg.neo.mobimarket_2.request.CheckRequest;
import kg.neo.mobimarket_2.request.RegisterRequest;
import kg.neo.mobimarket_2.response.AuthResponse;
import kg.neo.mobimarket_2.response.CheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    public AuthResponse register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return AuthResponse.builder()
                    .message("Email is already registered")
                    .build();
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return AuthResponse.builder()
                    .message("Username is already registered")
                    .build();
        }


        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .enabled(true)
                .verified(false)
                .build();
        if (user == null){
            throw new IllegalStateException("bad credentials");
        }

        userRepository.save(user);

        return AuthResponse.builder()
                .message("now you registered")
                .build();
    }

    public AuthResponse username(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                ));
        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public CheckResponse checkAvailability(CheckRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            return CheckResponse.builder()
                    .message("Email is already taken")
                    .userPresent(false)
                    .build();
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return CheckResponse.builder()
                    .message("Username is already taken")
                    .userPresent(false)
                    .build();
        }

        return CheckResponse.builder()
                .message("Email and Username is new !")
                .userPresent(true)
                .build();
    }
}
