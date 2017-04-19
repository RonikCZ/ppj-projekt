package cz.karelsir.projekt.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;

public class UserDao {

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    @Transactional
    public boolean create(User user) {

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("username", user.getUsername());
        params.addValue("user_registration", user.getUser_registration());

        return jdbc.update("insert into user (username, user_registration) values (:username, :user_registration)", params) == 1;
    }

    public boolean exists(String username) {
        return jdbc.queryForObject("select count(*) from user where username=:username",
                new MapSqlParameterSource("username", username), Integer.class) > 0;
    }

    public User getUser(int id_user) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_user", id_user);

        return jdbc.queryForObject("select * from user where id_user=:id_user", params,
                (ResultSet rs, int rowNum) -> {
                    User user = new User();
                    user.setId_user(rs.getInt("id_user"));
                    user.setUsername(rs.getString("username"));
                    user.setUser_registration(rs.getString("user_registration"));

                    return user;
                });
    }

    public User getUser(String username) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("username", username);

        return jdbc.queryForObject("select * from user where username=:username", params,
                (ResultSet rs, int rowNum) -> {
                    User user = new User();
                    user.setId_user(rs.getInt("id_user"));
                    user.setUsername(rs.getString("username"));
                    user.setUser_registration(rs.getString("user_registration"));

                    return user;
                });
    }



    public List<User> getAllUsers() {
        return jdbc
                .query("select * from user",
                        (ResultSet rs, int rowNum) -> {
                            User user = new User();
                            user.setId_user(rs.getInt("id_user"));
                            user.setUsername(rs.getString("username"));
                            user.setUser_registration(rs.getString("user_registration"));

                            return user;
                        }
                );
    }

    public void deleteUser(int id_user) {
        String query = "DELETE FROM user WHERE id_user="+id_user;
        jdbc.getJdbcOperations().execute(query);
    }

    public void deleteUsers() {
        jdbc.getJdbcOperations().execute("DELETE FROM user");
    }
}
