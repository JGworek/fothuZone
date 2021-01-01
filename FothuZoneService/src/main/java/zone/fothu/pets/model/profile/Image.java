package zone.fothu.pets.model.profile;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "images", schema = "pets")
public class Image implements Serializable {

    private static final long serialVersionUID = 1279603954667668706L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "image_url")
    private String imageURL;

    public Image() {
        super();
    }

    public Image(int id, String imageURL) {
        super();
        this.id = id;
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imageURL);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Image)) {
            return false;
        }
        Image other = (Image) obj;
        return id == other.id && Objects.equals(imageURL, other.imageURL);
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", imageURL=" + imageURL + "]";
    }
}