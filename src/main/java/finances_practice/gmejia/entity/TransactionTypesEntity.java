package finances_practice.gmejia.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "transaction_types", schema = "finance_personal")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionTypesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;
}
