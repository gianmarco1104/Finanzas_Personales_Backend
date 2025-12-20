package finances_practice.gmejia.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TransactionPerUserRequest {
    private Integer month;
    private Integer year;
    private Integer categoryId;
}
