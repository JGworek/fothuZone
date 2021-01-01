package zone.fothu.pets.model.adventure;

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
@Table(name = "xp_chart", schema = "pets")
public class XPValue implements Serializable {

    private static final long serialVersionUID = 2468772004595178136L;

    @Id
    @Column(name = "pet_level")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petLevel;

    @Column(name = "xp_to_next_level")
    private int xpToNextLevel;

    public XPValue() {
        super();
    }

    public XPValue(int petLevel, int xpToNextLevel) {
        super();
        this.petLevel = petLevel;
        this.xpToNextLevel = xpToNextLevel;
    }

    public int getPetLevel() {
        return petLevel;
    }

    public void setPetLevel(int petLevel) {
        this.petLevel = petLevel;
    }

    public int getXpToNextLevel() {
        return xpToNextLevel;
    }

    public void setXpToNextLevel(int xpToNextLevel) {
        this.xpToNextLevel = xpToNextLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(petLevel, xpToNextLevel);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof XPValue)) {
            return false;
        }
        XPValue other = (XPValue) obj;
        return petLevel == other.petLevel && xpToNextLevel == other.xpToNextLevel;
    }

    @Override
    public String toString() {
        return "XPChart [petLevel=" + petLevel + ", xpToNextLevel=" + xpToNextLevel + "]";
    }

}
