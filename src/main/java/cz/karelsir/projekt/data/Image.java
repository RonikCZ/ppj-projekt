package cz.karelsir.projekt.data;

public class Image {

    private Integer id;
    private Integer id_user;

    private String url;
    private String title;
    private String image_creation;
    private String image_lastedit;
    private Integer image_likes;

    public Image() {

    }

    public Image(int id_user, String url, String title, String image_creation) {
        this.id_user = id_user;
        this.url = url;
        this.title = title;
        this.image_creation = image_creation;
        this.image_lastedit = image_creation;
        this.image_likes = 0;
    }

    public Image(int id, int id_user, String url, String title, String image_creation, String date_lastedit, Integer likes) {
        this.id = id;
        this.id_user = id_user;
        this.url = url;
        this.title = title;
        this.image_creation = image_creation;
        this.image_lastedit = date_lastedit;
        this.image_likes = likes;
    }

    public Integer getId() {
        return id;
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

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Image image = (Image) o;

        if (id != null ? !id.equals(image.id) : image.id != null) return false;
        if (id_user != null ? !id_user.equals(image.id_user) : image.id_user != null) return false;
        if (url != null ? !url.equals(image.url) : image.url != null) return false;
        if (title != null ? !title.equals(image.title) : image.title != null) return false;
        if (image_creation != null ? !image_creation.equals(image.image_creation) : image.image_creation != null)
            return false;
        if (image_lastedit != null ? !image_lastedit.equals(image.image_lastedit) : image.image_lastedit != null)
            return false;
        return image_likes != null ? image_likes.equals(image.image_likes) : image.image_likes == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (id_user != null ? id_user.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (image_creation != null ? image_creation.hashCode() : 0);
        result = 31 * result + (image_lastedit != null ? image_lastedit.hashCode() : 0);
        result = 31 * result + (image_likes != null ? image_likes.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", id_user=" + id_user +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", image_creation='" + image_creation + '\'' +
                ", image_lastedit='" + image_lastedit + '\'' +
                ", image_likes=" + image_likes +
                '}';
    }
}
