package finances_practice.gmejia.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private CatalogResponse gender;
    private CatalogResponse role;
    private CountriesResponse country;
}
