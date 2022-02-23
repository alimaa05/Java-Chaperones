package com.chaperones.user;

import com.chaperones.activity.Activity;

import java.util.List;

public interface UserDAO {
    public int add(User user);
    public List<User> getAll();
    public User getById(Integer id);
    public List<Activity> getActivities(Integer id, boolean cancelled);
    public int updateById(Integer id, User update);
    public int deleteById(Integer id);
}
