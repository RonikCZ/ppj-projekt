package cz.karelsir.projekt;

import cz.karelsir.projekt.data.*;
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
 * Created by Ronik on 19. 4. 2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagDaoTests {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private TagDao tagDao;

    @Test
    public void testCreateTag() {

        User imageCreator = new User("imageCreator", "2008-01-01 00:00:01");
        userDao.create(imageCreator);
        imageCreator = userDao.getUser(imageCreator.getUsername());

        Image image = new Image(imageCreator,"http://tag", "tagTest", "2008-01-01 00:00:01");
        imageDao.create(image);
        image = imageDao.getImage(image.getUrl());

        Tag tag = new Tag(image, "test");
        assertTrue("Tag creation should return true", tagDao.create(tag));

        Tag created = tagDao.getTag(image.getId_image(), tag.getTag_title());
        assertEquals("Returned tag from the database", tag.getTag_title(), created.getTag_title());

        tagDao.delete(tag);
        imageDao.deleteImage(image.getId_image());
        userDao.deleteUser(imageCreator.getId_user());
    }
}


