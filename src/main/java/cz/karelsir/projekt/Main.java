package cz.karelsir.projekt;

import cz.karelsir.projekt.data.*;
import cz.karelsir.projekt.provisioning.Provisioner;
import cz.karelsir.projekt.services.ImageService;
import cz.karelsir.projekt.services.UserService;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@EntityScan({"cz.karelsir.projekt.data","cz.karelsir.projekt.tools"})
@ComponentScan("cz.karelsir.projekt.services")
@EnableJpaRepositories(basePackages = "cz.karelsir.projekt.repositories")
public class Main {


    @Autowired
    EntityManagerFactory entityManagerFactory;

    @Bean
    public SessionFactory sessionFactory() {
        return entityManagerFactory.unwrap(SessionFactory.class);
    }


    //   @Profile({"devel", "test"})
    public static void main(String[] args) throws Exception {

        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext ctx = app.run(args);

        UserService userService = ctx.getBean(UserService.class);
        List<User> users = userService.getAllUsers();
        System.out.println(users);

        ImageService imageService = ctx.getBean(ImageService.class);
        List<Image> images = imageService.getAllImages();
        System.out.println(images);

    }

}