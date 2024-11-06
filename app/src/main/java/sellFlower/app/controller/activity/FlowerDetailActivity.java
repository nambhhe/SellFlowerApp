package sellFlower.app.controller.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import sellFlower.app.R;
import sellFlower.app.databinding.ActivityFlowerDetailBinding;
import sellFlower.app.model.Flower;
import sellFlower.app.repository.CartRepository;
import sellFlower.app.repository.FlowerRepository;

public class FlowerDetailActivity extends AppCompatActivity {

    private ActivityFlowerDetailBinding binding;
    private Flower flower;
    private CartRepository cartRepository;
    private FlowerRepository flowerRepository;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlowerDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        cartRepository = CartRepository.getInstance(this);
        flowerRepository = FlowerRepository.getInstance(this);

        int flowerId = getIntent().getIntExtra("FLOWER_ID", -1);
        if (flowerId != -1) {
            loadFlowerDetails(flowerId);
        } else {
            Toast.makeText(this, "Error loading flower details", Toast.LENGTH_SHORT).show();
            finish();
        }

        setupListeners();
    }

    private void loadFlowerDetails(int flowerId) {
        flower = flowerRepository.getFlowerById(flowerId);
        if (flower != null) {
            displayFlowerDetails();
        } else {
            Toast.makeText(this, "Flower not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void displayFlowerDetails() {
        binding.flowerNameTextView.setText(flower.getName());
        binding.flowerPriceTextView.setText(String.format("$%.2f", flower.getPrice()));
        binding.flowerDescriptionTextView.setText(flower.getDescription());

        Glide.with(this)
                .load(flower.getImageUrl())
                .placeholder(R.drawable.loading_placeholder)
                .error(R.drawable.error_placeholder)
                .into(binding.flowerImageView);

        // Display stock information
//        binding.stockTextView.setText(String.format("Stock: %d", flower.getStock()));
    }

    private void setupListeners() {
        binding.decreaseQuantityButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                updateQuantityDisplay();
            }
        });

        binding.increaseQuantityButton.setOnClickListener(v -> {
            if (quantity < flower.getStock()) {
                quantity++;
                updateQuantityDisplay();
            } else {
                Toast.makeText(this, "Maximum stock reached", Toast.LENGTH_SHORT).show();
            }
        });

        binding.quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int newQuantity = Integer.parseInt(s.toString());
                    if (newQuantity > 0 && newQuantity <= flower.getStock()) {
                        quantity = newQuantity;
                    } else {
                        quantity = 1;
                        updateQuantityDisplay();
                    }
                } catch (NumberFormatException e) {
                    quantity = 1;
                    updateQuantityDisplay();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.addToCartButton.setOnClickListener(v -> addToCart());
    }

    private void updateQuantityDisplay() {
        binding.quantityEditText.setText(String.format("%02d", quantity));
    }

    private void addToCart() {
        if (flower.getStock() >= quantity) {
            // Show confirmation dialog
            new AlertDialog.Builder(this)
                    .setTitle("Add to Cart")
                    .setMessage("Add " + quantity + " " + flower.getName() + " to cart?")
                    .setPositiveButton("Add", (dialog, which) -> {
                        // Show loading dialog
                        ProgressDialog progressDialog = new ProgressDialog(this);
                        progressDialog.setMessage("Adding to cart...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();

                        cartRepository.addToCart(flower.getId(), quantity, new CartRepository.DatabaseCallback<Void>() {
                            @Override
                            public void onSuccess(Void result) {
                                // Update stock in database
                                flower.setStock(flower.getStock() - quantity);
                                flowerRepository.updateFlower(flower);

                                runOnUiThread(() -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(FlowerDetailActivity.this,
                                            "Successfully added to cart", Toast.LENGTH_SHORT).show();

                                    // Navigate to CartActivity
                                    Intent intent = new Intent(FlowerDetailActivity.this, CartActivity.class);
                                    startActivity(intent);
//                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                });
                            }

                            @Override
                            public void onError(String error) {
                                runOnUiThread(() -> {
                                    progressDialog.dismiss();
                                    Toast.makeText(FlowerDetailActivity.this,
                                            "Error: " + error, Toast.LENGTH_SHORT).show();
                                });
                            }
                        });
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        } else {
            Toast.makeText(this, "Not enough stock available", Toast.LENGTH_SHORT).show();
        }
    }

    // Optional: Handle back button press with animation
//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//    }
}