package sellFlower.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

import sellFlower.app.model.Flower;

@Dao
public interface FlowerDao {

    @Insert
    void insertFlower(Flower flower);

    @Update
    void updateFlower(Flower flower);

    @Delete
    void deleteFlower(Flower flower);
}
