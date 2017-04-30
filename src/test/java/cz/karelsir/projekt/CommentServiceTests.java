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

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

        assertTrue("Comment should exist", commentService.exists(comment.getId()));

        Comment created = commentService.getComment(comment.getId());
        assertEquals("Returned comment from the database", created.getText(), comment.getText());

        commentService.deleteComment(created.getId());
        imageService.deleteImage(image.getId());
        userService.deleteUser(commenter.getId());
        userService.deleteUser(imageCreator.getId());
    }

    @Test
    public void testCreateComments() {
        User creator = new User("imageCreator");
        userService.create(creator);

        Image image = new Image(creator,"http://first", "original");
        imageService.create(image);

        Comment comment = new Comment(creator, image, "test");
        Comment comment2 = new Comment(creator, image, "test");
        Comment comment3 = new Comment(creator, image, "test");

        commentService.create(comment);

        List<Comment> list = Arrays.asList(comment, comment2, comment3);
        commentService.create(list);

        List<Comment> allComments = commentService.getAllComments();
        assertEquals("Should return 3 comments", 3, allComments.size());
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

    @Test
    public void testGetCommentsByImage() {

        User imageCreator = new User("imageCreator");
        userService.create(imageCreator);
        User commenter = new User("commenter");
        userService.create(commenter);

        Image image = new Image(imageCreator,"http://first", "original");
        imageService.create(image);
        Image image2 = new Image(imageCreator,"http://second", "original");
        imageService.create(image2);

        Comment first = new Comment(commenter, image, "first");
        commentService.create(first);
        Comment second = new Comment(commenter, image2, "second");
        commentService.create(second);
        Comment third = new Comment(commenter, image2, "third");
        commentService.create(third);

        List<Comment> allComments = commentService.getAllComments();
        List<Comment> firstImageComments = commentService.getCommentsByImage(image);
        List<Comment> secondImageComments = commentService.getCommentsByImage(image2);

        assertEquals("Should return 3 comments", 3, allComments.size());
        assertEquals("Should return 1 comment", 1, firstImageComments.size());
        assertEquals("Should return 2 comments", 2, secondImageComments.size());
    }

    @Test
    public void testGetCommentsByUser() {

        User imageCreator = new User("imageCreator");
        userService.create(imageCreator);
        User commenter1 = new User("commenter1");
        userService.create(commenter1);
        User commenter2 = new User("commenter2");
        userService.create(commenter2);

        Image image = new Image(imageCreator,"http://first", "original");
        imageService.create(image);

        Comment first = new Comment(commenter1, image, "first");
        commentService.create(first);
        Comment second = new Comment(commenter2, image, "second");
        commentService.create(second);
        Comment third = new Comment(commenter2, image, "third");
        commentService.create(third);

        List<Comment> allComments = commentService.getAllComments();
        List<Comment> firstUserComments = commentService.getCommentsByUser(commenter1);
        List<Comment> secondUserComments = commentService.getCommentsByUser(commenter2);

        assertEquals("Should return 3 comments", 3, allComments.size());
        assertEquals("Should return 1 comment", 1, firstUserComments.size());
        assertEquals("Should return 2 comments", 2, secondUserComments.size());
    }

    @Test
    public void testDeleteComments() {
        User creator = new User("imageCreator");
        userService.create(creator);

        Image image = new Image(creator,"http://first", "original");
        imageService.create(image);

        Comment comment = new Comment(creator, image, "test");
        Comment comment2 = new Comment(creator, image, "test");
        Comment comment3 = new Comment(creator, image, "test");

        List<Comment> created = Arrays.asList(comment, comment2, comment3);
        commentService.create(created);
        commentService.deleteComments();
        List<Comment> list = commentService.getAllComments();

        assertTrue("No comments should exist", list.isEmpty());
    }


}
