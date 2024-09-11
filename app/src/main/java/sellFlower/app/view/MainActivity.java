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

        // Example operations
        insertData();
        queryData();
    }
    private void insertData() {
        // Insert a new user
        User newUser = new User("John Doe", "john@example.com");
        userDao.insertUser(newUser);

    }

    private void queryData() {
        // Query all users
        ArrayList<User> users = userDao.getAllUsers();
        for (User user : users) {
            Log.d("User", "ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        }

    }

}