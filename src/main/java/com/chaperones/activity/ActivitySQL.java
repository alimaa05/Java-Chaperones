package activity;

import guide.Guide;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import venue.Venue;

import java.util.List;

@Repository("postgres")
public class ActivitySQL implements ActivityDAO{

    private JdbcTemplate jdbcTemplate;

    public ActivitySQL(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to add an activity
    @Override
    public int add(Activity activity, Guide guide, Venue venue){
        String sql = """
                INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        int rowAffected = jdbcTemplate.update(
                sql,
                guide.getId(),
                venue.getId(),
                activity.getName(),
                activity.getDescription(),
                activity.getDate(),
                activity.getTime(),
                activity.getDuration(),
                activity.getPrice(),
                activity.getCapacity()

        );
        return rowAffected;

    };

    // ----------------------------------------------------------

    // Method for getting all the cars
    @Override
    public List<Activity> getAll(){
        String sql = """
                SELECT id, name, description, date, time, duration, price, capacity, cancelled
                FROM activities
                """;
        RowMapper<Activity> activityRowMapper = (rs, rowNum) ->
           new Activity(
                  rs.getInt("id"),
                  rs.getString("name"),
                  rs.getString("description"),
                  rs.getInt("date"),
                  rs.getInt("time"),
                  rs.getString("duration"),
                  rs.getDouble("price"),
                  rs.getInt("capacity"),
                  rs.getBoolean("cancelled")
          );


        return jdbcTemplate.query(sql, activityRowMapper);

    };

    // ----------------------------------------------------------

    // Method for getting an activity by the id
    @Override
    public Activity getById(int id){
        String sql = """
                SELECT id, name, description, date, time, duration, price, capacity, cancelled
                FROM activities
                WHERE id = ?"
                """;

        //sql object - pass as string
        // rs - everything between the green brackets - takes the result set
        // rowNum - argument you're passing in this case id
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
            new Activity(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getInt("date"),
                    rs.getInt("time"),
                    rs.getString("duration"),
                    rs.getDouble("price"),
                    rs.getInt("capacity"),
                    rs.getBoolean("cancelled")
            ),
                id

        );

    };

    // ----------------------------------------------------------

    // Method for updating an activity by the id
    @Override
    public void updateById(int id, Activity update){

    };
    public void deleteById(int id){

    };
}
