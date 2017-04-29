package cz.karelsir.projekt.data;

import java.time.LocalDateTime;

/**
 * Created by akasa on 20.1.2015.
 */
public class User {

    private Integer id;
    private String username;
    private LocalDateTime user_registration;

    public User() {

    }

    public User(int id, String username, LocalDateTime user_registration) {
        this.id = id;
        this.username = username;
        this.user_registration = user_registration;
    }

    public User(String username) {
        this.username = username;
        this.user_registration = LocalDateTime.now();
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

    public LocalDateTime getUser_registration() {
        return user_registration;
    }

    public void setUser_registration(LocalDateTime user_registration) {
        this.user_registration = user_registration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;
        return user_registration != null ? user_registration.equals(user.user_registration) : user.user_registration == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (user_registration != null ? user_registration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", user_registration='" + user_registration + '\'' +
                '}';
    }
}
