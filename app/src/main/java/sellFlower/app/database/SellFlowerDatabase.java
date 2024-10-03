package sellFlower.app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import sellFlower.app.dao.FlowerDao;
import sellFlower.app.dao.UserDao;
import sellFlower.app.model.Flower;
import sellFlower.app.model.User;

@Database(entities = {
        User.class,
        Flower.class
        },
        version = 1)
public abstract class SellFlowerDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract FlowerDao flowerDao();
}
