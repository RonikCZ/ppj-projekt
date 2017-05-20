package cz.karelsir.projekt.repositories;

import cz.karelsir.projekt.data.Comment;
import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ronik on 30. 4. 2017.
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    public List<Comment> findByText(@Param("text") String text);

    public List<Comment> findByImage(@Param("image") Image image);

    public List<Comment> findByUser(@Param("user") User user);
}
