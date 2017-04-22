package cz.karelsir.projekt.data;

import javax.persistence.*;

/**
 * Created by Ronik on 7. 4. 2017.
 */

@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @Column(name = "id_comment")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id_comment;

    private String comment_creation;
    private String comment_lastedit;
    private Integer comment_likes;
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

    public Comment(User user, Image image, String date_creation, String date_lastedit, String text) {
        this.user = user;
        this.image = image;
        this.comment_creation = date_creation;
        this.comment_lastedit = date_lastedit;
        this.comment_likes = 0;
        this.text = text;
    }

    public Comment(Integer id_comment, User user, Image image, String date_creation, String date_lastedit, Integer likes, String text) {
        this.id_comment = id_comment;
        this.user = user;
        this.image = image;
        this.comment_creation = date_creation;
        this.comment_lastedit = date_lastedit;
        this.comment_likes = likes;
        this.text = text;
    }

    public Integer getId_comment() {
        return id_comment;
    }

    public void setId_comment(Integer id_comment) {
        this.id_comment = id_comment;
    }

    public String getComment_creation() {
        return comment_creation;
    }

    public void setComment_creation(String comment_creation) {
        this.comment_creation = comment_creation;
    }

    public String getComment_lastedit() {
        return comment_lastedit;
    }

    public void setComment_lastedit(String comment_lastedit) {
        this.comment_lastedit = comment_lastedit;
    }

    public Integer getComment_likes() {
        return comment_likes;
    }

    public void setComment_likes(Integer comment_likes) {
        this.comment_likes = comment_likes;
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((text == null) ? 0 : text.hashCode());
        result = prime * result + ((comment_creation == null) ? 0 : comment_creation.hashCode());
        result = prime * result + ((comment_lastedit == null) ? 0 : comment_lastedit.hashCode());
        result = prime * result + ((comment_likes == null) ? 0 : comment_likes.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        result = prime * result + ((image == null) ? 0 : image.hashCode());

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
        Comment other = (Comment) obj;
        if (id_comment == null) {
            if (other.id_comment != null)
                return false;
        } else if (!id_comment.equals(other.id_comment))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Comment [id_comment=" + id_comment + ", user=" + user + ", image=" + image +
                ", comment_creation=" + comment_creation + ", comment_lastedit=" + comment_lastedit + ", comment_likes=" + comment_likes + ", text=" + text + "]";
    }

}