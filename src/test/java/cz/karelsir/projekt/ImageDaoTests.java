package cz.karelsir.projekt;

import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.ImageDao;
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
public class ImageDaoTests {

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;

    @Test
    public void testCreateImage() {

        User user = new User("imageCreator");
        userDao.create(user);

        Image image = new Image(user.getId(),"http://test", "titulek", "2008-01-01 00:00:01");
        assertNotEquals("Creation should affect more than 0 rows", 0, imageDao.create(image));

        Image created = imageDao.getImage(image.getId());

        assertEquals("Returned image from the database", created.getUrl(), image.getUrl());

        imageDao.deleteImage(created.getId());
        userDao.deleteUser(user.getId());
    }

    @Test
    public void testLikeDislike() {

        User creator = new User("imageCreator");
        userDao.create(creator);

        Image image = new Image(creator.getId(),"http://liketest", "like", "2008-01-01 00:00:01");
        imageDao.create(image);

        assertEquals("Likes at 0", (int)image.getImage_likes(), 0);
        imageDao.changeLikes(image, true);
        assertEquals("Likes at 1", (int)image.getImage_likes(), 1);
        imageDao.changeLikes(image, false);
        assertEquals("Likes at 0 again", (int)image.getImage_likes(), 0);

        imageDao.deleteImage(image.getId());
        userDao.deleteUser(creator.getId());
    }

    @Test
    public void testChangeImage() {

        User creator = new User("imageCreator");
        userDao.create(creator);

        Image image = new Image(creator.getId(),"http://liketest", "original", "2008-01-01 00:00:01");
        imageDao.create(image);
        image = imageDao.getImage(image.getUrl());

        Image original = new Image(image.getId(), image.getId_user(), image.getUrl(), image.getTitle(),
                image.getImage_creation(), image.getImage_creation(), image.getImage_likes());
        image.setTitle("zmena");
        imageDao.update(image);
        image = imageDao.getImage(image.getId());

        assertNotEquals("Check if changed", image.getTitle(), original.getTitle());

        imageDao.deleteImage(image.getId());
        userDao.deleteUser(creator.getId());
    }



}
