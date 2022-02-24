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

    public UserService(@Qualifier("userPostgres") UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void addNewUser(User user) {
        List<User> allUsers = userDAO.getAll();
        for (User u : allUsers) {
            if (u.getPhoneNumber().equalsIgnoreCase(user.getPhoneNumber().trim())
                    || u.getEmail().equalsIgnoreCase(user.getEmail().trim())) {
                throw new IllegalStateException("User already exists");
            }
        }
        int added = userDAO.add(user);
        if (added != 1) {
            throw new IllegalStateException("User could not be added");
        }
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public User getUserById(Integer id) {
        User selected = userDAO.getById(id);
        if (selected == null) {
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
            throw new UserNotFoundException("User with id " + id + " could not be found");
        }

        int result = userDAO.updateById(id, user);

        if (result != 1) {
            throw new IllegalStateException("User with id " + id + " could not be updated");
        }
    }

    public void deleteUserById(Integer id) {
        if (userDAO.getById(id) == null) {
            throw new UserNotFoundException("User with id " + id + " could not be found");
        }

        int result = userDAO.deleteById(id);

        if (result != 1) {
            throw new IllegalStateException("User with id " + id + " could not be deleted");
        }
    }


    public void addUserToActivity(Integer user_id, Integer activity_id){

        if (userDAO.getById(user_id) == null) {
            throw new UserNotFoundException("User with id " + user_id + " could not be found");
        }

        int freeSpaces = userDAO.getFreeSpaces(activity_id);

        if (freeSpaces > 0) {
            userDAO.addUserToActivity(user_id, activity_id);
        }
        else {
            throw new IllegalStateException("This activity is full.");
        }
    }

    public int removeUserFromActivity(Integer user_id, Integer activity_id) {
        if (userDAO.getById(user_id) == null) {
            throw new UserNotFoundException("User with id " + user_id + " could not be found");
        }

        int result = userDAO.removeUserFromActivity(user_id, activity_id);

        if (result != 1) {
            throw new IllegalStateException("User with id " + user_id + " could not be removed from activity");
        }

        return result;
    }
}
