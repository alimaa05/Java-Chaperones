package user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private user.UserService userService;

    public UserController(user.UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "users")
    public void addUsers(@RequestBody user.User user) {
        userService.addNewUser(user);
    }

    @GetMapping(path = "users")
    public List<user.User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "users/{id}")
    public user.User getUserById(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @PutMapping(path = "users/{id}")
    public void updateUserById(@PathVariable("id") Integer id, @RequestBody user.User update) {
        userService.updateUserById(id, update);
    }

    @DeleteMapping(path = "users/{id}")
    public void deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
    }
}