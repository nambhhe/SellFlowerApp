package sellFlower.app.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import sellFlower.app.R;
import sellFlower.app.dao.UserDao;
import sellFlower.app.database.DatabaseInstance;
import sellFlower.app.database.SellFlowerDatabase;
import sellFlower.app.model.User;

public class MainActivity extends AppCompatActivity {

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the database instance and DAOs
        SellFlowerDatabase db = DatabaseInstance.getInstance(this);
        userDao = db.userDao();

    }


}