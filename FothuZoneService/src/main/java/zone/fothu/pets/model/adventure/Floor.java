package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
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
@Table(name = "floors", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class Floor implements Serializable {

	private static final long serialVersionUID = -8689810916101092985L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	@JoinColumn(name = "dungeon_id")
	@JsonIgnoreProperties("floors")
	Dungeon dungeon;
	@Column(name = "floor_number")
	private int floorNumber;
	@Column(name = "enemy_level")
	private int enemyLevel;
	@Column(name = "monster_spawn_rate")
	private int monsterSpawnRate;
	@Column(name = "starting_room")
	private int startingRoom;
	@Column(name = "boss_room")
	private int bossRoom;
	@Column(name = "ladder_room")
	private int ladderRoom;
	@ManyToOne
	@JoinColumn(name = "mini_boss_pet_id")
	private Pet miniBossPet;
	@ElementCollection
	@CollectionTable(schema = "pets", name = "floors_non_barren_cells")
	@JoinColumns({ @JoinColumn(name = "floor_id", referencedColumnName = "id"), @JoinColumn(name = "non_barren_cell", referencedColumnName = "cell") })
	private List<Integer> nonBarrenCells;
}