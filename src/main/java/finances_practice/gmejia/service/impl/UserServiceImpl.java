package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.request.EmailUpdateRequest;
import finances_practice.gmejia.dto.request.PasswordUpdateRequest;
import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.request.UpdateRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.UserResponse;
import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.mapper.UserMapper;
import finances_practice.gmejia.repository.UserRepository;
import finances_practice.gmejia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        return userMapper.toResponse(user);
    }

    @Override
    @Transactional
    public GeneralResponse updateUser(UpdateRequest request){
        // El getName devuelve el email, como fue configurado el JWT
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        userMapper.updateUser(request, userEntity);
        UserEntity updateUser = userRepository.save(userEntity);
        return GeneralResponse.builder()
                .id(updateUser.getId())
                .message("Actualizacion exitosa")
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse updateEmailUser(EmailUpdateRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        if(!email.equalsIgnoreCase(request.getCurrentEmail())){
            throw new BusinessException("El correo actual no coincide con el usuario autenticado", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(()-> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new BusinessException("La contrasena es incorrecta", HttpStatus.UNAUTHORIZED);
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
                .message("Correo actualizado corretamente")
                .build();
    }

    @Override
    @Transactional
    public GeneralResponse updatePasswordUser(PasswordUpdateRequest request){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));

        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new BusinessException("La contrasena actual es incorrecta", HttpStatus.UNAUTHORIZED);
        }

        if(passwordEncoder.matches(request.getNewPassword(), user.getPassword())){
            throw new BusinessException("La contrasena no puede ser igual a la anterior", HttpStatus.BAD_REQUEST);
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
}

