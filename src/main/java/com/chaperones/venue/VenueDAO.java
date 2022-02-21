package venue;

import java.util.List;

public interface VenueDAO {
    public int add(venue.Venue venue);
    public List<venue.Venue> getAll();
    public venue.Venue getById(Integer id);
    public int updateById(Integer id, venue.Venue update);
    public int deleteById(Integer id);
}
