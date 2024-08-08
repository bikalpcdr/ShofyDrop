package com.MSP.shopydrop.Service;

import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Enum.LoginType;
import com.MSP.shopydrop.Enum.UserType;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public interface UsersService {

    String signupUser(Users user);

    void sendVerificationEmail(String email);

    void verifyEmailToken(String token);

    Users loginUser(String email, String password);

    Optional<Users> getUser(Long id);

    Users updateUser(Long id, Users user);

    void deleteUser(Long id);

    void forgetPassword(String email);

    void verifyPasswordResetCode(int verificationCode);

    void resetPassword(String newPassword);

    List<Users> getAllUsers();
}

