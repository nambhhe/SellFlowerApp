package sellFlower.app.controller.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Toast;

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
//    private CartRepository cartRepository;
    private FlowerRepository flowerRepository;
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlowerDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        cartRepository = CartRepository.getInstance(this);
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

    }

    private void setupListeners() {
        binding.decreaseQuantityButton.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                binding.quantityEditText.setText(String.format("%02d", quantity));
            }
        });

        binding.increaseQuantityButton.setOnClickListener(v -> {
            quantity++;
            binding.quantityEditText.setText(String.format("%02d", quantity));
        });

        binding.quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    quantity = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    quantity = 1; // Default to 1 if input is invalid
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.addToCartButton.setOnClickListener(v -> addToCart());
    }

    private void addToCart() {
        if (flower.getStock() >= quantity) {
//            cartRepository.addToCart(flower, quantity);
            flower.setStock(flower.getStock() - quantity);
            flowerRepository.updateFlower(flower);
            Toast.makeText(this, "Added to cart", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not enough stock available", Toast.LENGTH_SHORT).show();
        }
    }
}