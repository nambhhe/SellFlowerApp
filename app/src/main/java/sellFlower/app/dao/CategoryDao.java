package sellFlower.app.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import sellFlower.app.model.Category;
import sellFlower.app.model.Flower;

@Dao
public interface CategoryDao {

    @Insert
    void insertCategory(Category category);

    @Query("SELECT * FROM flower WHERE categoryId = :categoryId")
    List<Flower> getFlowersByCategory(int categoryId);
}
