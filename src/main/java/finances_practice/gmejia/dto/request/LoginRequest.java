package finances_practice.gmejia.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//De momento no se usa el AllArgsConstructor por el hecho de que no se estan haciendo pruebas unitarias desde codigo
@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
    @NotBlank @Size(max = 100) @Email
    private String email;

    @NotBlank @Size(min = 6, max = 255)
    private String password;
}
