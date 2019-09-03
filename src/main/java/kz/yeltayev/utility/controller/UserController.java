package kz.yeltayev.utility.controller;

import kz.yeltayev.utility.model.dto.UserDto;
import kz.yeltayev.utility.model.request.AuthRequest;
import kz.yeltayev.utility.model.entity.User;
import kz.yeltayev.utility.exception.ResourceNotFoundException;
import kz.yeltayev.utility.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDto> fetchUsers() {
        return userService.fetchUsers();
    }

    @GetMapping("/users/owner/{owner}")
    public List<UserDto> fetchUsersByOwner(@PathVariable(value = "owner") String owner) {
        return userService.fetchUsersByOwner(owner);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> fetchUserById(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        UserDto userDto = userService.fetchUserById(userId);
        return ResponseEntity.ok().body(userDto);
    }

    @PostMapping("/users")
    public UserDto addUser(@Valid @RequestBody User user) {
        return userService.addUser(user);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable(value = "id") Long userId,
                                              @Valid @RequestBody User userDetails) throws ResourceNotFoundException {
        UserDto updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long userId)
            throws ResourceNotFoundException {
        return userService.deleteUser(userId);
    }

    @PostMapping("/users/auth")
    public UserDto auth(@Valid @RequestBody AuthRequest authRequest) {
        return userService.auth(authRequest);
    }
}
