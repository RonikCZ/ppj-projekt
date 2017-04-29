package cz.karelsir.projekt.data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Ronik on 7. 4. 2017.
 */

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "id_comment")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment_creation")
    private LocalDateTime dateCreation;
    @Column(name = "comment_lastedit")
    private LocalDateTime dateLastEdit;
    @Column(name = "comment_likes")
    private Integer likes;
    private String text;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_image")
    private Image image;


    public Comment() {
        this.user = new User();
        this.image = new Image();
    }

    public Comment(User user, Image image, String text) {
        this.user = user;
        this.image = image;
        this.dateCreation = LocalDateTime.now();
        this.dateLastEdit = this.dateCreation;
        this.likes = 0;
        this.text = text;
    }

    public Comment(Integer id, User user, Image image, LocalDateTime date_creation, LocalDateTime date_lastedit, Integer likes, String text) {
        this.id = id;
        this.user = user;
        this.image = image;
        this.dateCreation = dateCreation;
        this.dateLastEdit = dateLastEdit;
        this.likes = likes;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (dateCreation != null ? !dateCreation.equals(comment.dateCreation) : comment.dateCreation != null)
            return false;
        if (dateLastEdit != null ? !dateLastEdit.equals(comment.dateLastEdit) : comment.dateLastEdit != null)
            return false;
        if (likes != null ? !likes.equals(comment.likes) : comment.likes != null) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (user != null ? !user.equals(comment.user) : comment.user != null) return false;
        return image != null ? image.equals(comment.image) : comment.image == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dateCreation != null ? dateCreation.hashCode() : 0);
        result = 31 * result + (dateLastEdit != null ? dateLastEdit.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", dateCreation=" + dateCreation +
                ", dateLastEdit=" + dateLastEdit +
                ", likes=" + likes +
                ", text='" + text + '\'' +
                ", user=" + user +
                ", image=" + image +
                '}';
    }
}