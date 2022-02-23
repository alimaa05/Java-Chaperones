package com.chaperones.activity;


import com.chaperones.user.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.chaperones.guide.Guide;
import com.chaperones.venue.Venue;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository("activitiesPostgres")
public class ActivitySQL implements ActivityDAO {

    private JdbcTemplate jdbcTemplate;

    public ActivitySQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method to add an activity
    @Override
    public int add(Activity activity) {
        String sql = """
                INSERT INTO activities (guide_id, venue_id, name, description, date, time, duration, price, capacity)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        int rowAffected = jdbcTemplate.update(
                sql,
                activity.getGuide_id(),
                activity.getVenue_id(),
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

    // Method for getting all the activities
    @Override
    public List<Activity> getAll() {
        String sql = """
                SELECT id, guide_id, venue_id, name, description, date, time, duration, price, capacity, cancelled
                FROM activities
                """;
        //Result set (rs) - sets the result of the whole row
        //RowNumber - how many rows there are - as long as there is a next row it will carry on then stops when there’s no more
        RowMapper<Activity> activityRowMapper = (rs, rowNum) ->
                new Activity(
                        rs.getInt("id"),
                        rs.getInt("guide_id"),
                        rs.getInt("venue_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        LocalDate.parse(rs.getString("date")),
                        LocalTime.parse(rs.getString("time")),
                        rs.getString("duration"),
                        rs.getDouble("price"),
                        rs.getInt("capacity"),
                        rs.getBoolean("cancelled")
                );


        //the 'sql' in parenthesis after  jdbcTemplate.query is the placeholder from String sql = """...
        return jdbcTemplate.query(sql, activityRowMapper);

    }


    // ----------------------------------------------------------

    // Method for getting an activity by the id
    @Override
    public Activity getById(Integer id) {
        String sql = """
                SELECT id, guide_id, venue_id, name, description, date, time, duration, price, capacity, cancelled
                FROM activities
                WHERE id = ?
                """;

        //sql object - pass as string
        // rs - everything between the green brackets - takes the result set
        // rowNum - argument you're passing in this case id
        try {
            //QueryForObject - only returns one line
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                    new Activity(
                            rs.getInt("id"),
                            rs.getInt("guide_id"),
                            rs.getInt("venue_id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            LocalDate.parse(rs.getString("date")),
                            LocalTime.parse(rs.getString("time")),
                            rs.getString("duration"),
                            rs.getDouble("price"),
                            rs.getInt("capacity"),
                            rs.getBoolean("cancelled")
                            //Get it by that id we pass in (the argument we’re passing)
                    ), id);

        } catch (Exception e) {
            return null;
        }

    }


    // ----------------------------------------------------------

    // Method for updating an activity by the id
    @Override
    public int updateById(Integer id, Activity update) {
        String sql = """
                UPDATE activities
                SET (guide_id, venue_id, name, description, date, time, duration, price, capacity, cancelled)=(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                WHERE id = ?
                """;

        // creating a new variable which is equal to the getbyId method- we want to update it by the right id (the one we're passing)
        // Can’t call a method and then equal another method - need to create a placeholder
        // id - show that this is the id and row we changed
        Activity original = getById(id);

        Integer newGuide_id = update.getGuide_id();
        if (newGuide_id == null) newGuide_id = original.getGuide_id();

        Integer newVenue_id = update.getVenue_id();
        if (newVenue_id == null) newVenue_id = original.getVenue_id();

        // create a variable called newName that is equal to updated name entered
        String newName = update.getName();
        // if the newName is null then the newName will equal the original activity name of that specific id
        if (newName == null) newName = original.getName();

        String newDescription = update.getDescription();
        if (newDescription == null) newDescription = original.getDescription();

        LocalDate newDate = update.getDate();
        if (newDate == null) newDate = original.getDate();

        LocalTime newTime = update.getTime();
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
                newGuide_id,
                newVenue_id,
                newName,
                newDescription,
                newDate,
                newTime,
                newDuration,
                newPrice,
                newCapacity,
                newCancelled,
                id
        );

    }

    // ----------------------------------------------------------

    public int deleteById(Integer id) {

        String sql = """
                DELETE FROM activities WHERE id = ?
                """;

        return jdbcTemplate.update(sql, id);

    }

    // ----------------------------------------------------------

    // Method to get all the users booked on a given activity

    // want a list of all the users
    public List<User> getAllUsersFromGivenActivity(Integer id) {
        String sql = """
                SELECT users.id, users.name, users.phoneNumber, users.email
                FROM ((users
                INNER JOIN bookings
                ON users.id = bookings.user_id)
                INNER JOIN activities
                ON bookings.activity_id = activities.id)
                WHERE activity_id = ?
                """;


        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("email")

                ), id);


    }

// ----------------------------------------------------------


    public int getFreeSpaces(Integer id){
        String sql = """
                SELECT
                (SELECT capacity
                FROM activities
                WHERE id = ?) -
                (SELECT COUNT (DISTINCT user_id)
                FROM bookings
                WHERE activity_id = ?) AS total
                """;


        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id, id);

        if (count == null){
            count = 0;
        }
                return count;
    }


}
