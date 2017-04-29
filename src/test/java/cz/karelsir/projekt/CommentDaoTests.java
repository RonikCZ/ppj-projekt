package cz.karelsir.projekt;

import cz.karelsir.projekt.data.*;
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
import static org.junit.Assert.assertTrue;

/**
 * Created by Ronik on 8. 4. 2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {Main.class})
@ActiveProfiles({"test"})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommentDaoTests {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    @Autowired
    private ImageDao imageDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private CommentDao commentDao;

    @Test
    public void testCreateComment() {

        User imageCreator = new User("imageCreator");
        userDao.create(imageCreator);

        User commenter = new User("commenter");
        userDao.create(commenter);

        Image image = new Image(imageCreator,"http://test", "titulek");
        imageDao.create(image);
        image = imageDao.getImage(image.getUrl());

        Comment comment = new Comment(commenter, image, "2008-01-01 00:00:01", "2008-01-01 00:00:01", "test");
        commentDao.create(comment);

        Comment created = commentDao.getComment(comment.getText());
        log.info(created.toString());
        assertEquals("Returned comment from the database", created.getText(), comment.getText());

        commentDao.deleteComment(created.getId_comment());
        imageDao.deleteImage(image.getId());
        userDao.deleteUser(commenter.getId());
        userDao.deleteUser(imageCreator.getId());
    }

    @Test
    public void testLikeDislikeComment() {

        User imageCreator = new User("imageCreator");
        userDao.create(imageCreator);

        User commenter = new User("commenter");
        userDao.create(commenter);

        Image image = new Image(imageCreator,"http://test", "titulek");
        imageDao.create(image);
        image = imageDao.getImage(image.getUrl());

        Comment comment = new Comment(commenter, image, "2008-01-01 00:00:01", "2008-01-01 00:00:01", "test");
        commentDao.create(comment);
        comment = commentDao.getComment(comment.getText());

        assertEquals("Likes at 0", (int)comment.getComment_likes(), 0);
        commentDao.changeLikes(comment, true);
        assertEquals("Likes at 1", (int)comment.getComment_likes(), 1);
        commentDao.changeLikes(comment, false);
        assertEquals("Likes at 0 again", (int)comment.getComment_likes(), 0);

        commentDao.deleteComment(comment.getId_comment());
        imageDao.deleteImage(image.getId());
        userDao.deleteUser(commenter.getId());
        userDao.deleteUser(imageCreator.getId());
    }

}
