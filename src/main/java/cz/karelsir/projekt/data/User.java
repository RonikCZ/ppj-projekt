package cz.karelsir.projekt.data;

/**
 * Created by akasa on 20.1.2015.
 */
public class User {

    private Integer id_user;
    private String username;
    private String user_registration;

    public User() {

    }

    public User(int id_user, String username, String user_registration) {
        this.id_user = id_user;
        this.username = username;
        this.user_registration = user_registration;
    }

    public User(String username, String user_registration) {
        this.username = username;
        this.user_registration = user_registration;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUser_registration() {
        return user_registration;
    }

    public void setUser_registration(String user_registration) {
        this.user_registration = user_registration;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        result = prime * result + ((user_registration == null) ? 0 : user_registration.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id_user == null) {
            if (other.id_user != null)
                return false;
        } else if (!id_user.equals(other.id_user))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "User [ id_user=" + id_user +", username=" + username + ", user_registration=" + user_registration + "]";
    }


}
