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
    @Query("select i from Image as i where i.url=:url")
    public List<Image> findByUrl(@Param("url") String url);
}
