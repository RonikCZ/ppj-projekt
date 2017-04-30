package cz.karelsir.projekt.data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Ronik on 30. 4. 2017.
 */
@Embeddable
public class TagId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "id_image")
    Image image;

    @Column(name = "tag_title")
    String title;

    public TagId() {
        this.image = new Image();
    }

    public TagId(Image image, String title) {
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

        TagId tagId = (TagId) o;

        if (title != null ? !title.equals(tagId.title) : tagId.title != null) return false;
        return image != null ? image.equals(tagId.image) : tagId.image == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
