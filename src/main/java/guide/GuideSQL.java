package guide;


import org.springframework.jdbc.core.JdbcTemplate;
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
    }



    public List<Guide> getAll() {

    }

    ;

    public Guide getById(int id) {

    }

    ;

    public int updateById(int id, Guide update) {

    }

    ;

    public int deleteById(int id) {

    }

    ;
}
