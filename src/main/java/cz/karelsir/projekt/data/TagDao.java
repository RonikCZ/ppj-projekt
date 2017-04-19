package cz.karelsir.projekt.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ronik on 8. 4. 2017.
 */
public class TagDao {
    @Autowired
    private NamedParameterJdbcOperations jdbc;

    public List<Tag> getTags() {

        return jdbc
                .query("SELECT * FROM tag, image, user where tag.id_image=image.id_image and image.id_user=user.id_user",
                        (ResultSet rs, int rowNum) -> {
                            User user = new User();
                            user.setId_user(rs.getInt("id_user"));
                            user.setUsername(rs.getString("username"));
                            user.setUser_registration(rs.getString("user_registration"));

                            Image image = new Image();
                            image.setId_image(rs.getInt("id_image"));
                            image.setUrl(rs.getString("url"));
                            image.setTitle(rs.getString("title"));
                            image.setImage_creation(rs.getString("image_creation"));
                            image.setImage_lastedit(rs.getString("image_lastedit"));
                            image.setImage_likes(rs.getInt("image_likes"));
                            image.setUser(user);

                            Tag tag = new Tag();
                            tag.setTag_title(rs.getString("tag_title"));
                            tag.setImage(image);

                            return tag;
                        }
                );
    }

    public boolean create(Tag tag) {

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                tag);

        return jdbc
                .update("insert into tag (tag_title, id_image)" +
                                "values (:tag_title, :image.id_image)",
                        params) == 1;
    }

    @Transactional
    public int[] create(List<Tag> tags) {

        SqlParameterSource[] params = SqlParameterSourceUtils
                .createBatch(tags.toArray());

        return jdbc
                .batchUpdate("insert into tag (tag_title, id_image)" +
                                "values (:tag_title, :image.id_image)",
                        params);
    }

    public boolean delete(Tag tag) {
        MapSqlParameterSource params = new MapSqlParameterSource("id_image", tag.getImage().getId_image());
        params.addValue("tag_title", tag.getTag_title());

        return jdbc.update("delete from tag where id_image=:id_image and tag_title=:tag_title", params) == 1;
    }

    public Tag getTag(int id_image, String tag_title) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_image", id_image);
        params.addValue("tag_title", tag_title);


        return jdbc.queryForObject("SELECT * FROM tag, image, user where tag.id_image=image.id_image and tag.id_image=:id_image and tag_title=:tag_title and image.id_user=user.id_user", params,
                new RowMapper<Tag>() {

                    public Tag mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        User user = new User();
                        user.setId_user(rs.getInt("id_user"));
                        user.setUsername(rs.getString("username"));
                        user.setUser_registration(rs.getString("user_registration"));

                        Image image = new Image();
                        image.setId_image(rs.getInt("id_image"));
                        image.setUrl(rs.getString("url"));
                        image.setTitle(rs.getString("title"));
                        image.setImage_creation(rs.getString("image_creation"));
                        image.setImage_lastedit(rs.getString("image_lastedit"));
                        image.setImage_likes(rs.getInt("image_likes"));
                        image.setUser(user);

                        Tag tag = new Tag();
                        tag.setTag_title(rs.getString("tag_title"));
                        tag.setImage(image);

                        return tag;
                    }

                });
    }


    public void deleteTags() {
        jdbc.getJdbcOperations().execute("DELETE FROM tag");
    }


}