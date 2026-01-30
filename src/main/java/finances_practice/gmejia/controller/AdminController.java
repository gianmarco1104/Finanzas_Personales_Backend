package finances_practice.gmejia.controller;

import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.ListUsersResponse;
import finances_practice.gmejia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;

    @GetMapping("/list/users/{status}")
    public ResponseEntity<@NonNull List<ListUsersResponse>> getUsersByStatus(@PathVariable Boolean status){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUsersByStatus(status));
    }

    @PatchMapping("/user/activate/{id}")
    public ResponseEntity<@NonNull GeneralResponse> activate(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.userActivate(id));
    }

    @PatchMapping("/user/desactivate/{id}")
    public ResponseEntity<@NonNull GeneralResponse> inactive(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.userDesactivate(id));
    }
}
