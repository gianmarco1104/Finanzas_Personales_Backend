package finances_practice.gmejia.service.impl;

import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.repository.UserRepository;
import finances_practice.gmejia.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public GeneralResponse createUser(RegisterRequest request){

        if(userRepository.existsByEmail(request.getEmail())){
            throw new BusinessException("El email ya esta registrado", HttpStatus.CONFLICT);
        }

        UserEntity user = UserEntity.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .countryId(request.getCountryId())
                .genderId(request.getGenderId())
                .password(passwordEncoder.encode(request.getPassword()))
                .roleId(2)
                .status(true)
                .createdBy(2)
                .createdAt(LocalDateTime.now())
                .build();

        UserEntity savedUser = userRepository.save(user);

        return GeneralResponse.builder()
                .id(savedUser.getId())
                .message("Registro exitoso")
                .build();
    }
}
