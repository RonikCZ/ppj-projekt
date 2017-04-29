package cz.karelsir.projekt.data;

/**
 * Created by akasa on 20.1.2015.
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ImageDao {

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String CREATE_SQL = "insert into image (id_user, url, title, image_creation, image_lastedit) values (:id_user, :url, :title, :image_creation, :image_lastedit)";

    @Transactional
    public long create(Image image) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                image);

        int numberOfAffectedRows = namedParameterJdbcTemplate.update(CREATE_SQL,
                params,
                generatedKeyHolder);

        image.setId(generatedKeyHolder.getKey().intValue());
        return numberOfAffectedRows == 1 ? generatedKeyHolder.getKey().longValue() : -1L;
    }

    @Transactional
    public int create(List<Image> images) {
        int numberOfAffectedRows = 0;
        for (Image image:images
             ) {
            numberOfAffectedRows+=create(image);
        }
        return numberOfAffectedRows;
    }

    public List<Image> getImages() {
        return jdbc
                .query("select * from image", new ImageRowMapper());
    }

    public boolean update(Image image) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                image);
        return jdbc.update("update image set title=:title where id_image=:id", params) == 1;
    }

    public boolean changeLikes(Image image, boolean like) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_image", image.getId());
        if (like){
            image.setImage_likes(image.getImage_likes()+1);
            params.addValue("image_likes", image.getImage_likes()+1);
        }else{
            image.setImage_likes(image.getImage_likes()-1);
            params.addValue("image_likes", image.getImage_likes()-1);
        }
        return jdbc.update("update image set image_likes=:image_likes where id_image=:id_image", params) == 1;
    }

    public boolean delete(int id_image) {
        MapSqlParameterSource params = new MapSqlParameterSource("id_image", id_image);

        return jdbc.update("delete from image where id_image=:id_image", params) == 1;
    }

    public Image getImage(int id_image) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_image", id_image);

        return jdbc.queryForObject("select * from image where id_image=:id_image", params, new ImageRowMapper());
    }

    public Image getImage(String url) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("url", url);

        return jdbc.queryForObject("select * from image where url=:url", params, new ImageRowMapper());
    }

    public void deleteImage(int id_image) {
        String query = "DELETE FROM image WHERE id_image="+id_image;
        jdbc.getJdbcOperations().execute(query);
    }

    public void deleteImages() {
        jdbc.getJdbcOperations().execute("DELETE FROM image");
    }

}

class ImageRowMapper implements RowMapper<Image>
{
    public Image mapRow(ResultSet rs, int rowNum) throws SQLException{
        Image image = new Image();
        image.setId(rs.getInt("id_image"));
        image.setId_user(rs.getInt("id_user"));
        image.setUrl(rs.getString("url"));
        image.setTitle(rs.getString("title"));
        image.setImage_creation(rs.getString("image_creation"));
        image.setImage_lastedit(rs.getString("image_lastedit"));
        image.setImage_likes(rs.getInt("image_likes"));

        return image;
    }
}
