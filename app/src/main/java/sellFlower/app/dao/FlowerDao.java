package sellFlower.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sellFlower.app.model.Flower;

@Dao
public interface FlowerDao {

    @Insert
    void insertFlower(Flower flower);

    @Update
    void updateFlower(Flower flower);

    @Delete
    void deleteFlower(Flower flower);

    @Query("SELECT * FROM flower WHERE isFeatured = 1")
    List<Flower> getAllFeaturedFlowers();

    @Query("SELECT * FROM flower")
    List<Flower> getAllFlower();

    @Query("SELECT * FROM flower WHERE id = :id")
    Flower getFlowerById(int id);

    @Query("SELECT * FROM flower WHERE categoryId = :categoryId")
    List<Flower> getFlowersByCategory(int categoryId);

    @Query("SELECT * FROM flower WHERE name LIKE :query OR description LIKE :query")
    List<Flower> searchFlowers(String query);

    @Query("UPDATE flower SET stock = :newStock WHERE id = :flowerId")
    void updateStock(int flowerId, int newStock);

}