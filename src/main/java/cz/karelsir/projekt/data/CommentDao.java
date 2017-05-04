package cz.karelsir.projekt.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Ronik on 7. 4. 2017.
 */
public class CommentDao {

    @Autowired
    private NamedParameterJdbcOperations jdbc;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final static String CREATE_SQL = "insert into comment (id_user, id_image, comment_creation, comment_lastedit, comment_likes, text)" +
            "values (:id_user, :id_image, :comment_creation, :comment_lastedit, :comment_likes, :text)";

    @Transactional
    public long create(Comment comment) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id_image", comment.getId_image());
        params.addValue("id_user", comment.getId_user());
        params.addValue("comment_creation", Timestamp.valueOf(comment.getComment_creation()));
        params.addValue("comment_lastedit", Timestamp.valueOf(comment.getComment_lastedit()));
        params.addValue("comment_likes", comment.getComment_likes());
        params.addValue("text", comment.getText());

        int numberOfAffectedRows = namedParameterJdbcTemplate.update(CREATE_SQL,
                params,
                generatedKeyHolder);

        comment.setId(generatedKeyHolder.getKey().intValue());
        return numberOfAffectedRows == 1 ? generatedKeyHolder.getKey().longValue() : -1L;
    }

    @Transactional
    public int create(List<Comment> comments) {
        int numberOfAffectedRows = 0;
        for (Comment comment:comments
                ) {
            numberOfAffectedRows+=create(comment);
        }
        return numberOfAffectedRows;

    }

    public List<Comment> getComments() {

        return jdbc
                .query("SELECT * from comment", new CommentRowMapper());
    }

    public boolean update(Comment comment) {
        comment.setComment_lastedit(LocalDateTime.now());

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", comment.getId());
        params.addValue("text", comment.getText());
        params.addValue("comment_lastedit", Timestamp.valueOf(comment.getComment_lastedit()));

        return jdbc.update("update comment set text=:text, comment_lastedit:=comment_lastedit where id_comment=:id", params) == 1;
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

    @Transactional
    public void deleteComments() {
        jdbc.getJdbcOperations().execute("DELETE FROM comment");
    }

}

class CommentRowMapper implements RowMapper<Comment>
{
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException{
        Comment comment = new Comment();
        comment.setId(rs.getInt("id_comment"));
        comment.setComment_creation(rs.getTimestamp("comment_creation").toLocalDateTime());
        comment.setComment_lastedit(rs.getTimestamp("comment_lastedit").toLocalDateTime());
        comment.setComment_likes(rs.getInt("comment_likes"));
        comment.setText(rs.getString("text"));
        comment.setId_user(rs.getInt("id_user"));
        comment.setId_image(rs.getInt("id_image"));

        return comment;
    }
}
