package user;

import java.util.List;

public interface UserDAO {
    public int add(User user);
    public List<User> getAll();
    public User getById(int id);
    public int updateById(int id, User update);
    public int deleteById(int id);
}
