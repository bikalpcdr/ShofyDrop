package com.MSP.shopydrop.Service;

import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Enum.LoginType;
import com.MSP.shopydrop.Enum.UserType;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface UsersService {

    Users addUser(Users users);

    Users updateUser(Users users, Long id);

    Users getUserById(Long id);

    Users getUserLoginType(LoginType loginType);

    Users getUserType(UserType userType);

    List<Users> getAllUsers();

    boolean deleteUser(Long id);

    List<Users> getUsersByUserTypeSorted(UserType userType);
}

