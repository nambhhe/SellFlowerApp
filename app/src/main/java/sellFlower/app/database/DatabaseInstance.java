package sellFlower.app.database;

import android.content.Context;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseInstance {
    private static volatile SellFlowerDatabase instance;

    public static SellFlowerDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (SellFlowerDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    SellFlowerDatabase.class, "SellFlower_DB")
                            .allowMainThreadQueries()
                            .addMigrations(MIGRATION_1_2)
                            .fallbackToDestructiveMigration() // Add this line for development
                            .build();
                }
            }
        }
        return instance;
    }

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Drop the existing table if it exists
            database.execSQL("DROP TABLE IF EXISTS user");

            // Create the table with all required columns
            database.execSQL("CREATE TABLE IF NOT EXISTS user (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    "name TEXT, " +
                    "email TEXT, " +
                    "password TEXT NOT NULL DEFAULT '')");
        }
    };
}