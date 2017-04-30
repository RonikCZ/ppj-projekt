package cz.karelsir.projekt.data;

import java.io.Serializable;

/**
 * Created by Ronik on 30. 4. 2017.
 */
public class TagId implements Serializable {
    String title;
    Image image;

    public TagId() {
        this.image = new Image();
    }

    public TagId(String title, Image image) {
        this.title = title;
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
