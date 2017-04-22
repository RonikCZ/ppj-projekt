package cz.karelsir.projekt.data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Ronik on 7. 4. 2017.
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    @Id
    @Column(name = "tag_title")
    private String tag_title;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_image")
    private Image image;

    public Tag() {
        this.image = new Image();
    }

    public Tag(Image image, String tag_title) {
        this.image = image;
        this.tag_title = tag_title;
    }

    public String getTag_title() {
        return tag_title;
    }

    public void setTag_title(String tag_title) {
        this.tag_title = tag_title;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tag_title == null) ? 0 : tag_title.hashCode());

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
        Tag other = (Tag) obj;
        if (tag_title == null) {
            if (other.tag_title != null)
                return false;
        } else if (!tag_title.equals(other.tag_title))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Tag [tag_title=" + tag_title + ", image=" + image + "]";
    }

}
