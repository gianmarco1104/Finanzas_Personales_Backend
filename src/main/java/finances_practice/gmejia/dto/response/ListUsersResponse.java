package finances_practice.gmejia.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListUsersResponse {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private Boolean status;
    private LocalDateTime createdAt;
}
