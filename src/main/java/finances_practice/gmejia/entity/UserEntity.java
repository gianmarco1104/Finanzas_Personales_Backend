package finances_practice.gmejia.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
DATA -> Lombok, Metodos de acceso Getter y Setter, Crea un ToString para todos los campos
@EqualsAndHashCode, permite comparar basandose en el contenido y no en la memoria
@RequiredArgsConstructor, Crea un constructor para los campos final
*/

@Entity
@Table(name = "users", schema = "finance_personal")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Genera el autoincremental
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;
    private String password;
    private String phone;

    @Column(name = "country_id")
    private Integer countryId;

    @Column(name = "gender_id")
    private Integer genderId;

    @Column(name = "role_id")
    private Integer roleId;

    private Boolean status;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
