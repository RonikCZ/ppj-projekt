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

        User user = new User("imageCreator", "2008-01-01 00:00:01");
        userDao.create(user);
        user = userDao.getUser(user.getUsername());

        Image image = new Image(user,"http://test", "titulek", "2008-01-01 00:00:01");
        assertTrue("Image creation should return true", imageDao.create(image));

        Image created = imageDao.getImage(image.getUrl());

        assertEquals("Returned image from the database", created.getUrl(), image.getUrl());

        imageDao.deleteImage(created.getId_image());
        userDao.deleteUser(user.getId_user());
    }

    @Test
    public void testLikeDislike() {

        User creator = new User("imageCreator", "2008-01-01 00:00:01");
        userDao.create(creator);
        creator = userDao.getUser(creator.getUsername());

        Image image = new Image(creator,"http://liketest", "like", "2008-01-01 00:00:01");
        imageDao.create(image);
        image = imageDao.getImage(image.getUrl());

        assertEquals("Likes at 0", (int)image.getImage_likes(), 0);
        imageDao.changeLikes(image, true);
        assertEquals("Likes at 1", (int)image.getImage_likes(), 1);
        imageDao.changeLikes(image, false);
        assertEquals("Likes at 0 again", (int)image.getImage_likes(), 0);

        imageDao.deleteImage(image.getId_image());
        userDao.deleteUser(creator.getId_user());
    }

    @Test
    public void testChangeImage() {

        User creator = new User("imageCreator", "2008-01-01 00:00:01");
        userDao.create(creator);
        creator = userDao.getUser(creator.getUsername());

        Image image = new Image(creator,"http://liketest", "original", "2008-01-01 00:00:01");
        imageDao.create(image);
        image = imageDao.getImage(image.getUrl());

        Image original = new Image(image.getId_image(), image.getUser(), image.getUrl(), image.getTitle(),
                image.getImage_creation(), image.getImage_creation(), image.getImage_likes());
        image.setTitle("zmena");
        imageDao.update(image);
        image = imageDao.getImage(image.getId_image());

        assertNotEquals("Check if changed", image.getTitle(), original.getTitle());

        imageDao.deleteImage(image.getId_image());
        userDao.deleteUser(creator.getId_user());
    }



}
