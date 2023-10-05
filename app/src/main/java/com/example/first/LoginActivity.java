package com.example.first;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText cnicEditText, passwordEditText;
    Button btnLogin;
    DatabaseHelper myDB;
    public  static final String SHARED_PREFS="sharedPrefs";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cnicEditText = findViewById(R.id.login_cnic);
        passwordEditText = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_button);
        CheckBox rememberMeCheckBox = findViewById(R.id.checkbox);
        TextView signupRedirectText = findViewById(R.id.signupRedirectText);
        Button forgotPasswordTxt = findViewById(R.id.forgotPasswordText);
        myDB = new DatabaseHelper(this);

        checkBox();
        btnLogin.setOnClickListener(v -> {
            // Get user input
            String cnic = cnicEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString();

            // Validate user input (add your specific validation logic)
            if (cnic.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in CNIC field", Toast.LENGTH_SHORT).show();

            } else if (password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in Password field", Toast.LENGTH_SHORT).show();
            } else {
                // Replace the following with your authentication logic and database check
                boolean isAuthenticated = authenticateUser(cnic, password);

                if (isAuthenticated) {
                    // Redirect to the main activity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Close the login activity
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                }
            }
        });


        forgotPasswordTxt.setOnClickListener(v -> {
            // Implement logic for password recovery, if needed
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            // You can show a dialog or navigate to a password recovery activity
            //return null;

        });
        signupRedirectText.setOnClickListener(v -> {
            // Redirect to the signup activity
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
            //return null;

        });
    }

    private void checkBox() {
        SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        String check=sharedPreferences.getString("name","");
        if (check.equals("true")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Close the login activity

        }
    }

    // Implement your authentication logic here
    // Replace this with your actual authentication logic and database check
    private  boolean authenticateUser(String cnic, String password) {
        // Replace this with your authentication logic
        // Check if the CNIC and password match your database records
        // Return true if authentication is successful, otherwise return false
        boolean userExists = myDB.checkUser(cnic, password);
        if (!userExists) {
            return false;
        }
        DatabaseHelper.User user = myDB.getUser(cnic, password);

        // Example (replace with your actual database query):
        if (!(user.getPassword().equals(password)) && !(user.getCnic().equals(cnic))) {
            return false;
        }

        // If we reach here, then the authentication is successful
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", "true");
        editor.apply();
        return myDB.checkcnicPassword(cnic, password);

        // For a basic example, always return true

    }



}
