package cz.karelsir.projekt.services;

import cz.karelsir.projekt.data.User;
import cz.karelsir.projekt.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Ronik on 30. 4. 2017.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void create(User user) {
        userRepository.save(user);
    }

    public boolean exists(Integer id) {
        return userRepository.exists(id);
    }

    public User getUser(int id) {
        return userRepository.findOne(id);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).get(0);
    }

    public List<User> getAllUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void deleteUser(int id) {
        userRepository.delete(id);
    }

    public void deleteUsers() {
        userRepository.deleteAll();
    }
}
