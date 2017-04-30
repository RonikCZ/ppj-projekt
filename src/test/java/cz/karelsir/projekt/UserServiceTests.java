package cz.karelsir.projekt;

import cz.karelsir.projekt.data.User;
import cz.karelsir.projekt.services.UserService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ronik on 8. 4. 2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTests {

    @Autowired
    UserService userService;

    @Test
    public void testCreateUser() {

        User user = new User("testUser");

        userService.create(user);

        int id = user.getId();

        assertTrue("User should exist", userService.exists(id));

        User created = userService.getUser(id);

        assertEquals("Check if the returned user is the same as created", created.getUsername(), user.getUsername());

        userService.deleteUser(created.getId());
        assertTrue("User should be deleted", !userService.exists(id));
    }

    @Test
    public void testDeleteUsers() {

        User user = new User("testUser");
        User user2 = new User("testUser2");
        User user3 = new User("testUser3");

        userService.create(user);
        userService.create(user2);
        userService.create(user3);

        userService.deleteUsers();

        List<User> list = userService.getAllUsers();

        assertTrue("No users should exist", list.isEmpty());
    }

}
