package sellFlower.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;

import sellFlower.app.model.User;

@Dao
public interface UserDao {

    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user")
    ArrayList<User> getAllUsers();

    @Query("SELECT * FROM user WHERE id = :UserId")
    User getUserById(int UserId);
}
