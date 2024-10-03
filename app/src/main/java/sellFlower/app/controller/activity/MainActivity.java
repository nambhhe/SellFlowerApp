package sellFlower.app.controller.activity;

import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

import sellFlower.app.R;
import sellFlower.app.controller.adapter.PopularAdapter;
import sellFlower.app.dao.FlowerDao;
import sellFlower.app.database.DatabaseInstance;
import sellFlower.app.model.Flower;
import sellFlower.app.dao.UserDao;
import sellFlower.app.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private UserDao userDao;
    private FlowerDao flowerDao;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        flowerDao = DatabaseInstance.getInstance(this).flowerDao();
        userDao = DatabaseInstance.getInstance(this).userDao();

        setStatusBarColor();
        initRecyclerView();

    }

    private void setStatusBarColor() {
        Window window = MainActivity.this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.purple_dark));
    }

    private void initRecyclerView() {
        List<Flower> itemList = new ArrayList<>(flowerDao.getAllFlower());
        itemList = flowerDao.getAllFlower();

        binding.popularView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.popularView.setAdapter(new PopularAdapter((ArrayList<Flower>) itemList));
    }
}