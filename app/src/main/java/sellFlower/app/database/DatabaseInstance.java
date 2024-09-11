package sellFlower.app.database;

import android.content.Context;

import androidx.room.Room;

public class DatabaseInstance {

    private static volatile SellFlowerDatabase instance;

    // Singleton pattern to ensure only one instance of the database
    public static SellFlowerDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (SellFlowerDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            SellFlowerDatabase.class, "SellFlower_DB")
                            .allowMainThreadQueries().build();
                }
            }
        }

        return instance;
    }
}
