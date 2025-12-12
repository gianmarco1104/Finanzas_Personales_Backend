package finances_practice.gmejia.service;

import finances_practice.gmejia.dto.request.RegisterRequest;
import finances_practice.gmejia.dto.response.GeneralResponse;

public interface UserService {
    GeneralResponse createUser(RegisterRequest request);
}
