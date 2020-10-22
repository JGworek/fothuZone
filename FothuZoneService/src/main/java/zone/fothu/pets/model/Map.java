    package zone.fothu.pets.model;

    import java.io.Serializable;
    import java.util.List;

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
    public class Map implements Serializable {

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
        @JoinColumns({@JoinColumn(name = "map_id", referencedColumnName = "id"), @JoinColumn(name="non_barren_cells", referencedColumnName = "cell")})
        private List<Integer> nonBarrenCells;
        
        @Column(name = "boss_pet_id")
        private int bossPetId;

        public Map() {
            super();
        }

        public Map(int id, String name, int startingRoom, int bossRoom, int ladderRoom, int enemyLevel,
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
            final int prime = 31;
            int result = 1;
            result = prime * result + bossPetId;
            result = prime * result + bossRoom;
            result = prime * result + enemyLevel;
            result = prime * result + id;
            result = prime * result + ladderRoom;
            result = prime * result + monsterSpawnRate;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((nonBarrenCells == null) ? 0 : nonBarrenCells.hashCode());
            result = prime * result + startingRoom;
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
            Map other = (Map) obj;
            if (bossPetId != other.bossPetId)
                return false;
            if (bossRoom != other.bossRoom)
                return false;
            if (enemyLevel != other.enemyLevel)
                return false;
            if (id != other.id)
                return false;
            if (ladderRoom != other.ladderRoom)
                return false;
            if (monsterSpawnRate != other.monsterSpawnRate)
                return false;
            if (name == null) {
                if (other.name != null)
                    return false;
            } else if (!name.equals(other.name))
                return false;
            if (nonBarrenCells == null) {
                if (other.nonBarrenCells != null)
                    return false;
            } else if (!nonBarrenCells.equals(other.nonBarrenCells))
                return false;
            if (startingRoom != other.startingRoom)
                return false;
            return true;
        }

        @Override
        public String toString() {
            return "Map [id=" + id + ", name=" + name + ", startingRoom=" + startingRoom + ", bossRoom=" + bossRoom
                + ", ladderRoom=" + ladderRoom + ", enemyLevel=" + enemyLevel + ", monsterSpawnRate=" + monsterSpawnRate
                + ", nonBarrenCells=" + nonBarrenCells + ", bossPetId=" + bossPetId + "]";
        }
}
