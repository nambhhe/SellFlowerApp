// app/src/main/java/sellFlower/app/dao/CartItemDao.java
package sellFlower.app.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sellFlower.app.model.CartItem;

@Dao
public interface CartItemDao {
    @Insert
    void insert(CartItem cartItem);

    @Update
    void update(CartItem cartItem);

    @Delete
    void delete(CartItem cartItem);

    @Query("SELECT * FROM cart_items")
    List<CartItem> getAllCartItems();

    @Query("SELECT * FROM cart_items WHERE flowerId = :flowerId")
    CartItem getCartItemByFlowerId(int flowerId);

    @Query("DELETE FROM cart_items")
    void clearCart();
}