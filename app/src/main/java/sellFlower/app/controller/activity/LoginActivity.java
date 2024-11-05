package sellFlower.app.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import sellFlower.app.database.DatabaseInstance;
import sellFlower.app.dao.UserDao;
import sellFlower.app.model.User;
import sellFlower.app.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userDao = DatabaseInstance.getInstance(this).userDao();

        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailInput.getText().toString();
                String password = binding.passwordInput.getText().toString();

                if (verifyUser(email, password)) {
                    // Login successful
                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });
//
//        binding.signUpText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
//            }
//        });

        binding.forgotPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement forgot password functionality
                Toast.makeText(LoginActivity.this, "Forgot password clicked", Toast.LENGTH_SHORT).show();
            }
        });

        binding.facebookSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement Facebook sign-in
                Toast.makeText(LoginActivity.this, "Facebook sign-in clicked", Toast.LENGTH_SHORT).show();
            }
        });

        binding.emailSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement email sign-in (if different from regular sign-in)
                Toast.makeText(LoginActivity.this, "Email sign-in clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean verifyUser(String email, String password) {
        User user = userDao.getUserByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}