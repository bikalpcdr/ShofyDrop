package com.MSP.shopydrop.Controller;

import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Exception.ResourceNotFoundException;
import com.MSP.shopydrop.Service.UsersService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/signup")
    public ResponseEntity<Users> addUser(@RequestBody Users users){
        return ResponseEntity.ok().body(usersService.loginUser(users));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Users> updateUser(@RequestBody Users users, @PathVariable Long id){
        usersService.updateUser(id,users);
        return ResponseEntity.ok().body(usersService.updateUser(id,users));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.usersService.getUser(id)
                .orElseThrow(()-> new ResourceNotFoundException("User doesn't exist with this id")));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        usersService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
}
