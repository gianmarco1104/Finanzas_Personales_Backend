package finances_practice.gmejia.dto.response;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//Roles, Payment Methods, Categories, Transaction Types y Gender
public class CatalogResponse {
    private Integer id;
    private String name;
}
