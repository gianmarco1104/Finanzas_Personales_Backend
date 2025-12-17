package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.request.LoginRequest;
import finances_practice.gmejia.dto.response.AuthResponse;
import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.repository.UserRepository;
import finances_practice.gmejia.security.JwtService;
import finances_practice.gmejia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional(readOnly = true)
    public AuthResponse login (LoginRequest request){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            // Error personalizado
            throw new BusinessException("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }

        //Buscar usuario
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        // Validar Status
        if (!user.getStatus()) {
            throw new BusinessException("Cuenta inactiva, requiere reactivacion", HttpStatus.FORBIDDEN);
        }

        // Rol
        String roleName = (user.getRole() != null) ? user.getRole().getName() : "Sin Rol";

        // Generar Token
        String token = jwtService.generateToken(user.getEmail(), user.getId(), user.getRole().getId());

        // Fechas del Token de Inicio y de Fin
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
    @Transactional
    public AuthResponse reactivate(LoginRequest request){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            // Atrapamos el error de Spring y lanzamos TU error personalizado
            throw new BusinessException("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }

        UserEntity user = userRepository.findByEmail(request.getEmail())
            .orElseThrow();

        if (user.getStatus()) {
            throw new BusinessException("La cuenta ya esta activa", HttpStatus.BAD_REQUEST);
        }

        user.setStatus(true);
        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail(), user.getId(), user.getRole().getId());

        Date issued = new Date(); // Hora actual
        Date expired = new Date(issued.getTime() + jwtService.getExpirationTime());

        String roleName = (user.getRole() != null) ? user.getRole().getName() : "Sin Rol";

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
