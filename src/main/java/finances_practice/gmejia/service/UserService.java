package finances_practice.gmejia.service;

import finances_practice.gmejia.dto.request.*;
import finances_practice.gmejia.dto.response.GeneralResponse;
import finances_practice.gmejia.dto.response.ListUsersResponse;
import finances_practice.gmejia.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    GeneralResponse createUser(RegisterRequest request);
    UserResponse getMyProfile();
    GeneralResponse updateUser(UpdateRequest request);
    GeneralResponse updateEmailUser(EmailUpdateRequest request);
    GeneralResponse updatePasswordUser(PasswordUpdateRequest request);

    //Admin
    List<ListUsersResponse> getUsersByStatus(Boolean status);
    GeneralResponse userActivate(Long id);
    GeneralResponse userDesactivate(Long id);

    //Verificacion de Usuario por codigo
    GeneralResponse verifyUser(VerifyCodeRequest request);
    GeneralResponse resendVerificationCode(ResendCodeRequest request);
}
