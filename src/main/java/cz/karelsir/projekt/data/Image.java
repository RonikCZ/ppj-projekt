package cz.karelsir.projekt.data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @Column(name = "id_image")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_image;

    private String url;
    private String title;
    private String image_creation;
    private String image_lastedit;
    private Integer image_likes;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    public Image() {
        this.user = new User();
    }

    public Image(User user, String url, String title, String image_creation) {
        this.user = user;
        this.url = url;
        this.title = title;
        this.image_creation = image_creation;
        this.image_lastedit = image_creation;
        this.image_likes = 0;
    }

    public Image(int id_image, User user, String url, String title, String image_creation, String date_lastedit, Integer likes) {
        this.id_image = id_image;
        this.user = user;
        this.url = url;
        this.title = title;
        this.image_creation = image_creation;
        this.image_lastedit = date_lastedit;
        this.image_likes = likes;
    }

    public Integer getId_image() {
        return id_image;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_creation() {
        return image_creation;
    }

    public String getImage_lastedit() {
        return image_lastedit;
    }

    public Integer getImage_likes() {
        return image_likes;
    }

    public User getUser() {
        return user;
    }

    public void setId_image(Integer id_image) {
        this.id_image = id_image;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage_creation(String image_creation) {
        this.image_creation = image_creation;
    }

    public void setImage_lastedit(String image_lastedit) {
        this.image_lastedit = image_lastedit;
    }

    public void setImage_likes(Integer image_likes) {
        this.image_likes = image_likes;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((image_creation == null) ? 0 : image_creation.hashCode());
        result = prime * result + ((image_lastedit == null) ? 0 : image_lastedit.hashCode());
        result = prime * result + ((image_likes == null) ? 0 : image_likes.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());

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
        Image other = (Image) obj;
        if (id_image == null) {
            if (other.id_image != null)
                return false;
        } else if (!id_image.equals(other.id_image))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Image [id_image=" + id_image + ", user=" + user + ", url=" + url + ", title=" + title +
                ", image_creation=" + image_creation + ", image_lastedit=" + image_lastedit + ", image_likes=" + image_likes + "]";
    }



}
