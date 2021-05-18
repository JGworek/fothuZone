package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import zone.fothu.pets.model.profile.Pet;

@Component
@Scope("prototype")
@Entity
@Table(name = "dungeons", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class Dungeon implements Serializable {

	private static final long serialVersionUID = 6885313722453626583L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "dungeon_name")
	private String dungeonName;
	@OneToMany(mappedBy = "dungeon")
	@JsonIgnoreProperties("dungeon")
	@OrderBy("floor_number")
	List<Floor> floors;
	@ManyToOne
	@JoinColumn(name = "boss_pet_id")
	private Pet bossPet;
}