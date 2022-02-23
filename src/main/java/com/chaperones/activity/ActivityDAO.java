package com.chaperones.activity;

import com.chaperones.guide.Guide;
import com.chaperones.user.User;
import com.chaperones.venue.Venue;

import java.util.List;

public interface ActivityDAO {
    public int add(Activity activity);
    public List<Activity> getAll();
    public Activity getById(Integer id);
    public int updateById(Integer id, Activity update);
    public int deleteById(Integer id);
    public List<User> getAllUsersFromGivenActivity(Integer id);
}
