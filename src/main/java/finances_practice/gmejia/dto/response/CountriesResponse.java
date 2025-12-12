package finances_practice.gmejia.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountriesResponse {
    private Integer id;
    private String name;
    private String phoneCode;
    private String isoCode;
}
