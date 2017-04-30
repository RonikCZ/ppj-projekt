package cz.karelsir.projekt;

import cz.karelsir.projekt.data.*;
import cz.karelsir.projekt.services.CommentService;
import cz.karelsir.projekt.services.ImageService;
import cz.karelsir.projekt.services.UserService;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ronik on 8. 4. 2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentServiceTests {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Autowired
    ImageService imageService;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;

    @Test
    public void testCreateComment() {

        User imageCreator = new User("imageCreator");
        userService.create(imageCreator);

        User commenter = new User("commenter");
        userService.create(commenter);

        Image image = new Image(imageCreator,"http://test", "titulek");
        imageService.create(image);

        Comment comment = new Comment(commenter, image,"test");
        commentService.create(comment);

        Comment created = commentService.getComment(comment.getId());
        assertEquals("Returned comment from the database", created.getText(), comment.getText());

        commentService.deleteComment(created.getId());
        imageService.deleteImage(image.getId());
        userService.deleteUser(commenter.getId());
        userService.deleteUser(imageCreator.getId());
    }

    @Test
    public void testLikeDislikeComment() {

        User imageCreator = new User("imageCreator");
        userService.create(imageCreator);

        User commenter = new User("commenter");
        userService.create(commenter);

        Image image = new Image(imageCreator,"http://test", "titulek");
        imageService.create(image);

        Comment comment = new Comment(commenter, image, "test");
        commentService.create(comment);

        assertEquals("Likes at 0", (int)comment.getLikes(), 0);
        commentService.changeLikes(comment, true);
        assertEquals("Likes at 1", (int)comment.getLikes(), 1);
        commentService.changeLikes(comment, false);
        assertEquals("Likes at 0 again", (int)comment.getLikes(), 0);

        commentService.deleteComment(comment.getId());
        imageService.deleteImage(image.getId());
        userService.deleteUser(commenter.getId());
        userService.deleteUser(imageCreator.getId());
    }

}
