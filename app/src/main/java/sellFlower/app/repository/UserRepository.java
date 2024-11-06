// app/src/main/java/sellFlower/app/repository/UserRepository.java
package sellFlower.app.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sellFlower.app.database.DatabaseInstance;
import sellFlower.app.dao.UserDao;
import sellFlower.app.model.User;

public class UserRepository {
    private UserDao userDao;
    private static UserRepository instance;

    private UserRepository(Context context) {
        userDao = DatabaseInstance.getInstance(context).userDao();
    }

    public static UserRepository getInstance(Context context) {
        if (instance == null) {
            instance = new UserRepository(context);
        }
        return instance;
    }

    public interface DatabaseCallback<T> {
        void onSuccess(T result);
        void onError(String error);
    }
    public void insertSampleUsers(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        boolean sampleUsersInserted = prefs.getBoolean("SAMPLE_USERS_INSERTED", false);

        if (!sampleUsersInserted) {
            new AsyncTask<Void, Void, Boolean>() {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    List<User> sampleUsers = new ArrayList<>();
                    sampleUsers.add(new User("John Doe", "john@example.com", "password123"));
                    sampleUsers.add(new User("Jane Smith", "jane@example.com", "securepass456"));
                    sampleUsers.add(new User("Alice Johnson", "alice@example.com", "alicepass789"));

                    boolean allInserted = true;
                    for (User user : sampleUsers) {
                        try {
                            User existingUser = userDao.getUserByEmail(user.getEmail());
                            if (existingUser == null) {
                                userDao.insertUser(user);
                                Log.d("UserRepository", "Sample user inserted: " + user.getEmail());
                            } else {
                                Log.d("UserRepository", "Sample user already exists: " + user.getEmail());
                            }
                        } catch (Exception e) {
                            Log.e("UserRepository", "Error inserting sample user: " + user.getEmail(), e);
                            allInserted = false;
                        }
                    }
                    return allInserted;
                }

                @Override
                protected void onPostExecute(Boolean allInserted) {
                    if (allInserted) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("SAMPLE_USERS_INSERTED", true);
                        editor.apply();
                        Log.d("UserRepository", "All sample users inserted successfully");
                    } else {
                        Log.e("UserRepository", "Not all sample users were inserted");
                    }
                }
            }.execute();
        } else {
            Log.d("UserRepository", "Sample users were already inserted");
        }
    }
    public void registerUser(User user, DatabaseCallback<Void> callback) {
        new AsyncTask<User, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(User... users) {
                try {
                    User existingUser = userDao.getUserByEmail(users[0].getEmail());
                    if (existingUser != null) {
                        return false;
                    }
                    userDao.insertUser(users[0]);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    callback.onSuccess(null);
                } else {
                    callback.onError("Email already exists or registration failed");
                }
            }
        }.execute(user);
    }

    public void loginUser(String email, String password, DatabaseCallback<User> callback) {
        new AsyncTask<String, Void, User>() {
            @Override
            protected User doInBackground(String... params) {
                try {
                    return userDao.getUserByEmail(params[0]);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(User user) {
                if (user != null && user.getPassword().equals(password)) {
                    callback.onSuccess(user);
                } else {
                    callback.onError("Invalid email or password");
                }
            }
        }.execute(email);
    }
}