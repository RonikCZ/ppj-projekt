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

import java.util.Arrays;
import java.util.List;

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

        assertTrue("Image should exist", imageService.exists(image.getId()));

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

    @Test
    public void testCreateImages() {
        User creator = new User("imageCreator");
        userService.create(creator);

        Image image = new Image(creator,"http://first", "original");
        Image image2 = new Image(creator,"http://second", "original");
        Image image3 = new Image(creator,"http://third", "original");

        List<Image> list = Arrays.asList(image, image2, image3);
        imageService.create(list);

        List<Image> allImages = imageService.getAllImages();
        assertEquals("Should return 3 images", 3, allImages.size());
    }

    @Test
    public void testGetImages() {

        User first = new User("imageCreator");
        userService.create(first);

        User second = new User("imageCreator2");
        userService.create(second);

        Image image = new Image(first,"http://firstUser", "original");
        imageService.create(image);

        Image image2 = new Image(second,"http://secondUserFirstImage", "original");
        imageService.create(image2);

        Image image3 = new Image(second,"http://secondUserSecondImage", "original");
        imageService.create(image3);

        List<Image> allImages = imageService.getAllImages();
        List<Image> firstUserImages = imageService.getUserImages(first);
        List<Image> secondUserImages = imageService.getUserImages(second);

        assertEquals("Should return 3 images", 3, allImages.size());
        assertEquals("Should return 1 image", 1, firstUserImages.size());
        assertEquals("Should return 2 images", 2, secondUserImages.size());
    }

    @Test
    public void testDeleteImages() {

        User first = new User("imageCreator");
        userService.create(first);

        User second = new User("imageCreator");
        userService.create(second);

        Image image = new Image(first,"http://firstUser", "original");
        imageService.create(image);

        Image image2 = new Image(second,"http://secondUserFirstImage", "original");
        imageService.create(image2);

        Image image3 = new Image(second,"http://secondUserSecondImage", "original");
        imageService.create(image3);

        imageService.deleteImages();

        List<Image> list = imageService.getAllImages();

        assertTrue("No images should exist", list.isEmpty());
    }


}
