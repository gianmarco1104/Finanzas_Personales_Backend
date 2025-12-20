package finances_practice.gmejia.controller;

import finances_practice.gmejia.dto.request.EmailUpdateRequest;
import finances_practice.gmejia.dto.request.PasswordUpdateRequest;
import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.request.UpdateRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.UserResponse;
import finances_practice.gmejia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserResponse> getProfile(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMyProfile());
    }

    @PutMapping("/update")
    public ResponseEntity<GeneralResponse> update(@RequestBody @Valid UpdateRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(request));
    }

    @PatchMapping("/update/email")
    public ResponseEntity<GeneralResponse> updateEmail(@RequestBody @Valid EmailUpdateRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateEmailUser(request));
    }

    @PatchMapping("/update/password")
    public ResponseEntity<GeneralResponse> updatePassword(@RequestBody @Valid PasswordUpdateRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePasswordUser(request));
    }
}
