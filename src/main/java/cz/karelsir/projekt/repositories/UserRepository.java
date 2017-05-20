package cz.karelsir.projekt.repositories;

import cz.karelsir.projekt.data.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Ronik on 30. 4. 2017.
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    public List<User> findByUsername(@Param("username") String username);
}