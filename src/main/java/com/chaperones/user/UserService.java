package com.chaperones.user;

import com.chaperones.activity.Activity;
import com.chaperones.activity.ActivityDoesNotExistException;
import com.chaperones.venue.VenueNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDAO userDAO;

    public UserService(@Qualifier("userPostgres") UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void addNewUser(User user) {
        List<User> getAllUsers = userDAO.getAll();
        for (User getUserById : getAllUsers) {
            if (getUserById.getPhoneNumber().equals(user.getPhoneNumber())
                    || getUserById.getEmail().equals(user.getEmail())) {
                throw new IllegalStateException("User already exists");
            }
        }
            int addUser = userDAO.add(user);
            if (addUser != 1) {
                throw new IllegalStateException("User could not be added");
            }
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public User getUserById(Integer id) {
        User selected = userDAO.getById(id);
        if (selected == null){
            throw new UserNotFoundException("User could not be found");
        } else return selected;
    }

    public List<Activity> getActivitiesByUser(Integer id, boolean cancelled) {
        if (userDAO.getById(id) == null) {
            throw new UserNotFoundException("User with id " + id + " could not be found");
        }

        return userDAO.getActivities(id, cancelled);
    }

    public void updateUserById(Integer id, User user) {
        if (userDAO.getById(id) == null) {
            throw new UserNotFoundException("User with id " + id + " could not found");
        }

        int result = userDAO.updateById(id, user);

        if (result != 1) {
            throw new IllegalStateException("User with id " + id + " could not be updated");
        }
    }

    public void deleteUserById(Integer id) {
        if (userDAO.getById(id) == null) {
            throw new UserNotFoundException("User with id " + id + " could not found");
        }

        int result = userDAO.deleteById(id);

        if (result != 1) {
            throw new IllegalStateException("User with id " + id + " could not be deleted");
        }
    }

    public void addUserToActivity(Integer user_id, Integer activity_id){
        if (userDAO.getById(user_id) == null) {
            throw new UserNotFoundException("User with id " + user_id + " could not found");
        }

        int freeSpaces = userDAO.getFreeSpaces(activity_id);

        if (freeSpaces > 0) {
            userDAO.addUserToActivity(user_id, activity_id);
        }
        else {
            throw new IllegalStateException("This activity is full.");
        }
    }
}
