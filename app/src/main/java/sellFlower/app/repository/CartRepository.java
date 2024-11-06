// app/src/main/java/sellFlower/app/repository/CartRepository.java
package sellFlower.app.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import sellFlower.app.dao.CartItemDao;
import sellFlower.app.database.DatabaseInstance;
import sellFlower.app.model.CartItem;
public class CartRepository {
    private CartItemDao cartItemDao;
    private static CartRepository instance;

    private CartRepository(Context context) {
        cartItemDao = DatabaseInstance.getInstance(context).cartItemDao();
    }

    public static CartRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CartRepository(context);
        }
        return instance;
    }

    public void addToCart(CartItem cartItem, UserRepository.DatabaseCallback<Void> callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    CartItem existingItem = cartItemDao.getCartItemByFlowerId(cartItem.getFlowerId());
                    if (existingItem != null) {
                        existingItem.setQuantity(existingItem.getQuantity() + cartItem.getQuantity());
                        cartItemDao.update(existingItem);
                    } else {
                        cartItemDao.insert(cartItem);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Failed to add item to cart");
                }
            }
        }.execute();
    }

    // Implement other methods (getCartItems, updateCartItem, removeFromCart, clearCart)
    // ...

    public void getTotalCartPrice(UserRepository.DatabaseCallback<Double> callback) {
        new AsyncTask<Void, Void, Double>() {
            @Override
            protected Double doInBackground(Void... voids) {
                return cartItemDao.getTotalCartPrice();
            }

            @Override
            protected void onPostExecute(Double totalPrice) {
                callback.onSuccess(totalPrice);
            }
        }.execute();
    }
}}