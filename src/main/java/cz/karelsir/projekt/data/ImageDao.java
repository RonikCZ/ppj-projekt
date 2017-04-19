package cz.karelsir.projekt.data;

/**
 * Created by akasa on 20.1.2015.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ImageDao {

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    public List<Image> getImages() {

        return jdbc
                .query("select * from image, user where image.id_user=user.id_user",
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

                            return image;
                        }
                );
    }


    public List<Image> getImages_innerjoin() {

        return jdbc
                .query("select * from image join user using (id_user)",
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

                            return image;
                        }
                );
    }

    public boolean update(Image image) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                image);
        return jdbc.update("update image set title=:title where id_image=:id_image", params) == 1;
    }

    public boolean changeLikes(Image image, boolean like) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_image", image.getId_image());
        if (like){
            image.setImage_likes(image.getImage_likes()+1);
            params.addValue("image_likes", image.getImage_likes()+1);
        }else{
            image.setImage_likes(image.getImage_likes()-1);
            params.addValue("image_likes", image.getImage_likes()-1);
        }
        return jdbc.update("update image set image_likes=:image_likes where id_image=:id_image", params) == 1;
    }

    public boolean create(Image image) {

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                image);

        return jdbc
                .update("insert into image (id_user, url, title, image_creation, image_lastedit) values (:user.id_user, :url, :title, :image_creation, :image_lastedit)",
                        params) == 1;
    }

    @Transactional
    public int[] create(List<Image> images) {

        SqlParameterSource[] params = SqlParameterSourceUtils
                .createBatch(images.toArray());

        return jdbc
                .batchUpdate("insert into image (id_user, url, title, image_creation, image_lastedit) values (:user.id_user, :url, :title, :image_creation, :image_lastedit)",
                        params);
    }

    public boolean delete(int id_image) {
        MapSqlParameterSource params = new MapSqlParameterSource("id_image", id_image);

        return jdbc.update("delete from image where id_image=:id_image", params) == 1;
    }

    public Image getImage(int id_image) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_image", id_image);

        return jdbc.queryForObject("select * from image, user where image.id_user=user.id_user and id_image=:id_image", params,
                new RowMapper<Image>() {

                    public Image mapRow(ResultSet rs, int rowNum)
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

                        return image;
                    }

                });
    }

    public Image getImage(String url) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("url", url);

        return jdbc.queryForObject("select * from image, user where image.id_user=user.id_user and url=:url", params,
                new RowMapper<Image>() {

                    public Image mapRow(ResultSet rs, int rowNum)
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

                        return image;
                    }

                });
    }

    public void deleteImage(int id_image) {
        String query = "DELETE FROM image WHERE id_image="+id_image;
        jdbc.getJdbcOperations().execute(query);
    }

    public void deleteImages() {
        jdbc.getJdbcOperations().execute("DELETE FROM comment");
        jdbc.getJdbcOperations().execute("DELETE FROM image");
    }


}
