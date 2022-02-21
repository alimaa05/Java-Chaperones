package com.chaperones.guide;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("postgres")
public class GuideSQL implements GuideDAO {
    private JdbcTemplate jdbcTemplate;

    public GuideSQL(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int add(Guide guide) {
        String sql = """
            INSERT INTO guides(name, phoneNumber, email) 
            VALUES(?,?,?) 
            """;
        int rowsAffected = jdbcTemplate.update(
                sql, guide.getId(),
                guide.getName(),
                guide.getPhoneNumber(),
                guide.getEmail()
        );
        return rowsAffected;
    }

    @Override
    public List<Guide> getAll() {
        String sql = """
                SELECT id, name, phoneNumber, email 
                FROM  guides""";

        RowMapper<Guide> guideRowMapper = (rs, rowNum) -> {
            Guide guide = new Guide(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("phoneNumber"),
                    rs.getString("email")
            );
            return guide;
        };

        List<Guide> guides = jdbcTemplate.query(sql, guideRowMapper);
        return guides;
    }

    @Override
    public Guide getById(Integer id) {
        String sql = """
                SELECT id, name, phoneNumber, email 
                FROM guides""";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Guide(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("email")
                ), id);
    }

    @Override
    public int updateById(Integer id, Guide update) {
            String sql = """
                    UPDATE guides SET(name, phoneNumber, email) = (?, ?, ?) WHERE id = ?""";
            Guide original = getById(id);

            String newName = update.getName();
            if(newName == null){
                newName = original.getName();
            }
            String newPhone = update.getPhoneNumber();
            if(newPhone == null){
                newPhone = original.getPhoneNumber();
            }
            String newEmail = update.getEmail();
            if(newEmail == null) {
                newEmail = original.getEmail();
            }
            //this is the number of rows affected it is returning which is an integer
        // id is on the end as that is how we are identifying the row we want to change
            int updated = jdbcTemplate.update(sql, newName, newPhone, newEmail, id);
            return updated;
    }


    @Override
    public int deleteById(Integer id) {
        String sql = """
                DELETE FROM guides WHERE id = ?""";
        return jdbcTemplate.update(sql, id);

    }

}
