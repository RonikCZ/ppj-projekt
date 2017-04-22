package cz.karelsir.projekt;

import cz.karelsir.projekt.data.*;
import cz.karelsir.projekt.provisioning.Provisioner;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan("cz.karelsir.projekt.data")
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

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Bean
    public SessionFactory sessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new HibernateTransactionManager(entityManagerFactory.unwrap(SessionFactory.class));
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