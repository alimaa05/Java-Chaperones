package com.chaperones.venue;

import com.chaperones.activity.Activity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository("venuePostgres")
public class VenueSQL implements VenueDAO {

    private JdbcTemplate jdbcTemplate;

    public VenueSQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(Venue venue) {

        String sql = "INSERT INTO venues (name, area, address) VALUES(?, ?, ?)";

        return jdbcTemplate.update(sql,
            venue.getName(),
            venue.getArea(),
            venue.getAddress()
        );
    }

    @Override
    public List<Venue> getAll() {

        String sql = "SELECT id, name, area, address FROM venues";

        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new Venue(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("area"),
                rs.getString("address")
            )
        );
    }

    public List<Activity> getActivities(Integer id, boolean cancelled) {

        String sql = "SELECT id, guide_id, venue_id, name, description, date, time, duration, price, capacity, cancelled FROM activities WHERE venue_id = ? AND cancelled = ?";

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
    public Venue getById(Integer id) {

        String sql = "SELECT id, name, area, address FROM venues WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Venue(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("area"),
                        rs.getString("address")
                ),
                id
            );
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int updateById(Integer id, Venue update) {

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
    }

    @Override
    public int deleteById(Integer id) {

        String sql = "DELETE FROM venues WHERE id = ?";

        return jdbcTemplate.update(sql, id);
    }
}
