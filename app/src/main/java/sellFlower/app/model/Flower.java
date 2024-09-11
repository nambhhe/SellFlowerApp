package sellFlower.app.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flower")
public class Flower {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String flowerName;
    private String description;

    public Flower(int id, String flowerName, String description) {
        this.id = id;
        this.flowerName = flowerName;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
