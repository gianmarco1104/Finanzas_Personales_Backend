package finances_practice.gmejia.service;

import finances_practice.gmejia.dto.request.EmailUpdateRequest;
import finances_practice.gmejia.dto.request.PasswordUpdateRequest;
import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.request.UpdateRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.UserResponse;

public interface UserService {
    GeneralResponse createUser(RegisterRequest request);
    UserResponse getMyProfile();
    GeneralResponse updateUser(UpdateRequest request);
    GeneralResponse updateEmailUser(EmailUpdateRequest request);
    GeneralResponse updatePasswordUser(PasswordUpdateRequest request);
}
