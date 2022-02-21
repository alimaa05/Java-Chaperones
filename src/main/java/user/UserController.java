package user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "users")
    public void addUsers(@RequestBody User user) {
        userService.addNewUser(user);
    }

    @GetMapping(path = "users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "users/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping(path = "users/{id}")
    public void updateUserById(@PathVariable("id") Integer id, @RequestBody User update) {
        userService.updateUserById(id, update);
    }

    @DeleteMapping(path = "users/{id}")
    public void deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
    }
}