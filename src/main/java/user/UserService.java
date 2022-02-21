package user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserDAO userDAO;

    public UserService(@Qualifier("postgres") UserDAO userDAO){
        this.userDAO = userDAO;
    }

    public void addNewUser(User user) {
        int result = userDAO.add(user);

        if (result != 1) {
            throw new IllegalStateException("Could not add new user...");
        }
    }

    public List<User> getAllUsers() {
        return userDAO.getAll();
    }

    public User getUserById(int id) {
        User selected = userDAO.getById(id);
        if (selected == null){
            throw new IllegalStateException("Could not find user...");
        } else return selected;
    }

    public void updateUserById(int id, User user) {
        if (userDAO.getById(id) == null) {
            throw new IllegalStateException("Could not find user...");
        }

        int result = userDAO.updateById(id, user);

        if (result != 1) {
            throw new IllegalStateException("Could not update selected user...");
        }
    }

    public void deleteUserById(int id) {
        if (userDAO.getById(id) == null) {
            throw new IllegalStateException("Could not find user...");
        }

        int result = userDAO.deleteById(id);

        if (result != 1) {
            throw new IllegalStateException("Could not delete selected user...");
        }
    }
    // cancelled booking for user
    // update booking for user
    // about dependency
}
