package com.chaperones.user;

import java.util.List;

public interface UserDAO {
    public int add(User user);
    public List<User> getAll();
    public User getById(Integer id);
    public int updateById(Integer id, User update);
    public int deleteById(Integer id);
}
