package cz.karelsir.projekt.repositories;

import cz.karelsir.projekt.data.Comment;
import cz.karelsir.projekt.data.Image;
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

    @Query("select c from Comment as c where c.id_image=:id_image")
    public List<Comment> findByImage(@Param("id_image") int id_image);

    @Query("select c from Comment as c where c.id_user=:id_user")
    public List<Comment> findByUser(@Param("id_user") int id_user);

}
