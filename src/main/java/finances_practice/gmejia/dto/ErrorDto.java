package finances_practice.gmejia.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {
    private int status;
    private String error;
    private String message;
}
