package finances_practice.gmejia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "genders", schema = "finance_personal")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
