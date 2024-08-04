package com.MSP.shopydrop.Controller;

import com.MSP.shopydrop.Entity.Users;
import com.MSP.shopydrop.Service.UsersService;
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
        return ResponseEntity.ok().body(usersService.addUser(users));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Users> updateUser(@RequestBody Users users, @PathVariable Long id){
        usersService.updateUser(users, id);
        return ResponseEntity.ok().body(usersService.updateUser(users,id));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id){
        return ResponseEntity.ok().body(this.usersService.getUserById(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Users>> getAllUsers(){
        return ResponseEntity.ok().body(this.usersService.getAllUsers());
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
        return ResponseEntity.ok().body(this.usersService.deleteUser(id));
    }
}
