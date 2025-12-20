package finances_practice.gmejia.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class TransactionRegisterRequest {

    @NotNull(message = "No se puede ingresar un monto nulo")
    @Positive(message = "El monto tiene que ser mayor a 0")
    private BigDecimal amount;

    @NotBlank(message = "La descripcion inicial es obligatoria")
    private String description;

    private Integer categoryId;

    @NotNull(message = "El tipo de transaccion es obligatoria")
    private Integer transactionTypeId;

    private Integer paymentMethodId;

    @NotNull(message = "La frecuencia es obligatoria")
    private Boolean isRecurring;

    private String notes;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime dateProcess;
}
