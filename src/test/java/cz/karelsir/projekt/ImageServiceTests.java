package cz.karelsir.projekt;

import cz.karelsir.projekt.data.Image;
import cz.karelsir.projekt.data.User;
import cz.karelsir.projekt.services.ImageService;
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
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Ronik on 8. 4. 2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ImageServiceTests {

    @Autowired
    ImageService imageService;
    @Autowired
    UserService userService;

    @Test
    public void testCreateImage() {

        User user = new User("imageCreator");
        userService.create(user);

        Image image = new Image(user,"http://test", "titulek");
        imageService.create(image);

        Image created = imageService.getImage(image.getId());

        assertEquals("Returned image from the database", created.getUrl(), image.getUrl());

        imageService.deleteImage(created.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    public void testLikeDislike() {

        User creator = new User("imageCreator");
        userService.create(creator);

        Image image = new Image(creator,"http://liketest", "like");
        imageService.create(image);

        assertEquals("Likes at 0", (int)image.getLikes(), 0);
        imageService.changeLikes(image, true);
        assertEquals("Likes at 1", (int)image.getLikes(), 1);
        imageService.changeLikes(image, false);
        assertEquals("Likes at 0 again", (int)image.getLikes(), 0);

        imageService.deleteImage(image.getId());
        userService.deleteUser(creator.getId());
    }

    @Test
    public void testChangeImage() {

        User creator = new User("imageCreator");
        userService.create(creator);

        Image image = new Image(creator,"http://liketest", "original");
        imageService.create(image);

        Image original = new Image(image.getId(), image.getUser(), image.getUrl(), image.getTitle(),
                image.getDateCreation(), image.getDateCreation(), image.getLikes());
        image.setTitle("zmena");
        imageService.update(image);
        image = imageService.getImage(image.getId());

        assertNotEquals("Check if changed", image.getTitle(), original.getTitle());

        imageService.deleteImage(image.getId());
        userService.deleteUser(creator.getId());
    }



}
