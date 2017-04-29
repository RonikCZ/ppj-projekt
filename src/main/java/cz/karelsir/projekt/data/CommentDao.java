package cz.karelsir.projekt.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Ronik on 7. 4. 2017.
 */
public class CommentDao {

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    public List<Comment> getComments() {

        return jdbc
                .query("SELECT * from comment", new CommentRowMapper());
    }

    public boolean update(Comment comment) {
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                comment);

        return jdbc.update("update comment set text=:text where id_comment=:id_comment", params) == 1;
    }

    public boolean create(Comment comment) {

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(
                comment);

        return jdbc
                .update("insert into comment (id_user, id_image, comment_creation, comment_lastedit, comment_likes, text)" +
                                "values (:id_user, :id_image, :comment_creation, :comment_lastedit, :comment_likes, :text)",
                        params) == 1;
    }

    public boolean changeLikes(Comment comment, boolean like) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_comment", comment.getId());
        if (like){
            comment.setComment_likes(comment.getComment_likes()+1);
            params.addValue("comment_likes", comment.getComment_likes()+1);
        }else{
            comment.setComment_likes(comment.getComment_likes()-1);
            params.addValue("comment_likes", comment.getComment_likes()-1);
        }
        return jdbc.update("update comment set comment_likes=:comment_likes where id_comment=:id_comment", params) == 1;
    }

    @Transactional
    public int[] create(List<Comment> comments) {

        SqlParameterSource[] params = SqlParameterSourceUtils
                .createBatch(comments.toArray());

        return jdbc
                .batchUpdate("insert into comment (id_user, id_image, comment_creation, comment_lastedit, comment_likes, text)" +
                                "values (:id_user, :id_image, :comment_creation, :comment_lastedit, :comment_likes, :text)",
                        params);
    }

    public void deleteComment(int id_comment) {
        String query = "DELETE FROM comment WHERE id_comment="+id_comment;
        jdbc.getJdbcOperations().execute(query);
    }

    public Comment getComment(int id_comment) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_comment", id_comment);

        return jdbc.queryForObject("SELECT * from comment WHERE id_comment=:id_comment", params, new CommentRowMapper());
    }

    public Comment getComment(String text) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("text", text);

        return jdbc.queryForObject("SELECT * from comment WHERE text=:text", params, new CommentRowMapper());
    }

    public void deleteComments() {
        jdbc.getJdbcOperations().execute("DELETE FROM comment");
    }

}

class CommentRowMapper implements RowMapper<Comment>
{
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException{
        Comment comment = new Comment();
        comment.setId(rs.getInt("id_comment"));
        comment.setComment_creation(rs.getString("comment_creation"));
        comment.setComment_lastedit(rs.getString("comment_lastedit"));
        comment.setComment_likes(rs.getInt("comment_likes"));
        comment.setText(rs.getString("text"));
        comment.setId_user(rs.getInt("id_user"));
        comment.setId_image(rs.getInt("id_image"));

        return comment;
    }
}
