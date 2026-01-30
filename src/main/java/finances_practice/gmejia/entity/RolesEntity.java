package finances_practice.gmejia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_roles", schema = "finance_personal")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RolesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
