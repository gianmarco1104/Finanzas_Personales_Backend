package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.request.LoginRequest;
import finances_practice.gmejia.dto.response.AuthResponse;
import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.repository.UserRepository;
import finances_practice.gmejia.security.JwtService;
import finances_practice.gmejia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse login (LoginRequest request){
        //Buscar usuario
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("El usuario no existe", HttpStatus.CONFLICT));

        //Validar password
        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BusinessException("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }

        // Validar Status
        if (!user.getStatus()) {
            throw new BusinessException("Cuenta inactiva, requiere reactivacion", HttpStatus.FORBIDDEN);
        }

        // Traducir el Rol
        String roleName = (user.getRoleId() != null && user.getRoleId() == 1)
                ? "Administrador"
                : "Usuario";

        // Generar Token
        String token = jwtService.generateToken(user.getEmail(), user.getId(), user.getRoleId());

        //Fechas del Token de Inicio y de Fin
        Date issued = new Date(); // Hora actual
        Date expired = new Date(issued.getTime() + jwtService.getExpirationTime());

        // Retornar el objeto completo
        return AuthResponse.builder()
                .token(token)
                .message("Login exitoso")
                .userId(user.getId())
                .username(user.getEmail())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .role(roleName)
                .issuedAt(issued)
                .expiredAt(expired)
                .build();
    }

    @Override
    public AuthResponse reactivate(LoginRequest request){
        UserEntity user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new BusinessException("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }

        if (user.getStatus()) {
            throw new BusinessException("La cuenta ya esta activa", HttpStatus.BAD_REQUEST);
        }

        user.setStatus(true);
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), user.getId(), user.getRoleId());

        Date issued = new Date(); // Hora actual
        Date expired = new Date(issued.getTime() + jwtService.getExpirationTime());

        String roleName = (user.getRoleId() != null && user.getRoleId() == 1)
                ? "Administrador"
                : "Usuario";

        return AuthResponse.builder()
                .token(token)
                .message("Login exitoso")
                .userId(user.getId())
                .username(user.getEmail())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .role(roleName)
                .issuedAt(issued)
                .expiredAt(expired)
                .build();
    }
}
