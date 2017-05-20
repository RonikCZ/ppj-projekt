package cz.karelsir.projekt.repositories;

import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.Tag;
import cz.karelsir.projekt.data.TagId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ronik on 30. 4. 2017.
 */
@Repository
public interface TagRepository extends CrudRepository<Tag, TagId> {
    public List<Tag> findByTagIdImage(@Param("image") Image image);
}
