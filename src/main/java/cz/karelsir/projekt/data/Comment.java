package cz.karelsir.projekt.data;

/**
 * Created by Ronik on 7. 4. 2017.
 */
public class Comment {

    private Integer id;
    private Integer id_user;
    private Integer id_image;


    private String comment_creation;
    private String comment_lastedit;
    private Integer comment_likes;
    private String text;



    public Comment() {

    }

    public Comment(int id_user, int id_image, String date_creation, String date_lastedit, String text) {
        this.id_user = id_user;
        this.id_image = id_image;
        this.comment_creation = date_creation;
        this.comment_lastedit = date_lastedit;
        this.comment_likes = 0;
        this.text = text;
    }

    public Comment(Integer id, int id_user, int id_image, String date_creation, String date_lastedit, Integer likes, String text) {
        this.id = id;
        this.id_user = id_user;
        this.id_image = id_image;
        this.comment_creation = date_creation;
        this.comment_lastedit = date_lastedit;
        this.comment_likes = likes;
        this.text = text;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getId_image() {
        return id_image;
    }

    public void setId_image(Integer id_image) {
        this.id_image = id_image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (id != null ? !id.equals(comment.id) : comment.id != null) return false;
        if (id_user != null ? !id_user.equals(comment.id_user) : comment.id_user != null) return false;
        if (id_image != null ? !id_image.equals(comment.id_image) : comment.id_image != null) return false;
        if (comment_creation != null ? !comment_creation.equals(comment.comment_creation) : comment.comment_creation != null)
            return false;
        if (comment_lastedit != null ? !comment_lastedit.equals(comment.comment_lastedit) : comment.comment_lastedit != null)
            return false;
        if (comment_likes != null ? !comment_likes.equals(comment.comment_likes) : comment.comment_likes != null)
            return false;
        return text != null ? text.equals(comment.text) : comment.text == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (id_user != null ? id_user.hashCode() : 0);
        result = 31 * result + (id_image != null ? id_image.hashCode() : 0);
        result = 31 * result + (comment_creation != null ? comment_creation.hashCode() : 0);
        result = 31 * result + (comment_lastedit != null ? comment_lastedit.hashCode() : 0);
        result = 31 * result + (comment_likes != null ? comment_likes.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", id_image=" + id_image +
                ", comment_creation='" + comment_creation + '\'' +
                ", comment_lastedit='" + comment_lastedit + '\'' +
                ", comment_likes=" + comment_likes +
                ", text='" + text + '\'' +
                '}';
    }
}