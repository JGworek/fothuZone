package zone.fothu.pets.model.adventure;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "maps", schema = "pets")
public class DungeonMap implements Serializable {

    private static final long serialVersionUID = 6885313722453626583L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "starting_room")
    private int startingRoom;

    @Column(name = "boss_room", nullable = true)
    private int bossRoom;

    @Column(name = "ladder_room", nullable = true)
    private int ladderRoom;

    @Column(name = "enemy_level")
    private int enemyLevel;

    @Column(name = "monster_spawn_rate")
    private int monsterSpawnRate;

    @ElementCollection
    @CollectionTable(schema = "pets", name = "maps_non_barren_cells")
    @JoinColumns({ @JoinColumn(name = "map_id", referencedColumnName = "id"),
        @JoinColumn(name = "non_barren_cells", referencedColumnName = "cell") })
    private List<Integer> nonBarrenCells;

    @Column(name = "boss_pet_id")
    private int bossPetId;

    public DungeonMap() {
        super();
    }

    public DungeonMap(int id, String name, int startingRoom, int bossRoom, int ladderRoom, int enemyLevel,
        int monsterSpawnRate, List<Integer> nonBarrenCells, int bossPetId) {
        super();
        this.id = id;
        this.name = name;
        this.startingRoom = startingRoom;
        this.bossRoom = bossRoom;
        this.ladderRoom = ladderRoom;
        this.enemyLevel = enemyLevel;
        this.monsterSpawnRate = monsterSpawnRate;
        this.nonBarrenCells = nonBarrenCells;
        this.bossPetId = bossPetId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStartingRoom() {
        return startingRoom;
    }

    public void setStartingRoom(int startingRoom) {
        this.startingRoom = startingRoom;
    }

    public int getBossRoom() {
        return bossRoom;
    }

    public void setBossRoom(int bossRoom) {
        this.bossRoom = bossRoom;
    }

    public int getLadderRoom() {
        return ladderRoom;
    }

    public void setLadderRoom(int ladderRoom) {
        this.ladderRoom = ladderRoom;
    }

    public int getEnemyLevel() {
        return enemyLevel;
    }

    public void setEnemyLevel(int enemyLevel) {
        this.enemyLevel = enemyLevel;
    }

    public int getMonsterSpawnRate() {
        return monsterSpawnRate;
    }

    public void setMonsterSpawnRate(int monsterSpawnRate) {
        this.monsterSpawnRate = monsterSpawnRate;
    }

    public List<Integer> getNonBarrenCells() {
        return nonBarrenCells;
    }

    public void setNonBarrenCells(List<Integer> nonBarrenCells) {
        this.nonBarrenCells = nonBarrenCells;
    }

    public int getBossPetId() {
        return bossPetId;
    }

    public void setBossPetId(int bossPetId) {
        this.bossPetId = bossPetId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(bossPetId, bossRoom, enemyLevel, id, ladderRoom, monsterSpawnRate, name, nonBarrenCells,
            startingRoom);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DungeonMap)) {
            return false;
        }
        DungeonMap other = (DungeonMap) obj;
        return bossPetId == other.bossPetId && bossRoom == other.bossRoom && enemyLevel == other.enemyLevel
            && id == other.id && ladderRoom == other.ladderRoom && monsterSpawnRate == other.monsterSpawnRate
            && Objects.equals(name, other.name) && Objects.equals(nonBarrenCells, other.nonBarrenCells)
            && startingRoom == other.startingRoom;
    }

    @Override
    public String toString() {
        return "DungeonMap [id=" + id + ", name=" + name + ", startingRoom=" + startingRoom + ", bossRoom=" + bossRoom
            + ", ladderRoom=" + ladderRoom + ", enemyLevel=" + enemyLevel + ", monsterSpawnRate=" + monsterSpawnRate
            + ", nonBarrenCells=" + nonBarrenCells + ", bossPetId=" + bossPetId + "]";
    }
}
