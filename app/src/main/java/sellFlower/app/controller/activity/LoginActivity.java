package sellFlower.app.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import sellFlower.app.databinding.ActivityLoginBinding;
import sellFlower.app.model.User;
import sellFlower.app.repository.UserRepository;
import sellFlower.app.utils.ValidationUtils;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = UserRepository.getInstance(this);
        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.signInBtn.setOnClickListener(v -> attemptLogin());

        binding.signUpText.setOnClickListener(v ->
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class))
        );

        binding.forgotPasswordText.setOnClickListener(v ->
                Snackbar.make(binding.getRoot(), "Forgot password feature coming soon!",
                        Snackbar.LENGTH_SHORT).show()
        );

        binding.facebookSignInBtn.setOnClickListener(v ->
                Snackbar.make(binding.getRoot(), "Facebook login coming soon!",
                        Snackbar.LENGTH_SHORT).show()
        );

        binding.emailSignInBtn.setOnClickListener(v ->
                Snackbar.make(binding.getRoot(), "Google login coming soon!",
                        Snackbar.LENGTH_SHORT).show()
        );
    }

    private void attemptLogin() {
        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();

        // Reset errors
        binding.emailInput.setError(null);
        binding.passwordInput.setError(null);

        // Validate input
        if (!ValidationUtils.isValidEmail(email)) {
            binding.emailInput.setError("Please enter a valid email address");
            binding.emailInput.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            binding.passwordInput.setError("Password cannot be empty");
            binding.passwordInput.requestFocus();
            return;
        }

        // Show loading state
        setLoadingState(true);

        userRepository.loginUser(email, password, new UserRepository.DatabaseCallback<User>() {
            @Override
            public void onSuccess(User user) {
                setLoadingState(false);
                // TODO: Save user session
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }

            @Override
            public void onError(String error) {
                setLoadingState(false);
                Snackbar.make(binding.getRoot(), error, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void setLoadingState(boolean isLoading) {
        binding.signInBtn.setEnabled(!isLoading);
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }
}