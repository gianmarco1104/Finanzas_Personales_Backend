package finances_practice.gmejia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions", schema = "finance_personal")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    private String description;

    @Column(name = "is_recurring")
    private Boolean isRecurring;

    private String notes;

    @Column(name = "date")
    private LocalDateTime dateProcess;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoriesEntity categories;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private TransactionTypesEntity transaction;

    @ManyToOne
    @JoinColumn(name = "payment_method_id")
    private PaymentMethodsEntity paymentMethods;

    private Boolean status;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
