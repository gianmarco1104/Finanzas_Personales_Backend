package finances_practice.gmejia.controller;

import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.UserResponse;
import finances_practice.gmejia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<GeneralResponse> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(Authentication authentication){
        String currentEmail = authentication.getName();
        return ResponseEntity.ok(userService.getMyProfile((currentEmail)));
    }
}
