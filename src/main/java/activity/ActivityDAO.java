package activity;

import guide.Guide;
import venue.Venue;

import java.util.List;

public interface ActivityDAO {
    public void add(Activity activity, Guide guide, Venue venue);
    public List<Activity> getAll();
    public Activity getById(int id);
    public void updateById(int id, Activity update);
    public void deleteById(int id);
}
