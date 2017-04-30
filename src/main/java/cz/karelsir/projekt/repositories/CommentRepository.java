package cz.karelsir.projekt.repositories;

import cz.karelsir.projekt.data.Comment;
import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ronik on 30. 4. 2017.
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    @Query("select c from Comment as c where c.text=:text")
    public List<Comment> findByText(@Param("text") String text);

    @Query("select c from Comment as c where c.image=:image")
    public List<Comment> findByImage(@Param("image") Image image);

    @Query("select c from Comment as c where c.user=:user")
    public List<Comment> findByUser(@Param("user") User user);

}
