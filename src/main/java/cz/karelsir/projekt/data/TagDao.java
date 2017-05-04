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
                .query("SELECT * FROM tag", new TagRowMapper());
    }

    @Transactional
    public boolean create(Tag tag) {

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                tag);

        return jdbc
                .update("insert into tag (tag_title, id_image)" +
                                "values (:tag_title, :id_image)",
                        params) == 1;
    }

    @Transactional
    public int[] create(List<Tag> tags) {

        SqlParameterSource[] params = SqlParameterSourceUtils
                .createBatch(tags.toArray());

        return jdbc
                .batchUpdate("insert into tag (tag_title, id_image)" +
                                "values (:tag_title, :id_image)",
                        params);
    }

    @Transactional
    public boolean delete(Tag tag) {
        MapSqlParameterSource params = new MapSqlParameterSource("id_image", tag.getId_image());
        params.addValue("tag_title", tag.getTag_title());

        return jdbc.update("delete from tag where id_image=:id_image and tag_title=:tag_title", params) == 1;
    }

    public Tag getTag(int id_image, String tag_title) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_image", id_image);
        params.addValue("tag_title", tag_title);


        return jdbc.queryForObject("SELECT * FROM tag WHERE id_image=:id_image and tag_title=:tag_title",
                params, new TagRowMapper());
    }

    public void deleteTags() {
        jdbc.getJdbcOperations().execute("DELETE FROM tag");
    }

}

class TagRowMapper implements RowMapper<Tag>
{
    public Tag mapRow(ResultSet rs, int rowNum) throws SQLException{
        Tag tag = new Tag();
        tag.setTag_title(rs.getString("tag_title"));
        tag.setId_image(rs.getInt("id_image"));

        return tag;
    }
}
