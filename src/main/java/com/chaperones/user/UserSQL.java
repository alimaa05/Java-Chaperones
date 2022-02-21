package com.chaperones.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userPostgres")
public class UserSQL implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    public UserSQL(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
        public int add(User user) {
        String sql = """
                INSERT INTO users (name, phoneNumber, email)
                VALUES(?, ?, ?)
                """;
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
                FROM user
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
        public User getById(int id) {

        String sql = "SELECT id, name, phoneNumber, email FROM venues WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                        new User(
                                rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("phoneNumber"),
                                rs.getString("email")
                        ),
                id
        );
    }

    @Override
        public int updateById(int id, User update){

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
        public int deleteById(int id){

            String sql = "DELETE FROM users WHERE id = ?";

            return jdbcTemplate.update(sql, id);
        };
}
