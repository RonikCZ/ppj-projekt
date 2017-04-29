package cz.karelsir.projekt.data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @Column(name = "id_image")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private String url;
    private String title;
    @Column(name = "image_creation")
    private LocalDateTime dateCreation;
    @Column(name = "image_lastedit")
    private LocalDateTime dateLastEdit;
    @Column(name = "image_likes")
    private Integer likes;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Image() {
        this.user = new User();
    }

    public Image(User user, String url, String title) {
        this.user = user;
        this.url = url;
        this.title = title;
        this.dateCreation = LocalDateTime.now();
        this.dateLastEdit = this.dateCreation;
        this.likes = 0;
    }

    public Image(int id, User user, String url, String title, LocalDateTime dateCreation, LocalDateTime dateLastEdit, Integer likes) {
        this.id = id;
        this.user = user;
        this.url = url;
        this.title = title;
        this.dateCreation = dateCreation;
        this.dateLastEdit = dateLastEdit;
        this.likes = likes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateLastEdit() {
        return dateLastEdit;
    }

    public void setDateLastEdit(LocalDateTime dateLastEdit) {
        this.dateLastEdit = dateLastEdit;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (id != null ? !id.equals(image.id) : image.id != null) return false;
        if (url != null ? !url.equals(image.url) : image.url != null) return false;
        if (title != null ? !title.equals(image.title) : image.title != null) return false;
        if (dateCreation != null ? !dateCreation.equals(image.dateCreation) : image.dateCreation != null) return false;
        if (dateLastEdit != null ? !dateLastEdit.equals(image.dateLastEdit) : image.dateLastEdit != null) return false;
        if (likes != null ? !likes.equals(image.likes) : image.likes != null) return false;
        return user != null ? user.equals(image.user) : image.user == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (dateCreation != null ? dateCreation.hashCode() : 0);
        result = 31 * result + (dateLastEdit != null ? dateLastEdit.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", dateCreation=" + dateCreation +
                ", dateLastEdit=" + dateLastEdit +
                ", likes=" + likes +
                ", user=" + user +
                '}';
    }
}
