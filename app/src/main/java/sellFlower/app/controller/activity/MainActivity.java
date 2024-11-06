package sellFlower.app.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.ArrayList;
import java.util.List;

import sellFlower.app.R;
import sellFlower.app.controller.adapter.PopularAdapter;
import sellFlower.app.repository.FlowerRepository;
import sellFlower.app.model.Flower;
import sellFlower.app.databinding.ActivityMainBinding;
import sellFlower.app.repository.UserRepository;

public class MainActivity extends AppCompatActivity implements PopularAdapter.OnFlowerClickListener{

    private FlowerRepository flowerRepository;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        flowerRepository = FlowerRepository.getInstance(this);

        UserRepository userRepository = UserRepository.getInstance(this);
        userRepository.insertSampleUsers(this);
        setStatusBarColor();
        insertSampleFlowers();
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_cart) {
            // Navigate to CartActivity
            Intent intent = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setStatusBarColor() {
        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.purple_dark));
    }

    private void initRecyclerView() {
        List<Flower> itemList = flowerRepository.getAllFlowers();
        binding.popularView.setAdapter(new PopularAdapter((ArrayList<Flower>) itemList,this));
    }
    @Override
    public void onFlowerClick(int flowerId) {
        Intent intent = new Intent(MainActivity.this, FlowerDetailActivity.class);
        intent.putExtra("FLOWER_ID", flowerId);
        startActivity(intent);
    }
    private void insertSampleFlowers() {
        // Check if the database is empty before inserting sample data
        if (flowerRepository.getAllFlowers().isEmpty()) {
            List<Flower> sampleFlowers = new ArrayList<>();
            sampleFlowers.add(new Flower(0,
                    "Red Rose Bouquet",
                    "Elegant bouquet of fresh red roses symbolizing love and passion",
                    59.99,
                    "https://images.unsplash.com/photo-1582794543139-8ac9cb0f7b11",
                    1, 15, 4.8f,
                    "Bouquet",
                    "Romance"));

            sampleFlowers.add(new Flower(0,
                    "Pink Rose Basket",
                    "Delicate pink roses arranged in a rustic basket",
                    49.99,
                    "https://images.unsplash.com/photo-1561181286-d3fee7d55364",
                    1, 12, 4.6f,
                    "Basket",
                    "Birthday"));

            // Sunflower Arrangements
            sampleFlowers.add(new Flower(0,
                    "Sunny Sunflower Bunch",
                    "Bright and cheerful sunflowers to light up any room",
                    45.99,
                    "https://images.unsplash.com/photo-1551627651-23e5e9438bb0",
                    2, 10, 4.7f,
                    "Bunch",
                    "Summer"));

            // Lily Arrangements
            sampleFlowers.add(new Flower(0,
                    "White Lily Elegance",
                    "Pure white lilies symbolizing peace and serenity",
                    65.99,
                    "https://images.unsplash.com/photo-1577090727079-a854293d7cd2",
                    3, 8, 4.9f,
                    "Arrangement",
                    "Sympathy"));

            // Tulip Arrangements
            sampleFlowers.add(new Flower(0,
                    "Rainbow Tulip Mix",
                    "Vibrant mix of colorful spring tulips",
                    39.99,
                    "https://images.unsplash.com/photo-1520618821905-f81f9f199fb8",
                    4, 20, 4.5f,
                    "Bouquet",
                    "Spring"));

            // Mixed Arrangements
            sampleFlowers.add(new Flower(0,
                    "Garden Fresh Mix",
                    "Beautiful combination of seasonal flowers",
                    69.99,
                    "https://images.unsplash.com/photo-1563241527-3004b7be0ffd",
                    5, 10, 4.7f,
                    "Mixed",
                    "Anniversary"));

            sampleFlowers.add(new Flower(0,
                    "Purple Paradise",
                    "Luxurious arrangement of purple orchids and lilacs",
                    79.99,
                    "https://images.unsplash.com/photo-1584279415770-f40c0d5c5da4",
                    5, 7, 4.8f,
                    "Luxury",
                    "Special"));

            sampleFlowers.add(new Flower(0,
                    "Wildflower Delight",
                    "Natural arrangement of fresh wildflowers",
                    49.99,
                    "https://images.unsplash.com/photo-1444021465936-c6ca81d39b84",
                    6, 15, 4.4f,
                    "Natural",
                    "Everyday"));

            sampleFlowers.add(new Flower(0,
                    "Peony Perfection",
                    "Soft and romantic peony arrangement",
                    89.99,
                    "https://images.unsplash.com/photo-1509709354606-c32ddc2a4cf3",
                    7, 6, 4.9f,
                    "Premium",
                    "Wedding"));

            sampleFlowers.add(new Flower(0,
                    "Tropical Paradise",
                    "Exotic blend of tropical flowers and greenery",
                    75.99,
                    "https://images.unsplash.com/photo-1579783900882-c0d3dad7b119",
                    8, 8, 4.6f,
                    "Exotic",
                    "Summer"));

            for (Flower flower : sampleFlowers) {
                flowerRepository.insertFlower(flower);
            }
        }
    }
}