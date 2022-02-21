package venue;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postgres")
public class VenueSQL implements VenueDAO{

    private JdbcTemplate jdbcTemplate;

    public VenueSQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int add(Venue venue){

        String sql = "INSERT INTO venues (name, area, address) VALUES(?, ?, ?)";

        return jdbcTemplate.update(sql,
            venue.getName(),
            venue.getArea(),
            venue.getAddress()
        );
    };

    public List<Venue> getAll(){

        String sql = "SELECT id, name, area, address FROM venues";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Venue(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("area"),
                rs.getString("address")
            )
        );
    };

    public Venue getById(int id){

        String sql = "SELECT id, name, area, address FROM venues WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new Venue(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("area"),
                rs.getString("address")
            ),
            id
        );

    };
    public int updateById(int id, Venue update){

        String sql = "UPDATE venues SET (name, area, address)=(?, ?, ?) WHERE id = ?";

        Venue original = getById(id);

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

    };
    public int deleteById(int id){

        String sql = "DELETE FROM venues WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    };
}
