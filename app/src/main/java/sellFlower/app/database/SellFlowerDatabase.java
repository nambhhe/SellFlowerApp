package sellFlower.app.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import sellFlower.app.dao.CartItemDao;
import sellFlower.app.dao.FlowerDao;
import sellFlower.app.dao.UserDao;
import sellFlower.app.model.CartItem;
import sellFlower.app.model.Flower;
import sellFlower.app.model.User;
@Database(entities = {User.class, Flower.class, CartItem.class}, version = 3)
public abstract class SellFlowerDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract FlowerDao flowerDao();
    public abstract CartItemDao cartItemDao();
}
