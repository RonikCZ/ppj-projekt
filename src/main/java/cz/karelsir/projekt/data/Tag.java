package cz.karelsir.projekt.data;

/**
 * Created by Ronik on 7. 4. 2017.
 */
public class Tag {

    private String tag_title;

    private Integer id_image;

    public Tag() {

    }

    public Tag(int id_image, String tag_title) {
        this.id_image = id_image;
        this.tag_title = tag_title;
    }

    public String getTag_title() {
        return tag_title;
    }

    public void setTag_title(String tag_title) {
        this.tag_title = tag_title;
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

        Tag tag = (Tag) o;

        if (tag_title != null ? !tag_title.equals(tag.tag_title) : tag.tag_title != null) return false;
        return id_image != null ? id_image.equals(tag.id_image) : tag.id_image == null;
    }

    @Override
    public int hashCode() {
        int result = tag_title != null ? tag_title.hashCode() : 0;
        result = 31 * result + (id_image != null ? id_image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag_title='" + tag_title + '\'' +
                ", id_image=" + id_image +
                '}';
    }
}
