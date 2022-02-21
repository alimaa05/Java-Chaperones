package activity;

import guide.Guide;
import venue.Venue;

import java.util.List;

public interface ActivityDAO {
    public int add(Activity activity, Guide guide, Venue venue);
    public List<Activity> getAll();
    public Activity getById(Integer id);
    public int updateById(Integer id, Activity update);
    public int deleteById(Integer id);
}
