package com.chaperones.activity;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.chaperones.guide.Guide;
import com.chaperones.venue.Venue;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository("postgres")
public class ActivitySQL implements ActivityDAO {

    private JdbcTemplate jdbcTemplate;

    public ActivitySQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to add an activity
    @Override
    public int add(Activity activity, Guide guide, Venue venue) {
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

    ;

    // ----------------------------------------------------------

    // Method for getting all the cars
    @Override
    public List<Activity> getAll() {
        String sql = """
                SELECT id, name, description, date, time, duration, price, capacity, cancelled
                FROM activities
                """;
        RowMapper<Activity> activityRowMapper = (rs, rowNum) ->
                new Activity(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("date"),
                        rs.getString("time"),
                        rs.getString("duration"),
                        rs.getDouble("price"),
                        rs.getInt("capacity"),
                        rs.getBoolean("cancelled")
                );


        return jdbcTemplate.query(sql, activityRowMapper);

    }

    ;

    // ----------------------------------------------------------

    // Method for getting an activity by the id
    @Override
    public Activity getById(Integer id) {
        String sql = """
                SELECT id, name, description, date, time, duration, price, capacity, cancelled
                FROM activities
                WHERE id = ?
                """;

        //sql object - pass as string
        // rs - everything between the green brackets - takes the result set
        // rowNum - argument you're passing in this case id
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        new Activity(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("description"),
                                rs.getString("date"),
                                rs.getString("time"),
                                rs.getString("duration"),
                                rs.getDouble("price"),
                                rs.getInt("capacity"),
                                rs.getBoolean("cancelled")
                        ),
                id

        );

    }

    ;

    // ----------------------------------------------------------

    // Method for updating an activity by the id
    @Override
    public int updateById(Integer id, Activity update) {
        String sql = """
                UPDATE activities
                SET (name, description, date, time, duration, price, capacity, cancelled) = (?, ?, ?, ?, ?, ?, ?, ?)
                WHERE id = ?
                """;

        // creating a new variable which is equal to the getbyId method- we want to update it by the id
        Activity original = getById(id);

        // create a variable called newName that is equal to updated name entered
        String newName = update.getName();
        // if the newName is null then the newName will equal the original activity name of that specific id
        if (newName == null) newName = original.getName();

        String newDescription = update.getDescription();
        if (newDescription == null) newDescription = original.getDescription();

        String newDate = update.getDate();
        if (newDate == null) newDate = original.getDate();

        String newTime = update.getTime();
        if (newTime == null) newTime = original.getTime();

        String newDuration = update.getDuration();
        if (newDuration == null) newDuration = original.getDuration();

        Double newPrice = update.getPrice();
        if (newPrice == null) newPrice = original.getPrice();

        Integer newCapacity = update.getCapacity();
        if (newCapacity == null) newCapacity = original.getCapacity();

        Boolean newCancelled = update.getCancelled();
        if (newCancelled == null) newCancelled = original.getCancelled();

        // returning the variables made above
        return jdbcTemplate.update(sql,
                newName,
                newDescription,
                newDate,
                newTime,
                newDuration,
                newPrice,
                newCapacity,
                newCancelled

        );

    };

    // ----------------------------------------------------------

    public int deleteById(Integer id) {

        String sql = """
                DELETE FROM activities WHERE id = ?
                """;

        return jdbcTemplate.update(sql, id);

    }

}
