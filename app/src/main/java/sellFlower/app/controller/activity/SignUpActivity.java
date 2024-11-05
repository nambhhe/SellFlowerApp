package sellFlower.app.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import sellFlower.app.databinding.ActivitySignUpBinding;
import sellFlower.app.model.User;
import sellFlower.app.repository.UserRepository;
import sellFlower.app.utils.ValidationUtils;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userRepository = UserRepository.getInstance(this);
        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.signUpBtn.setOnClickListener(v -> attemptSignUp());

        binding.loginText.setOnClickListener(v ->
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class))
        );
    }

    private void attemptSignUp() {
        String name = binding.nameInput.getText().toString().trim();
        String email = binding.emailInput.getText().toString().trim();
        String password = binding.passwordInput.getText().toString().trim();
        String confirmPassword = binding.confirmPasswordInput.getText().toString().trim();

        // Reset errors
        binding.nameInput.setError(null);
        binding.emailInput.setError(null);
        binding.passwordInput.setError(null);
        binding.confirmPasswordInput.setError(null);

        // Validate input
        if (!ValidationUtils.isValidName(name)) {
            binding.nameInput.setError("Please enter a valid name");
            binding.nameInput.requestFocus();
            return;
        }

        if (!ValidationUtils.isValidEmail(email)) {
            binding.emailInput.setError("Please enter a valid email address");
            binding.emailInput.requestFocus();
            return;
        }

        if (!ValidationUtils.isValidPassword(password)) {
            binding.passwordInput.setError(ValidationUtils.getPasswordErrorMessage());
            binding.passwordInput.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            binding.confirmPasswordInput.setError("Passwords do not match");
            binding.confirmPasswordInput.requestFocus();
            return;
        }

        // Show loading state
        setLoadingState(true);

        User user = new User(name, email, password);
        userRepository.registerUser(user, new UserRepository.DatabaseCallback<Void>() {
            @Override
            public void onSuccess(Void result) {
                setLoadingState(false);
                Toast.makeText(SignUpActivity.this, "Sign up successful!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
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
        binding.signUpBtn.setEnabled(!isLoading);
        binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

}