package finances_practice.gmejia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdateRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;

    @NotBlank(message = "El Apellido es obligatorio")
    private String lastName;

    @NotNull(message = "El genero es obligatorio")
    private Integer genderId;

    private String phone;

    @NotNull(message = "El pais es obligatorio")
    private Integer countryId;
}
