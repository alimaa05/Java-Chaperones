package activity;

import guide.Guide;
import org.springframework.jdbc.core.JdbcTemplate;
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

    }

    // ----------------------------------------------------------

    public List<Activity> getAll(){

    };
    public Activity getById(int id){

    };
    public void updateById(int id, Activity update){

    };
    public void deleteById(int id){

    };
}
