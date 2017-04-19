package cz.karelsir.projekt;

import cz.karelsir.projekt.data.*;
import cz.karelsir.projekt.provisioning.Provisioner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.List;

@SpringBootApplication
public class Main {

    @Bean
    public ImageDao offersDao() {
        return new ImageDao();
    }

    @Bean
    public UserDao userDao() {
        return new UserDao();
    }

    @Bean
    public CommentDao commentDao() {
        return new CommentDao();
    }

    @Bean
    public TagDao tagDao() {
        return new TagDao();
    }


    //   @Profile({"devel", "test"})
    @Bean(initMethod = "doProvision")
    public Provisioner provisioner() {
        return new Provisioner();
    }

    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        UserDao userDao = ctx.getBean(UserDao.class);
        List<User> users = userDao.getAllUsers();
        System.out.println(users);

        ImageDao imageDao = ctx.getBean(ImageDao.class);
        List<Image> images = imageDao.getImages();
        System.out.println(images);

        CommentDao commentDao = ctx.getBean(CommentDao.class);
        List<Comment> comments = commentDao.getComments();
        System.out.println(comments);

        TagDao tagDao = ctx.getBean(TagDao.class);
        List<Tag> tags = tagDao.getTags();
        System.out.println(tags);

    }

}