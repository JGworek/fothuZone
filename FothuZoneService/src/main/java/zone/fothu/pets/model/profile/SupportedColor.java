package zone.fothu.pets.model.profile;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "supported_user_colors", schema = "pets")
public class SupportedColor implements Serializable {

	private static final long serialVersionUID = -5659595201025851537L;

	@Id
	@Column(name = "colors")
	private String color;

	public SupportedColor() {
		super();
	}

	public SupportedColor(String color) {
		super();
		this.color = color;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SupportedColor)) {
			return false;
		}
		SupportedColor other = (SupportedColor) obj;
		return Objects.equals(color, other.color);
	}

	@Override
	public String toString() {
		return "SupportedColor [color=" + color + "]";
	}
}
