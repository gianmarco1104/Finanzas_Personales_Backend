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

    @ManyToOne //Las relaciones de una BD pero como un objeto de Java
    @JoinColumn(name = "country_id") //Se usa para relaciones (FKs)
    private CountriesEntity country;

    @ManyToOne
    @JoinColumn(name = "gender_id")
    private GenderEntity gender;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RolesEntity role;

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
