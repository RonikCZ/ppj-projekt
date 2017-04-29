package cz.karelsir.projekt;

import cz.karelsir.projekt.data.User;
import cz.karelsir.projekt.data.UserDao;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
    private UserDao userDao;

    @Test
    public void testCreateUser() {

        User user = new User("testUser");

        assertNotEquals("Creation should affect more than 0 rows", 0, userDao.create(user));

        assertTrue("User should exist", userDao.exists(user.getUsername()));

        User created = userDao.getUser(user.getId());

        assertEquals("Returned user from the database should equal created user", created.getUsername(), user.getUsername());

        userDao.deleteUser(created.getId());
    }

}
