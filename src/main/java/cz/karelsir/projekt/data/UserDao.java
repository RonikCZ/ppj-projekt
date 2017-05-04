package cz.karelsir.projekt.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class UserDao {

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String CREATE_SQL = "insert into user (username, user_registration) values (:username, :user_registration)";

    @Transactional
    public long create(User user) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("username", user.getUsername());
        params.addValue("user_registration", Timestamp.valueOf(user.getUser_registration()));

        int numberOfAffectedRows = namedParameterJdbcTemplate.update(CREATE_SQL,
                params,
                generatedKeyHolder);

        user.setId(generatedKeyHolder.getKey().intValue());
        return numberOfAffectedRows == 1 ? generatedKeyHolder.getKey().longValue() : -1L;
    }

    public boolean exists(String username) {
        return jdbc.queryForObject("select count(*) from user where username=:username",
                new MapSqlParameterSource("username", username), Integer.class) > 0;
    }

    public User getUser(int id_user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_user", id_user);

        return jdbc.queryForObject("select * from user where id_user=:id_user", params, new UserRowMapper());
    }

    public User getUser(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        return jdbc.queryForObject("select * from user where username=:username", params, new UserRowMapper());
    }

    public List<User> getAllUsers() {
        return jdbc
                .query("select * from user", new UserRowMapper());
    }

    @Transactional
    public void deleteUser(int id_user) {
        String query = "DELETE FROM user WHERE id_user="+id_user;
        jdbc.getJdbcOperations().execute(query);
    }

    @Transactional
    public void deleteUsers() {
        jdbc.getJdbcOperations().execute("DELETE FROM user");
    }
}

class UserRowMapper implements RowMapper<User>
{
    public User mapRow(ResultSet rs, int rowNum) throws SQLException{
        User user = new User();
        user.setId(rs.getInt("id_user"));
        user.setUsername(rs.getString("username"));
        user.setUser_registration(rs.getTimestamp("user_registration").toLocalDateTime());
        return user;
    }
}
