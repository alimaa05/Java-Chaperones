package venue;

import user.User;

import java.util.List;

public interface VenueDAO {
    public int add(Venue venue);
    public List<Venue> getAll();
    public Venue getById(Integer id);
    public int updateById(Integer id, Venue update);
    public int deleteById(Integer id);
}
