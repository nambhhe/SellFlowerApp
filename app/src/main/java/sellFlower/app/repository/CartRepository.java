package sellFlower.app.repository;

import android.content.Context;
import android.os.AsyncTask;
import java.util.List;

import sellFlower.app.dao.CartItemDao;
import sellFlower.app.database.DatabaseInstance;
import sellFlower.app.model.CartItem;
import sellFlower.app.model.Flower;

public class CartRepository {
    private CartItemDao cartItemDao;
    private static CartRepository instance;
    private FlowerRepository flowerRepository;

    private CartRepository(Context context) {
        cartItemDao = DatabaseInstance.getInstance(context).cartItemDao();
        flowerRepository = FlowerRepository.getInstance(context);
    }

    public static CartRepository getInstance(Context context) {
        if (instance == null) {
            instance = new CartRepository(context);
        }
        return instance;
    }

    // Add DatabaseCallback interface
    public interface DatabaseCallback<T> {
        void onSuccess(T result);
        void onError(String error);
    }

    // Method to get all cart items
    public void getCartItems(DatabaseCallback<List<CartItem>> callback) {
        new AsyncTask<Void, Void, List<CartItem>>() {
            private String errorMessage;

            @Override
            protected List<CartItem> doInBackground(Void... voids) {
                try {
                    return cartItemDao.getAllCartItems();
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<CartItem> result) {
                if (result != null) {
                    callback.onSuccess(result);
                } else {
                    callback.onError(errorMessage != null ? errorMessage : "Failed to load cart items");
                }
            }
        }.execute();
    }

    // Method to get total cart price
    public void getTotalCartPrice(DatabaseCallback<Double> callback) {
        new AsyncTask<Void, Void, Double>() {
            private String errorMessage;

            @Override
            protected Double doInBackground(Void... voids) {
                try {
                    return cartItemDao.getTotalCartPrice();
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Double result) {
                if (result != null) {
                    callback.onSuccess(result);
                } else {
                    callback.onError(errorMessage != null ? errorMessage : "Failed to calculate total price");
                }
            }
        }.execute();
    }

    public void addToCart(int flowerId, int quantity, DatabaseCallback<Void> callback) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    // Get flower details from FlowerRepository
                    Flower flower = flowerRepository.getFlowerById(flowerId);
                    if (flower == null) {
                        return false;
                    }

                    CartItem existingItem = cartItemDao.getCartItemByFlowerId(flowerId);
                    if (existingItem != null) {
                        existingItem.setQuantity(existingItem.getQuantity() + quantity);
                        cartItemDao.update(existingItem);
                    } else {
                        // Create new CartItem with all required parameters
                        CartItem newItem = new CartItem(
                                flowerId,
                                flower.getName(),
                                flower.getPrice(),
                                quantity,
                                flower.getImageUrl()
                        );
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

    // Method to check if an item exists in cart
    public void checkItemInCart(int flowerId, DatabaseCallback<Boolean> callback) {
        new AsyncTask<Void, Void, Boolean>() {
            private String errorMessage;

            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    CartItem item = cartItemDao.getCartItemByFlowerId(flowerId);
                    return item != null;
                } catch (Exception e) {
                    errorMessage = e.getMessage();
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (result != null) {
                    callback.onSuccess(result);
                } else {
                    callback.onError(errorMessage != null ? errorMessage : "Failed to check item in cart");
                }
            }
        }.execute();
    }
}