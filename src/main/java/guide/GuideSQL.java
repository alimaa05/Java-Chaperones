package guide;


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
    };

    @Override
    public Guide getById(int id) {
        String sql = """
                SELECT id, name, phoneNumber, email 
                FROM guides""";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Guide(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phoneNumber"),
                        rs.getString("email")
                ), id);
    };


    public int updateById(int id, Guide update) {

    }

    ;

    public int deleteById(int id) {

    }

    ;
}
