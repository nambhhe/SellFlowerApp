package sellFlower.app.controller.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import sellFlower.app.controller.adapter.CartAdapter;
import sellFlower.app.databinding.ActivityCartBinding;
import sellFlower.app.model.CartItem;
import sellFlower.app.repository.CartRepository;
import sellFlower.app.repository.FlowerRepository;

public class CartActivity extends AppCompatActivity implements CartAdapter.CartItemListener {

    private ActivityCartBinding binding;
    private CartRepository cartRepository;
    private FlowerRepository flowerRepository;
    private List<CartItem> cartItems;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartRepository = CartRepository.getInstance(this);
        flowerRepository = FlowerRepository.getInstance(this);
        cartItems = new ArrayList<>();

        setupRecyclerView();
        loadCartItems();
        setupCheckoutButton();
    }

    private void setupRecyclerView() {
        cartAdapter = new CartAdapter(cartItems, this);
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecyclerView.setAdapter(cartAdapter);
    }

    private void loadCartItems() {
        binding.progressBar.setVisibility(View.VISIBLE);
        cartRepository.getCartItems(new CartRepository.DatabaseCallback<List<CartItem>>() {
            @Override
            public void onSuccess(List<CartItem> result) {
                binding.progressBar.setVisibility(View.GONE);
                cartItems.clear();
                cartItems.addAll(result);
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
                updateEmptyState();
            }

            @Override
            public void onError(String error) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(CartActivity.this, "Error loading cart items: " + error, Toast.LENGTH_SHORT).show();
                updateEmptyState();
            }
        });
    }

    private void updateTotalPrice() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getPrice() * item.getQuantity();
        }
        binding.totalPriceTextView.setText(String.format("Total: $%.2f", total));
    }

    private void updateEmptyState() {
        if (cartItems.isEmpty()) {
            binding.emptyCartView.setVisibility(View.VISIBLE);
            binding.cartRecyclerView.setVisibility(View.GONE);
            binding.checkoutButton.setEnabled(false);
        } else {
            binding.emptyCartView.setVisibility(View.GONE);
            binding.cartRecyclerView.setVisibility(View.VISIBLE);
            binding.checkoutButton.setEnabled(true);
        }
    }

    @Override
    public void onQuantityChanged(CartItem cartItem, int newQuantity) {
        cartItem.setQuantity(newQuantity);
        cartRepository.updateCartItem(cartItem, new CartRepository.DatabaseCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                updateTotalPrice();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(CartActivity.this, "Error updating cart item: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRemoveItem(CartItem cartItem) {
        cartRepository.removeFromCart(cartItem, new CartRepository.DatabaseCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                cartItems.remove(cartItem);
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
                updateEmptyState();
                Toast.makeText(CartActivity.this, "Item removed from cart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(CartActivity.this, "Error removing item from cart: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupCheckoutButton() {
        binding.checkoutButton.setOnClickListener(v -> proceedToCheckout());
    }

    private void proceedToCheckout() {
        // Here you would typically start a new CheckoutActivity
        // For now, we'll just clear the cart as an example
        cartRepository.clearCart(new CartRepository.DatabaseCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                Toast.makeText(CartActivity.this, "Checkout successful! Cart cleared.", Toast.LENGTH_SHORT).show();
                cartItems.clear();
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
                updateEmptyState();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(CartActivity.this, "Error during checkout: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}