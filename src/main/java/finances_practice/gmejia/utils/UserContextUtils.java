package finances_practice.gmejia.utils;

import finances_practice.gmejia.entity.UserEntity;
import finances_practice.gmejia.exception.BusinessException;
import finances_practice.gmejia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserContextUtils {
    private final UserRepository userRepository;

    public UserEntity getCurrentUser(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
         return userRepository.findByEmail(email).orElseThrow(
                () -> new BusinessException("Usuario no encontrado", HttpStatus.NOT_FOUND));
    }
}
