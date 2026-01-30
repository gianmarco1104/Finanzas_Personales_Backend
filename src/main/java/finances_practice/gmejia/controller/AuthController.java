package finances_practice.gmejia.controller;

import finances_practice.gmejia.dto.request.LoginRequest;
import finances_practice.gmejia.dto.request.ResendCodeRequest;
import finances_practice.gmejia.dto.request.VerifyCodeRequest;
import finances_practice.gmejia.dto.response.AuthResponse;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.service.AuthService;
import finances_practice.gmejia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<@NonNull AuthResponse> login(@RequestBody @Valid LoginRequest request){
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reactivate")
    public ResponseEntity<@NonNull AuthResponse> reactivate(@RequestBody @Valid LoginRequest request){
        AuthResponse response = authService.reactivate(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifyAccount")
    public ResponseEntity<@NonNull GeneralResponse> verify(@RequestBody @Valid VerifyCodeRequest request){
        return ResponseEntity.ok(userService.verifyUser(request));
    }

    @PostMapping("/resendCode")
    public ResponseEntity<@NonNull GeneralResponse> resendCode(@RequestBody @Valid ResendCodeRequest request){
        return ResponseEntity.ok(userService.resendVerificationCode(request));
    }
}
