package sellFlower.app.controller.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import sellFlower.app.controller.adapter.CartAdapter;
import sellFlower.app.databinding.ActivityCartBinding;
import sellFlower.app.model.CartItem;
import sellFlower.app.repository.CartRepository;

public class CartActivity extends AppCompatActivity {
    private ActivityCartBinding binding;
    private CartRepository cartRepository;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartRepository = CartRepository.getInstance(this);
        setupRecyclerView();
        loadCartItems();
        updateTotalPrice();

        binding.checkoutButton.setOnClickListener(v -> checkout());
    }

    private void setupRecyclerView() {
        cartAdapter = new CartAdapter(new ArrayList<>(), this::updateCartItem, this::removeCartItem);
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.cartRecyclerView.setAdapter(cartAdapter);
    }

    private void loadCartItems() {
        cartRepository.getCartItems(new CartRepository.DatabaseCallback<List<CartItem>>() {
            @Override
            public void onSuccess(List<CartItem> result) {
                cartAdapter.updateItems(result);
            }

            @Override
            public void onError(String error) {
                Toast.makeText(CartActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateTotalPrice() {
        cartRepository.getTotalCartPrice(new CartRepository.DatabaseCallback<Double>() {
            @Override
            public void onSuccess(Double result) {
                binding.totalPriceTextView.setText(String.format("Total: $%.2f", result));
            }

            @Override
            public void onError(String error) {
                Toast.makeText(CartActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCartItem(CartItem cartItem) {
        cartRepository.updateCartItem(cartItem, new CartRepository.DatabaseCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                updateTotalPrice();
            }

            @Override
            public void onError(String error) {
                Toast.makeText(CartActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeCartItem(CartItem cartItem) {
        cartRepository.removeFromCart(cartItem, new CartRepository.DatabaseCallback<Void>() {
            @Override
            public void onSuccess(Void result) {