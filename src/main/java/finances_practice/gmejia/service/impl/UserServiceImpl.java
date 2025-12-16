package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.response.CatalogResponse;
import finances_practice.gmejia.dto.response.CountriesResponse;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.UserResponse;
import finances_practice.gmejia.entity.CountriesEntity;
import finances_practice.gmejia.entity.GenderEntity;
import finances_practice.gmejia.entity.RolesEntity;
import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.mapper.UserMapper;
import finances_practice.gmejia.repository.UserRepository;
import finances_practice.gmejia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
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
    public UserResponse getMyProfile(String email){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));
        return userMapper.toResponse(user);
    }
}
