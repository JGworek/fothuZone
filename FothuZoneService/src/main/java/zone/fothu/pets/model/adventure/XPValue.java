package zone.fothu.pets.model.adventure;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Component
@Scope("prototype")
@Entity
@Table(name = "xp_chart", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class XPValue implements Serializable {

	private static final long serialVersionUID = 2468772004595178136L;

	@Id
	@Column(name = "pet_level")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int petLevel;
	@Column(name = "xp_to_next_level")
	private int xpToNextLevel;
}