package cz.karelsir.projekt.data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Ronik on 7. 4. 2017.
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    @EmbeddedId
    private TagId tagId;

    public Tag() {

    }

    public Tag(Image image, String title) {
        this.tagId = new TagId(image, title);
    }

    public String getTitle() {
        return tagId.getTitle();
    }

    public void setTitle(String title) {
        this.tagId.setTitle(title);
    }

    public Image getImage() {
        return tagId.getImage();
    }

    public void setImage(Image image) {
        this.tagId.setImage(image);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        return tagId != null ? tagId.equals(tag.tagId) : tag.tagId == null;
    }

    @Override
    public int hashCode() {
        return tagId != null ? tagId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tagId=" + tagId +
                '}';
    }
}
