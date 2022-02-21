package venue;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postgres")
public class VenueSQL implements venue.VenueDAO {

    private JdbcTemplate jdbcTemplate;

    public VenueSQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(venue.Venue venue) {

        String sql = "INSERT INTO venues (name, area, address) VALUES(?, ?, ?)";

        return jdbcTemplate.update(sql,
            venue.getName(),
            venue.getArea(),
            venue.getAddress()
        );
    }

    @Override
    public List<venue.Venue> getAll() {

        String sql = "SELECT id, name, area, address FROM venues";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new venue.Venue(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("area"),
                rs.getString("address")
            )
        );
    }

    @Override
    public venue.Venue getById(Integer id) {

        String sql = "SELECT id, name, area, address FROM venues WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new venue.Venue(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("area"),
                rs.getString("address")
            ),
            id
        );
    }

    @Override
    public int updateById(Integer id, venue.Venue update) {

        String sql = "UPDATE venues SET (name, area, address)=(?, ?, ?) WHERE id = ?";

        venue.Venue original = getById(id);

        String newName = update.getName();
        if (newName == null) newName = original.getName();
        String newArea = update.getArea();
        if (newArea == null) newArea = original.getArea();
        String newAddress = update.getAddress();
        if (newAddress == null) newAddress = original.getAddress();

        return jdbcTemplate.update(sql,
                newName,
                newArea,
                newAddress,
                id
        );
    }

    @Override
    public int deleteById(Integer id) {

        String sql = "DELETE FROM venues WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
