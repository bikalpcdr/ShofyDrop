package com.MSP.shopydrop.Service.Implementation;

import com.MSP.shopydrop.Entity.ForgetPassword;
import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Entity.UsersVerification;
import com.MSP.shopydrop.Exception.EmailRelatedException;
import com.MSP.shopydrop.Exception.ResourceNotFoundException;
import com.MSP.shopydrop.Repository.ForgetPasswordRepo;
import com.MSP.shopydrop.Repository.UsersRepo;
import com.MSP.shopydrop.Repository.UsersVerificationRepo;
import com.MSP.shopydrop.Service.UsersService;
import com.MSP.shopydrop.Utils.MailUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UsersService {

    private final UsersRepo usersRepo;
    private final UsersVerificationRepo usersVerificationRepo;
    private final ForgetPasswordRepo forgetPasswordRepo;
    private final MailUtils mailUtils;

    public UserServiceImpl(UsersRepo usersRepo, UsersVerificationRepo usersVerificationRepo, ForgetPasswordRepo forgetPasswordRepo, MailUtils mailUtils){
        this.usersRepo = usersRepo;
        this.usersVerificationRepo = usersVerificationRepo;
        this.forgetPasswordRepo = forgetPasswordRepo;
        this.mailUtils = mailUtils;
    }

    @Override
    @Transactional
    public String signupUser(Users user) {
        try {
            if (user.getPassword() == null || user.getPassword().isEmpty()){
                throw new IllegalArgumentException("Password cannot be empty");
            }
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setIsEmailVerified('N');
            Users users =usersRepo.save(user);

            sendVerificationEmail(user.getEmail());
            return String.valueOf(user.getId());
        }catch (IllegalArgumentException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Internal Server Error: "+e.getMessage(),e);
        }
    }

    @Override
    @Transactional
    public void sendVerificationEmail(String email) {
        try {
            Users user = usersRepo.findByEmail(email)
                    .orElseThrow(()-> new ResourceNotFoundException("Email doesn't exist with this email"+email));
            if (user.getIsEmailVerified() == 'Y'){
                throw new EmailRelatedException("Email is already verified. Please for proceed for login");
            }
            String token = UUID.randomUUID().toString();
            String verificationLink = "http://localhost:8080/api/users/verifyEmail?token=" + token;

            UsersVerification tokenEntity = new UsersVerification();
            tokenEntity.setToken(token);
            tokenEntity.setExpiredAt(Timestamp.from(Instant.now().plusSeconds(21600)));
            tokenEntity.setUsers(user);
            usersVerificationRepo.save(tokenEntity);

            mailUtils.forgetPasswordVerificationCode(email,user.getName(),verificationLink);
        } catch (ResourceNotFoundException | EmailRelatedException e) {
            try {
                throw e;
            } catch (EmailRelatedException ex) {
                throw new RuntimeException(ex);
            }
        }catch (Exception e){
            throw new RuntimeException("Internal server: "+e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void verifyEmailToken(String token) {
        try {
            UsersVerification usersVerification = usersVerificationRepo.findByToken(token).orElseThrow(() -> new IllegalStateException("Invalid verification token."));

            List<UsersVerification> allTokens = usersVerificationRepo.findAllTokensByUser(usersVerification.getUsers().getId());
            if (allTokens.isEmpty()) {
                throw new IllegalStateException("No verification tokens found for user.");
            }

            UsersVerification latestToken = allTokens.get(0);
            if (!latestToken.getToken().equals(token)) {
                throw new IllegalStateException("This is not the latest verification token.");
            }
            if (latestToken.getExpiredAt().toInstant().isBefore(Instant.now())) {
                throw new IllegalStateException("Verification token expired.");
            }
            Users fetchUser = usersRepo.findById(latestToken.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this id:" + latestToken.getUsers().getId()));
            fetchUser.setIsEmailVerified('Y');
            fetchUser.setUpdatedAt(Timestamp.from(Instant.now()));
            usersRepo.save(fetchUser);

            usersVerificationRepo.deleteByUserId(usersVerification.getUsers().getId());
        } catch (IllegalStateException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error:" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Users loginUser(String email, String password) {
        try {
            Users user = usersRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Email don't match."));
            if (user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
                if (user.getIsEmailVerified() == 'N') {
                    throw new IllegalStateException("User is not verified, please verify your email before login.");
                }
                return user;
            } else {
                throw new ResourceNotFoundException("Password don't match.");
            }
        } catch (ResourceNotFoundException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error:" + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Optional<Users> getUser(Long id) {
        try {
            Optional<Users> user = usersRepo.findById(id);
            if (user.isEmpty()) {
                throw new ResourceNotFoundException("User not found wit id: " + id);
            }
            return user;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    public Users updateUser(Long id, Users users) {
        try {
            Users existingUser = usersRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
            existingUser.setName(users.getName());
            existingUser.setEmail(users.getEmail());

            existingUser.setUpdatedAt(Timestamp.from(Instant.now()));
            return usersRepo.save(existingUser);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        try {
            if (!usersRepo.existsById(id)) {
                throw new ResourceNotFoundException("User not found with id: " + id);
            }
            usersRepo.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void forgetPassword(String email) {
        try {
            Users users = usersRepo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this email: " + email));
            if (users.getIsEmailVerified() == 'N') {
                throw new EmailRelatedException("Email is not verified. Please verify your email before requesting for resetting password,");
            }
            UUID uuid = UUID.randomUUID();
            long lsb = uuid.getLeastSignificantBits();
            int verificationCode = Math.abs((int) (lsb % 1000000));

            ForgetPassword forgetPassword = new ForgetPassword();
            forgetPassword.setCode(verificationCode);
            forgetPassword.setIsVerified("N");
            forgetPassword.setUsers(users);
            forgetPassword.setExpiredAt(Timestamp.from(Instant.now().plusSeconds(3600)));

            forgetPasswordRepo.save(forgetPassword);

            mailUtils.forgetPasswordVerificationCode(email, users.getName(), verificationCode);
        } catch (EmailRelatedException | ResourceNotFoundException e) {
            try {
                throw e;
            } catch (EmailRelatedException ex) {
                throw new RuntimeException(ex);
            }
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void verifyPasswordResetCode(int verificationCode) {
        try {
            ForgetPassword forgetPassword = forgetPasswordRepo.findByCode(verificationCode).orElseThrow(() -> new IllegalStateException("Invalid password verification code."));

            List<ForgetPassword> allCodes = forgetPasswordRepo.findAllCodesByUser(forgetPassword.getUsers().getId());

            if (allCodes.isEmpty()) {
                throw new IllegalStateException("No verification codes found for user.");
            }
            ForgetPassword latestCode = allCodes.get(0);
            if (latestCode.getCode() != verificationCode) {
                throw new IllegalStateException("This is not the latest password reset code.");
            }
            if (latestCode.getExpiredAt().toInstant().isBefore(Instant.now())) {
                throw new IllegalStateException("Password verification code expired.");
            }

            Users users = usersRepo.findById(forgetPassword.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with Id: " + forgetPassword.getUsers().getId()));
            latestCode.setIsVerified("Y");
            forgetPasswordRepo.save(latestCode);
            usersRepo.save(users);

        } catch (ResourceNotFoundException | IllegalStateException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void resetPassword(String newPassword) {
        try {
            ForgetPassword forgetPassword = forgetPasswordRepo.findByIsVerified("Y").orElseThrow(() -> new ResourceNotFoundException("Password reset code is not verified."));

            Users users = usersRepo.findById(forgetPassword.getUsers().getId()).orElseThrow(() -> new ResourceNotFoundException("User doesn't exist with this Id."));
            users.setPassword(DigestUtils.md5DigestAsHex(newPassword.getBytes()));
            usersRepo.save(users);

            forgetPasswordRepo.deleteByUsersId(forgetPassword.getUsers().getId());
        } catch (IllegalArgumentException | ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Internal server error: " + e.getMessage(), e);
        }
    }
}
