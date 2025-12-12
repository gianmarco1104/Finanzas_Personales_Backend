package finances_practice.gmejia.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data //Explicado en UserEntity
@Builder //Genera un Patron de diseno builder AuthResponse.builder().token()... (SE APLICA EN EL AuthServiceImpl)
@AllArgsConstructor //Genera un constructor que recibe todos los argumentos ES NECESARIO PARA EL BUILDER
@NoArgsConstructor //Genera un constructor vacio ES NECESARIO POR UN POSIBLE JSON QUE SE ENVIE DESDE EL FRONT
public class AuthResponse {
    private String token;
    private String message;
    private Long userId;
    private String username;
    private String fullName;
    private String role;
    private Date issuedAt;
    private Date expiredAt;
}
