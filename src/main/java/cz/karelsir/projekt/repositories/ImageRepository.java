package cz.karelsir.projekt.repositories;

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
public interface ImageRepository extends CrudRepository<Image, Integer> {
    public List<Image> findByUrl(@Param("url") String url);

    public List<Image> findByUser(@Param("user") User user);

    @Query("select max(i.id) from Image as i")
    public Integer findTop1ById();
}
