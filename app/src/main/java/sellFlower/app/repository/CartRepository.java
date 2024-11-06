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

    public void addToCart(int flowerId, int quantity, DatabaseCallback<Void> callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    CartItem existingItem = cartItemDao.getCartItemByFlowerId(flowerId);
                    if (existingItem != null) {
                        existingItem.setQuantity(existingItem.getQuantity() + quantity);
                        cartItemDao.update(existingItem);
                    } else {
                        CartItem newItem = new CartItem(flowerId, quantity);
                        cartItemDao.insert(newItem);
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

    public void getCartItems(DatabaseCallback<List<CartItem>> callback) {
        new AsyncTask<Void, Void, List<CartItem>>() {
            @Override
            protected List<CartItem> doInBackground(Void... voids) {
                return cartItemDao.getAllCartItems();
            }

            @Override
            protected void onPostExecute(List<CartItem> cartItems) {
                callback.onSuccess(cartItems);
            }
        }.execute();
    }

    public void updateCartItem(CartItem cartItem, DatabaseCallback<Void> callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    cartItemDao.update(cartItem);
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
                    callback.onError("Failed to update cart item");
                }
            }
        }.execute();
    }

    public void removeFromCart(CartItem cartItem, DatabaseCallback<Void> callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    cartItemDao.delete(cartItem);
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
                    callback.onError("Failed to remove item from cart");
                }
            }
        }.execute();
    }

    public void clearCart(DatabaseCallback<Void> callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    cartItemDao.clearCart();
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
                    callback.onError("Failed to clear cart");
                }
            }
        }.execute();
    }

    public interface DatabaseCallback<T> {
        void onSuccess(T result);
        void onError(String error);
    }
}