package com.chaperones.user;

import java.util.List;

public interface UserDAO {
    public Integer add(User user);
    public List<User> getAll();
    public User getById(int id);
    public Integer updateById(int id, User update);
    public Integer deleteById(int id);
}
