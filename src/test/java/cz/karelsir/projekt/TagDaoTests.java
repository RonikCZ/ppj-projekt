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

        User imageCreator = new User("imageCreator");
        userDao.create(imageCreator);
        imageCreator = userDao.getUser(imageCreator.getUsername());

        Image image = new Image(imageCreator,"http://tag", "tagTest");
        imageDao.create(image);
        image = imageDao.getImage(image.getUrl());

        Tag tag = new Tag(image, "test");
        tagDao.create(tag);

        Tag created = tagDao.getTag(image.getId(), tag.getTag_title());
        assertEquals("Returned tag from the database", tag.getTag_title(), created.getTag_title());

        tagDao.delete(tag);
        imageDao.deleteImage(image.getId());
        userDao.deleteUser(imageCreator.getId());
    }
}


