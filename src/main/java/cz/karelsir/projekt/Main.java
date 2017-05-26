package cz.karelsir.projekt;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;

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
        SpringApplication.run(Main.class, args);
    }

}