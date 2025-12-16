package finances_practice.gmejia.service;

import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.UserResponse;

public interface UserService {
    GeneralResponse createUser(RegisterRequest request);
    UserResponse getMyProfile(String email);
}
