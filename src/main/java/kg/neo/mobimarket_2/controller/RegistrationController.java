package kg.neo.mobimarket_2.controller;

import io.swagger.annotations.Api;
import kg.neo.mobimarket_2.request.AuthRequest;
import kg.neo.mobimarket_2.request.CheckRequest;
import kg.neo.mobimarket_2.request.RegisterRequest;
import kg.neo.mobimarket_2.response.AuthResponse;
import kg.neo.mobimarket_2.response.CheckResponse;
import kg.neo.mobimarket_2.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static kg.neo.mobimarket_2.configuration.SwaggerConfig.REGISTRATION;

@Api(tags = REGISTRATION)
@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {
    private final RegistrationService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/log")
    public ResponseEntity<AuthResponse> username(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.username(request));
    }

    @GetMapping("/credentialsCheck")
    public ResponseEntity<CheckResponse> check(@RequestBody CheckRequest request){
        return ResponseEntity.ok(authService.checkAvailability(request));
    }

}
