package zone.fothu.pets.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

    @Column(name = "image_owner_username")
    private String imageOwnerUsername;

    @Column(name = "image_owner_name")
    private String imageOwnerName;

    public Image() {
        super();
    }

    public Image(int id, String imageURL, String imageOwnerUsername, String imageOwnerName) {
        super();
        this.id = id;
        this.imageURL = imageURL;
        this.imageOwnerUsername = imageOwnerUsername;
        this.imageOwnerName = imageOwnerName;
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

    public String getImageOwnerUsername() {
        return imageOwnerUsername;
    }

    public void setImageOwnerUsername(String imageOwnerUsername) {
        this.imageOwnerUsername = imageOwnerUsername;
    }

    public String getImageOwnerName() {
        return imageOwnerName;
    }

    public void setImageOwnerName(String imageOwnerName) {
        this.imageOwnerName = imageOwnerName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        result = prime * result + ((imageOwnerName == null) ? 0 : imageOwnerName.hashCode());
        result = prime * result + ((imageOwnerUsername == null) ? 0 : imageOwnerUsername.hashCode());
        result = prime * result + ((imageURL == null) ? 0 : imageURL.hashCode());
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
        Image other = (Image) obj;
        if (id != other.id)
            return false;
        if (imageOwnerName == null) {
            if (other.imageOwnerName != null)
                return false;
        } else if (!imageOwnerName.equals(other.imageOwnerName))
            return false;
        if (imageOwnerUsername == null) {
            if (other.imageOwnerUsername != null)
                return false;
        } else if (!imageOwnerUsername.equals(other.imageOwnerUsername))
            return false;
        if (imageURL == null) {
            if (other.imageURL != null)
                return false;
        } else if (!imageURL.equals(other.imageURL))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", imageURL=" + imageURL + ", imageOwnerUsername=" + imageOwnerUsername
            + ", imageOwnerName=" + imageOwnerName + "]";
    }
}