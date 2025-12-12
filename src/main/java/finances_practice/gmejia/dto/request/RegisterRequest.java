package finances_practice.gmejia.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;

    @NotBlank(message = "El Apellido es obligatorio")
    private String lastName;

    @NotNull(message = "El genero es obligatorio")
    private Integer genderId;

    private String phone;

    @Email(message = "El formato de email no es valido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Size(min = 6, max = 255)
    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotNull(message = "El pais es obligatorio")
    private Integer countryId;

}
