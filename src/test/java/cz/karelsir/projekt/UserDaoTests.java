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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ronik on 8. 4. 2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDaoTests {

    @Autowired
    UserService userService;

    @Test
    public void testCreateUser() {

        User user = new User("testUser");

        userService.create(user);

        assertTrue("User should exist", userService.exists(user.getId()));

        User created = userService.getUser(user.getId());

        assertEquals("Return user from the database", created.getUsername(), user.getUsername());

        userService.deleteUser(created.getId());
    }

}
