package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.request.*;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.ListUsersResponse;
import finances_practice.gmejia.dto.response.UserResponse;
import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.entity.VerificationCodeEntity;
import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.mapper.UserMapper;
import finances_practice.gmejia.repository.UserRepository;
import finances_practice.gmejia.repository.VerificationCodeRepository;
import finances_practice.gmejia.service.EmailService;
import finances_practice.gmejia.service.UserService;
import finances_practice.gmejia.utils.UserContextUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserContextUtils userContextUtils;
    private final VerificationCodeRepository codeRepository;
    private final EmailService emailService;

    @Override
    @Transactional
    public GeneralResponse createUser(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new BusinessException("El email ya esta registrado", HttpStatus.CONFLICT);
        }

        String passEncripted = passwordEncoder.encode(request.getPassword());
        UserEntity user = userMapper.toEntity(request,passEncripted);
        UserEntity savedUser = userRepository.save(user);

        // Generar codigo de verificacion
        String code = String.valueOf(new Random().nextInt(900000)+ 100000);

        VerificationCodeEntity verificationCode = VerificationCodeEntity.builder()
                .user(user)
                .code(code)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .createdAt(LocalDateTime.now())
                .build();

        codeRepository.save(verificationCode);

        //Enviar email
        emailService.sendVerificationEmail(savedUser.getEmail(), code);

        return GeneralResponse.builder()
                .id(savedUser.getId())
                .message("Registro exitoso, Verifique su correo")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getMyProfile(){
        UserEntity user = userContextUtils.getCurrentUser();
        return userMapper.toResponseUser(user);
    }

    @Override
    @Transactional
    public GeneralResponse updateUser(UpdateRequest request){
        // El getName devuelve el email, como fue configurado el JWT
        UserEntity user = userContextUtils.getCurrentUser();
        userMapper.updateUser(request, user);
        UserEntity updateUser = userRepository.save(user);
        return GeneralResponse.builder()
                .id(updateUser.getId())
                .message("Actualizacion exitosa")
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse updateEmailUser(EmailUpdateRequest request){
        UserEntity user = userContextUtils.getCurrentUser();

        if(!user.getEmail().equalsIgnoreCase(request.getCurrentEmail())){
            throw new BusinessException("El correo actual no coincide con el usuario autenticado", HttpStatus.BAD_REQUEST);
        }

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new BusinessException("La contraseña es incorrecta", HttpStatus.UNAUTHORIZED);
        }

        if(userRepository.existsByEmail(request.getNewEmail())){
            throw new BusinessException("El nuevo correo ya esta en uso", HttpStatus.BAD_REQUEST);
        }

        user.setEmail(request.getNewEmail());
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(user.getId());
        userRepository.save(user);

        return GeneralResponse.builder()
                .id(user.getId())
                .message("Correo actualizado correctamente. Por favor inicie sesion nuevamente.")
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse updatePasswordUser(PasswordUpdateRequest request){
        UserEntity user = userContextUtils.getCurrentUser();

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new BusinessException("La contraseña actual es incorrecta", HttpStatus.UNAUTHORIZED);
        }

        if(passwordEncoder.matches(request.getNewPassword(), user.getPassword())){
            throw new BusinessException("La contraseña no puede ser igual a la anterior", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        user.setUpdatedBy(user.getId());
        userRepository.save(user);
        return GeneralResponse.builder()
                .id(user.getId())
                .message("Contrasena actualizada correctamente")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('Administrador')")
    public List<ListUsersResponse> getUsersByStatus(Boolean status){
        List<UserEntity> users = userRepository.findAllByEnabledAndRole_NameOrderByIdAsc(status,"Usuario");
        return userMapper.toUserResponseList(users);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('Administrador')")
    public GeneralResponse userActivate(Long id){
        UserEntity user = userContextUtils.getCurrentUser();

        UserEntity targetUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if(targetUser.isEnabled()){
            throw new BusinessException("El usuario ya se encuentra con estado activo", HttpStatus.CONFLICT);
        }
        targetUser.setEnabled(true);
        userRepository.save(targetUser);

        return GeneralResponse.builder()
                .id(targetUser.getId())
                .message("Usuario habilitado correctamente")
                .build();
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('Administrador')")
    public GeneralResponse userDesactivate(Long id){
        UserEntity user = userContextUtils.getCurrentUser();

        if(user.getId().equals(id)){
            throw new BusinessException("No puedes desactivar tu propia cuenta", HttpStatus.CONFLICT);
        }

        UserEntity targetUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if(!targetUser.isEnabled()){
            throw new BusinessException("El usuario ya se encuentra con estado inactivo", HttpStatus.CONFLICT);
        }
        targetUser.setEnabled(false);
        userRepository.save(targetUser);

        return GeneralResponse.builder()
                .id(targetUser.getId())
                .message("Usuario deshabilitado correctamente")
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse verifyUser(VerifyCodeRequest request){
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        VerificationCodeEntity vCode = codeRepository.findByUserAndCode(user, request.getCode())
                .orElseThrow(() -> new BusinessException("Código inválido", HttpStatus.BAD_REQUEST));

        if (vCode.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException("El código ha expirado", HttpStatus.BAD_REQUEST);
        }

        user.setEnabled(true);
        userRepository.save(user);

        codeRepository.delete(vCode);

        return GeneralResponse.builder()
                .id(user.getId())
                .message("Cuenta activada exitosamente")
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse resendVerificationCode(ResendCodeRequest request){
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if(user.isEnabled()){
            throw new BusinessException("Esta cuenta ya está activa", HttpStatus.BAD_REQUEST);
        }

        Optional<VerificationCodeEntity> oldCode = codeRepository.findByUser(user);
        oldCode.ifPresent(codeRepository::delete);

        String code = String.valueOf(new Random().nextInt(900000) + 100000);

        VerificationCodeEntity newVerificationCode = VerificationCodeEntity.builder()
                .user(user)
                .code(code)
                .expiryDate(LocalDateTime.now().plusMinutes(30))
                .createdAt(LocalDateTime.now())
                .build();

        codeRepository.save(newVerificationCode);

        emailService.sendVerificationEmail(user.getEmail(), code);

        return GeneralResponse.builder()
                .id(user.getId())
                .message("Nuevo codigo enviado")
                .build();
    }
}

