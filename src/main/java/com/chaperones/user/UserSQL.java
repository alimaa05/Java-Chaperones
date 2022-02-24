package com.chaperones.user;

import com.chaperones.activity.Activity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository("userPostgres")
public class UserSQL implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    public UserSQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(User user) {
        String sql = "INSERT INTO users (name, phoneNumber, email) VALUES(?, ?, ?)";
        int rowsAffected = jdbcTemplate.update(
                sql,
                user.getName(),
                user.getPhoneNumber(),
                user.getEmail()
        );
        return rowsAffected;
    };

    @Override
    public List<User> getAll() {

        String sql = """
                SELECT id, name, phoneNumber, email
                FROM users
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("email")
                )
        );
    }

    @Override
    public User getById(Integer id) {

        String sql = "SELECT id, name, phoneNumber, email FROM users WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            new User(
                                    rs.getInt("id"),
                                    rs.getString("name"),
                                    rs.getString("phoneNumber"),
                                    rs.getString("email")
                            ),
                    id
            );
        } catch (Exception e) {
            return null;
        }
    }

    public List<Activity> getActivities(Integer id, boolean cancelled) {

        String sql = """
                SELECT activities.id, activities.guide_id, activities.venue_id, activities.name, activities.description, activities.date, activities.time, activities.duration, activities.price, activities.capacity, activities.cancelled 
                FROM ((activities
                INNER JOIN bookings
                ON activities.id = bookings.activity_id)
                INNER JOIN users
                ON bookings.user_id = users.id)
                WHERE user_id = ? AND cancelled = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
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
                        ),
                id,
                cancelled
        );
    }

    @Override
    public int updateById(Integer id, User update){

        String sql = "UPDATE users SET (name, phoneNumber, email)=(?, ?, ?) WHERE id = ?";

        User original = getById(id);

        String newName = update.getName();
        if (newName == null) newName = original.getName();
        String newPhoneNumber = update.getPhoneNumber();
        if (newPhoneNumber == null) newPhoneNumber = original.getPhoneNumber();
        String newEmail = update.getEmail();
        if (newEmail == null) newEmail = original.getEmail();

        return jdbcTemplate.update(
                sql,
                newName,
                newPhoneNumber,
                newEmail,
                id
        );
    };

    @Override
    public int deleteById(Integer id){

        String sql = "DELETE FROM users WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    };

    @Override
    public int addUserToActivity(Integer user_id, Integer activity_id){
        String sql = """
                INSERT INTO bookings
                (user_id, activity_id) VALUES (?, ?);
                """;

        return jdbcTemplate.update(sql, user_id, activity_id);
    }

    @Override
    public int removeUserFromActivity(Integer user_id, Integer activity_id){
        String sql = """
                DELETE FROM bookings WHERE 
                (user_id, activity_id) = (?, ?);
                """;

        return jdbcTemplate.update(sql, user_id, activity_id);
    }

    public int getFreeSpaces(Integer activity_id){
        String sql = """
                SELECT
                (SELECT capacity
                FROM activities
                WHERE id = ?) -
                (SELECT COUNT (DISTINCT user_id)
                FROM bookings
                WHERE activity_id = ?) AS total
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, activity_id, activity_id);

        if (count == null){
            count = 0;
        }
        return count;
    }
}
