package venue;

import user.User;

import java.util.List;

public interface VenueDAO {
    public void add(Venue venue);
    public List<Venue> getAll();
    public Venue getById(int id);
    public void updateById(int id, Venue update);
    public void deleteById(int id);
}
