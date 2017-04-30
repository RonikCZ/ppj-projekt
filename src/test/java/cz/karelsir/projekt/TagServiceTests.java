package cz.karelsir.projekt;

import cz.karelsir.projekt.data.*;
import cz.karelsir.projekt.services.ImageService;
import cz.karelsir.projekt.services.TagService;
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
 * Created by Ronik on 19. 4. 2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagServiceTests {
    @Autowired
    ImageService imageService;
    @Autowired
    UserService userService;
    @Autowired
    TagService tagService;

    @Test
    public void testCreateTag() {

        User imageCreator = new User("imageCreator");
        userService.create(imageCreator);

        Image image = new Image(imageCreator,"http://tag", "tagTest");
        imageService.create(image);

        Tag tag = new Tag(image, "test");
        tagService.create(tag);

        Tag created = tagService.getTag(image, tag.getTitle());
        assertEquals("Returned tag from the database", tag.getTitle(), created.getTitle());

        tagService.delete(tag);
        imageService.deleteImage(image.getId());
        userService.deleteUser(imageCreator.getId());
    }

    @Test
    public void testGetTags() {
        User imageCreator = new User("imageCreator");
        userService.create(imageCreator);

        Image image = new Image(imageCreator,"http://tag", "tagTest");
        imageService.create(image);

        Image image2 = new Image(imageCreator,"http://tag", "tagTest");
        imageService.create(image2);

        Tag tag = new Tag(image, "firstImage");
        tagService.create(tag);
        Tag tag2 = new Tag(image2, "secondImageFirstTag");
        tagService.create(tag2);
        Tag tag3 = new Tag(image2, "secondImageSecondTag");
        tagService.create(tag3);

        List<Tag> allTags = tagService.getAllTags();
        List<Tag> firstImageTags = tagService.getTagsByImage(image);
        List<Tag> secondImageTags = tagService.getTagsByImage(image2);

        assertEquals("Should return 3 tags", 3, allTags.size());
        assertEquals("Should return 1 tags", 1, firstImageTags.size());
        assertEquals("Should return 2 tags", 2, secondImageTags.size());
    }

    @Test
    public void testDeleteTags() {
        User imageCreator = new User("imageCreator");
        userService.create(imageCreator);

        Image image = new Image(imageCreator,"http://tag", "tagTest");
        imageService.create(image);

        Image image2 = new Image(imageCreator,"http://tag", "tagTest");
        imageService.create(image2);

        Tag tag = new Tag(image, "firstImage");
        tagService.create(tag);
        Tag tag2 = new Tag(image2, "secondImageFirstTag");
        tagService.create(tag2);
        Tag tag3 = new Tag(image2, "secondImageSecondTag");
        tagService.create(tag3);

        tagService.deleteTags();

        List<Tag> list = tagService.getAllTags();
        assertTrue("No tags should exist", list.isEmpty());
    }

}


