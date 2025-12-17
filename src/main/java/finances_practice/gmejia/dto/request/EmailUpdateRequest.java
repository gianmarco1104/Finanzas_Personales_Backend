package finances_practice.gmejia.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailUpdateRequest {

    @NotBlank(message = "El correo actual es obligatorio")
    @Email(message = "El formato del correo no es valido")
    private String currentEmail;

    @NotBlank(message = "El nuevo correo es obligatorio")
    @Email(message = "El formato del correo no es valido")
    private String newEmail;

    @NotBlank(message = "La contrasena es obligatoria para confirmar el cambio")
    @NotBlank @Size(min = 6, max = 255)
    private String currentPassword;
}
