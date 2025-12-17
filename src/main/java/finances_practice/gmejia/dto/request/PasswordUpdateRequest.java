package finances_practice.gmejia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordUpdateRequest {

    @NotBlank(message = "La contrasena actual es necesaria para el proceso")
    @NotBlank @Size(min = 6, max = 255)
    private String currentPassword;

    @NotBlank(message = "La contrasena nueva es obligatoria para el proceso")
    @NotBlank @Size(min = 6, max = 255)
    private String newPassword;
}
