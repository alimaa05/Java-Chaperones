package com.chaperones.user;

import com.chaperones.activity.Activity;
import org.springframework.data.relational.core.sql.In;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping(path = "users/{id}/activities")
    public List<Activity> getAllActivitiesByUser(@PathVariable("id") Integer id) {
        List<Activity> allActivities = new ArrayList<>();
        allActivities.addAll(userService.getActivitiesByUser(id, false));
        allActivities.addAll(userService.getActivitiesByUser(id, true));
        return allActivities;
    }

    @GetMapping(path = "users/{id}/activities/available")
    public List<Activity> getAvailableActivitiesByUser(@PathVariable("id") Integer id) {
        return userService.getActivitiesByUser(id, false);
    }

    @GetMapping(path = "users/{id}/activities/cancelled")
    public List<Activity> getCancelledActivitiesByUser(@PathVariable("id") Integer id) {
        return userService.getActivitiesByUser(id, true);
    }

    @PutMapping(path = "users/{id}")
    public void updateUserById(@PathVariable("id") Integer id, @RequestBody User update) {
        userService.updateUserById(id, update);
    }

    @DeleteMapping(path = "users/{id}")
    public void deleteUserById(@PathVariable("id") Integer id) {
        userService.deleteUserById(id);
    }

    @PostMapping(path = "users/{user_id}/activities")
    public void addUserToActivity(@PathVariable("user_id") Integer user_id, @RequestBody Activity activity) {
        userService.addUserToActivity(user_id, activity);
    }

    @DeleteMapping(path = "users/{user_id}/activities/{activity_id}")
    public int deleteUserFromActivity(@PathVariable("user_id") Integer user_id, @PathVariable Integer activity_id) {
       return userService.removeUserFromActivity(user_id, activity_id);
    }
}
