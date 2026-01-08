package finances_practice.gmejia.service;


import finances_practice.gmejia.dto.request.LoginRequest;
import finances_practice.gmejia.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse reactivate(LoginRequest request);

}
