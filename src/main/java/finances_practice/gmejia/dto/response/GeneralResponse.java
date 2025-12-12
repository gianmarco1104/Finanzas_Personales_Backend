package finances_practice.gmejia.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneralResponse {
    private Long id;
    private String message;
}
