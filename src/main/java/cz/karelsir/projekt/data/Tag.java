package cz.karelsir.projekt.data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Ronik on 7. 4. 2017.
 */
@Entity
@Table(name = "tag")
@IdClass(TagId.class)
public class Tag implements Serializable {

    @Id
    @Column(name = "tag_title")
    private String title;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_image")
    private Image image;

    public Tag() {
        this.image = new Image();
    }

    public Tag(Image image, String title) {
        this.image = image;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

        Tag tag = (Tag) o;

        if (title != null ? !title.equals(tag.title) : tag.title != null) return false;
        return image != null ? image.equals(tag.image) : tag.image == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "title='" + title + '\'' +
                ", image=" + image +
                '}';
    }
}
