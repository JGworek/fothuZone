package zone.fothu.pets.model;

import java.io.Serializable;

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
        final int prime = 31;
        int result = 1;
        result = prime * result + petLevel;
        result = prime * result + xpToNextLevel;
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
        XPValue other = (XPValue) obj;
        if (petLevel != other.petLevel)
            return false;
        if (xpToNextLevel != other.xpToNextLevel)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "XPChart [petLevel=" + petLevel + ", xpToNextLevel=" + xpToNextLevel + "]";
    }

}
