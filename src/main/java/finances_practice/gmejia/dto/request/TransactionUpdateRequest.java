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
public class TransactionUpdateRequest {

    @NotNull(message = "El ID de la transaccion es obligatorio")
    private Long id;

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser mayor a 0")
    private BigDecimal amount;

    @NotBlank(message = "La descripción es obligatoria")
    private String description;

    private Integer categoryId;
    private Integer transactionTypeId;
    private Integer paymentMethodId;

    @NotNull(message = "La frecuencia es obligatoria")
    private Boolean isRecurring;

    private String notes;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDateTime dateProcess;
}
