package cz.karelsir.projekt.data;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Created by akasa on 20.1.2015.
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id_user")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String username;

    @Column(name = "user_registration")
    private LocalDateTime registration;

    public User() {

    }

    public User(int id, String username, LocalDateTime registration) {
        this.id = id;
        this.username = username;
        this.registration = registration;
    }

    public User(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getRegistration() {
        return registration;
    }

    public void setRegistration(LocalDateTime registration) {
        this.registration = registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        return registration != null ? registration.equals(user.registration) : user.registration == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (registration != null ? registration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", registration=" + registration +
                '}';
    }

    @PrePersist
    public void prePersist(){
        setRegistration(LocalDateTime.now());
    }
}
