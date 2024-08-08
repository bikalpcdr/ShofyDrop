package com.MSP.shopydrop.Controller;

import com.MSP.shopydrop.Entity.ForgetPassword;
import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Exception.ResourceNotFoundException;
import com.MSP.shopydrop.Service.UsersService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@RequestBody Users user, HttpSession session) {
        try {
            usersService.signupUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("User signup successfully. Please verify your email for login.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Users users) {
        try {
            users = usersService.loginUser(users.getEmail(), users.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/requestEmailToken")
    public ResponseEntity<String> sendVerificationEmail(@RequestBody Users user) {
        try {
            usersService.sendVerificationEmail(user.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Email verification token is send again successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/verifyEmail")
    public ModelAndView verifyEmailToken(@RequestParam("token") String verificationToken) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            usersService.verifyEmailToken(verificationToken);
            modelAndView.setViewName("Assignment_verifySuccess");
        } catch (IllegalStateException e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("Assignment_error");
        } catch (ResourceNotFoundException e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("Assignment_error");
        } catch (IllegalArgumentException e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("Assignment_error");
        } catch (Exception e) {
            modelAndView.addObject("error", e.getMessage());
            modelAndView.setViewName("Assignment_error");
        }
        return modelAndView;
    }

    @PostMapping("/forgetPassword")
    public ResponseEntity<String> forgetPassword(@RequestBody Users users) {
        try {
            usersService.forgetPassword(users.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body("Password verification code is send to your email.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/verifyResetCode")
    public ResponseEntity<String> verifyPasswordResetCode(@RequestBody ForgetPassword forgetPassword) {
        try {
            usersService.verifyPasswordResetCode(forgetPassword.getCode());
            return ResponseEntity.status(HttpStatus.OK).body("Password Reset code verified successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Users user) {
        try {
            usersService.resetPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Password reset successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody Users user) {
        try {
            Users updatedUser = usersService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUsers(@PathVariable Long id) {
        try {
            Optional<Users> user = usersService.getUser(id);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            usersService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Users>> getAllUsers() {
        try {
            List<Users> users = usersService.getAllUsers();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
