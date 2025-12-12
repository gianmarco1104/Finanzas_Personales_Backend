package finances_practice.gmejia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "countries", schema = "finance_personal")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "phone_code")
    private String phoneCode;

    @Column(name = "iso_code")
    private String isoCode;
}
