//package sellFlower.app.controller.activity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import sellFlower.app.database.DatabaseInstance;
//import sellFlower.app.dao.UserDao;
//import sellFlower.app.model.User;
//import sellFlower.app.databinding.ActivitySignUpBinding;
//
//public class SignUpActivity extends AppCompatActivity {
//
//    private ActivitySignUpBinding binding;
//    private UserDao userDao;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        userDao = DatabaseInstance.getInstance(this).userDao();
//
//        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = binding.nameInput.getText().toString();
//                String email = binding.emailInput.getText().toString();
//                String password = binding.passwordInput.getText().toString();
//
//                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
//                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                User newUser = new User(name, email, password);
//                userDao.insertUser(newUser);
//
//                Toast.makeText(SignUpActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//    }
//}