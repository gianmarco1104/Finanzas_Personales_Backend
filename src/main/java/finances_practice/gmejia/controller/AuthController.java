package finances_practice.gmejia.controller;

import finances_practice.gmejia.dto.request.LoginRequest;
import finances_practice.gmejia.dto.response.AuthResponse;
import finances_practice.gmejia.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reactivate")
    public ResponseEntity<AuthResponse> reactivate(@RequestBody @Valid LoginRequest request){
        AuthResponse response = authService.reactivate(request);
        return ResponseEntity.ok(response);
    }
}
