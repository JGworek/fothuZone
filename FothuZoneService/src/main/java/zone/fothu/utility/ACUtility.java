package zone.fothu.utility;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ACUtility implements Serializable {

    private static final long serialVersionUID = -137278287806571455L;

    @Autowired
    AnnotationConfigApplicationContext applicationContext;
    
    private boolean scanned = false;
    
    public void ACScan() {
        applicationContext.scan("zone.fothu.pets.model");
        if (this.scanned == false) {
            applicationContext.refresh();
            this.scanned = true;
        }
    }
    
    public ACUtility() {
        super();
    }

    public ACUtility(boolean scanned) {
        super();
        this.scanned = scanned;
    }

    public boolean isScanned() {
        return scanned;
    }

    public void setScanned(boolean scanned) {
        this.scanned = scanned;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (scanned ? 1231 : 1237);
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
        ACUtility other = (ACUtility) obj;
        if (scanned != other.scanned)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "ACUtility [scanned=" + scanned + "]";
    }
    
}
