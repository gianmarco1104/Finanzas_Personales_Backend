package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.request.EmailUpdateRequest;
import finances_practice.gmejia.dto.request.PasswordUpdateRequest;
import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.request.UpdateRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.ListUsersResponse;
import finances_practice.gmejia.dto.response.UserResponse;
import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.mapper.UserMapper;
import finances_practice.gmejia.repository.UserRepository;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserContextUtils userContextUtils;

    @Override
    @Transactional
    public GeneralResponse createUser(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new BusinessException("El email ya esta registrado", HttpStatus.CONFLICT);
        }

        String passEncripted = passwordEncoder.encode(request.getPassword());
        UserEntity user = userMapper.toEntity(request,passEncripted);
        UserEntity savedUser = userRepository.save(user);

        return GeneralResponse.builder()
                .id(savedUser.getId())
                .message("Registro exitoso")
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
        List<UserEntity> users = userRepository.findAllByStatusAndRole_NameOrderByIdAsc(status,"Usuario");
        return userMapper.toUserResponseList(users);
    }

    @Override
    @Transactional
    @PreAuthorize("hasRole('Administrador')")
    public GeneralResponse userActivate(Long id){
        UserEntity user = userContextUtils.getCurrentUser();

        UserEntity targetUser = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if(Boolean.TRUE.equals(targetUser.getStatus())){
            throw new BusinessException("El usuario ya se encuentra con estado activo", HttpStatus.CONFLICT);
        }
        targetUser.setStatus(true);
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

        if(Boolean.FALSE.equals(targetUser.getStatus())){
            throw new BusinessException("El usuario ya se encuentra con estado inactivo", HttpStatus.CONFLICT);
        }
        targetUser.setStatus(false);
        userRepository.save(targetUser);

        return GeneralResponse.builder()
                .id(targetUser.getId())
                .message("Usuario deshabilitado correctamente")
                .build();
    }
}

