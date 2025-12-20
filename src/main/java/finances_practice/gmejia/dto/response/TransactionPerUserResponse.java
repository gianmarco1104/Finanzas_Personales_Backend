package finances_practice.gmejia.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionPerUserResponse {
    private Long id;
    private BigDecimal amount;
    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date;

    private String categoryName;
    private String typeName;
    private String paymentMethod;
    private Boolean isRecurring;
}
