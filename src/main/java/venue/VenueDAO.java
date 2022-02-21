package venue;

import user.User;

import java.util.List;

public interface VenueDAO {
    public int add(Venue venue);
    public List<Venue> getAll();
    public Venue getById(int id);
    public int updateById(int id, Venue update);
    public int deleteById(int id);
}
