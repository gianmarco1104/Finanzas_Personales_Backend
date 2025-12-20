package finances_practice.gmejia.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPerUserDetailResponse {
    private Long id;
    private BigDecimal amount;
    private String description;
    private String notes;
    private LocalDateTime dateProcess;
    private Long userId;
    private Boolean isRecurring;
    private Boolean status;
    private CatalogResponse categories;
    private CatalogResponse transaction;
    private CatalogResponse paymentMethods;
}
