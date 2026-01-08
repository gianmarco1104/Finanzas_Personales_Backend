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
        authenticate(request.getEmail(), request.getPassword());

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND )); //Buscar usuario

        if (!user.isEnabled()) {throw new BusinessException("Cuenta inactiva, requiere reactivacion", HttpStatus.FORBIDDEN);}  //Validar Status

        return buildAuthResponse(user, "Login exitoso");
    }

    @Override
    @Transactional
    public AuthResponse reactivate(LoginRequest request){
        authenticate(request.getEmail(), request.getPassword());

        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND ));

        if (user.isEnabled()) {throw new BusinessException("La cuenta ya esta activa", HttpStatus.BAD_REQUEST);}
        user.setEnabled(true); //Cambiar estado a true
        userRepository.save(user); //Update del user

        return buildAuthResponse(user,"Cuenta reactivada correctamente");

    }

    private void authenticate(String email, String password){
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        }catch (BadCredentialsException e){
            throw new BusinessException("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }
    }

    private AuthResponse buildAuthResponse(UserEntity user, String message){

        String token = jwtService.generateToken(user.getEmail(), user.getId(), user.getRole().getId());// Generar Token
        String roleName = (user.getRole() != null) ? user.getRole().getName() : "Sin Rol";         // Rol
        // Fechas del Token de Inicio y de Fin
        Date issued = new Date(); // Hora actual
        Date expired = new Date(issued.getTime() + jwtService.getExpirationTime());

        // Retornar el objeto completo
        return AuthResponse.builder()
                .token(token)
                .message(message)
                .userId(user.getId())
                .username(user.getEmail())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .role(roleName)
                .issuedAt(issued)
                .expiredAt(expired)
                .build();
    }
}
