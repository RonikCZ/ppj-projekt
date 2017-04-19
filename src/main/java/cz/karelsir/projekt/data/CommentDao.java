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
                .query("SELECT id_comment, comment.id_user as 'commenter_id', user2.username as 'commenter_username'," +
                                "user2.user_registration  as 'commenter_user_registration', comment.id_image," +
                                "comment_creation, comment_lastedit, comment_likes, text, url, title, image_creation," +
                                "image_lastedit, image_likes, image.id_user as 'creator_id'," +
                                "user.username as 'creator_username', user.user_registration as 'creator_user_registration'" +
                                " FROM comment, image INNER JOIN user ON (image.id_user=user.id_user)," +
                                "user AS user2 WHERE comment.id_user=user2.id_user and comment.id_image=image.id_image",
                        (ResultSet rs, int rowNum) -> {
                            User commenter = new User();
                            commenter.setId_user(rs.getInt("commenter_id"));
                            commenter.setUsername(rs.getString("commenter_username"));
                            commenter.setUser_registration(rs.getString("commenter_user_registration"));

                            User creator = new User();
                            creator.setId_user(rs.getInt("creator_id"));
                            creator.setUsername(rs.getString("creator_username"));
                            creator.setUser_registration(rs.getString("creator_user_registration"));

                            Image image = new Image();
                            image.setId_image(rs.getInt("id_image"));
                            image.setUrl(rs.getString("url"));
                            image.setTitle(rs.getString("title"));
                            image.setImage_creation(rs.getString("image_creation"));
                            image.setImage_lastedit(rs.getString("image_lastedit"));
                            image.setImage_likes(rs.getInt("image_likes"));
                            image.setUser(creator);

                            Comment comment = new Comment();
                            comment.setId_comment(rs.getInt("id_image"));
                            comment.setComment_creation(rs.getString("comment_creation"));
                            comment.setComment_lastedit(rs.getString("comment_lastedit"));
                            comment.setComment_likes(rs.getInt("comment_likes"));
                            comment.setText(rs.getString("text"));
                            comment.setUser(commenter);
                            comment.setImage(image);

                            return comment;
                        }
                );
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
                                "values (:user.id_user, :image.id_image, :comment_creation, :comment_lastedit, :comment_likes, :text)",
                        params) == 1;
    }

    public boolean changeLikes(Comment comment, boolean like) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_comment", comment.getId_comment());
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
                                "values (:user.id_user, :image.id_image, :comment_creation, :comment_lastedit, :comment_likes, :text)",
                        params);
    }

    public void deleteComment(int id_comment) {
        String query = "DELETE FROM comment WHERE id_comment="+id_comment;
        jdbc.getJdbcOperations().execute(query);
    }

    public Comment getComment(int id_comment) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id_comment", id_comment);

        return jdbc.queryForObject("SELECT id_comment, comment.id_user as 'commenter_id', user2.username as 'commenter_username'," +
                        "user2.user_registration  as 'commenter_user_registration', comment.id_image," +
                        "comment_creation, comment_lastedit, comment_likes, text, url, title, image_creation," +
                        "image_lastedit, image_likes, image.id_user as 'creator_id'," +
                        "user.username as 'creator_username', user.user_registration as 'creator_user_registration'" +
                        " FROM comment, image INNER JOIN user ON (image.id_user=user.id_user)," +
                        "user AS user2 WHERE comment.id_user=user2.id_user and comment.id_image=image.id_image and id_comment=:id_comment", params,
                new RowMapper<Comment>() {

                    public Comment mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        User commenter = new User();
                        commenter.setId_user(rs.getInt("commenter_id"));
                        commenter.setUsername(rs.getString("commenter_username"));
                        commenter.setUser_registration(rs.getString("commenter_user_registration"));

                        User creator = new User();
                        creator.setId_user(rs.getInt("creator_id"));
                        creator.setUsername(rs.getString("creator_username"));
                        creator.setUser_registration(rs.getString("creator_user_registration"));

                        Image image = new Image();
                        image.setId_image(rs.getInt("id_image"));
                        image.setUrl(rs.getString("url"));
                        image.setTitle(rs.getString("title"));
                        image.setImage_creation(rs.getString("image_creation"));
                        image.setImage_lastedit(rs.getString("image_lastedit"));
                        image.setImage_likes(rs.getInt("image_likes"));
                        image.setUser(creator);

                        Comment comment = new Comment();
                        comment.setId_comment(rs.getInt("id_comment"));
                        comment.setComment_creation(rs.getString("comment_creation"));
                        comment.setComment_lastedit(rs.getString("comment_lastedit"));
                        comment.setComment_likes(rs.getInt("comment_likes"));
                        comment.setText(rs.getString("text"));
                        comment.setUser(commenter);
                        comment.setImage(image);

                        return comment;
                    }

                });
    }

    public Comment getComment(String text) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("text", text);

        return jdbc.queryForObject("SELECT id_comment, comment.id_user as 'commenter_id', user2.username as 'commenter_username'," +
                        "user2.user_registration  as 'commenter_user_registration', comment.id_image," +
                        "comment_creation, comment_lastedit, comment_likes, text, url, title, image_creation," +
                        "image_lastedit, image_likes, image.id_user as 'creator_id'," +
                        "user.username as 'creator_username', user.user_registration as 'creator_user_registration'" +
                        " FROM comment, image INNER JOIN user ON (image.id_user=user.id_user)," +
                        "user AS user2 WHERE comment.id_user=user2.id_user and comment.id_image=image.id_image and text=:text", params,
                new RowMapper<Comment>() {

                    public Comment mapRow(ResultSet rs, int rowNum)
                            throws SQLException {
                        User commenter = new User();
                        commenter.setId_user(rs.getInt("commenter_id"));
                        commenter.setUsername(rs.getString("commenter_username"));
                        commenter.setUser_registration(rs.getString("commenter_user_registration"));

                        User creator = new User();
                        creator.setId_user(rs.getInt("creator_id"));
                        creator.setUsername(rs.getString("creator_username"));
                        creator.setUser_registration(rs.getString("creator_user_registration"));

                        Image image = new Image();
                        image.setId_image(rs.getInt("id_image"));
                        image.setUrl(rs.getString("url"));
                        image.setTitle(rs.getString("title"));
                        image.setImage_creation(rs.getString("image_creation"));
                        image.setImage_lastedit(rs.getString("image_lastedit"));
                        image.setImage_likes(rs.getInt("image_likes"));
                        image.setUser(creator);

                        Comment comment = new Comment();
                        comment.setId_comment(rs.getInt("id_comment"));
                        comment.setComment_creation(rs.getString("comment_creation"));
                        comment.setComment_lastedit(rs.getString("comment_lastedit"));
                        comment.setComment_likes(rs.getInt("comment_likes"));
                        comment.setText(rs.getString("text"));
                        comment.setUser(commenter);
                        comment.setImage(image);

                        return comment;
                    }

                });
    }



    public void deleteComments() {
        jdbc.getJdbcOperations().execute("DELETE FROM comment");
    }


}