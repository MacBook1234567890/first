package com.example.first;

import android.app.ActivityOptions;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Pair;
import android.view.View;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Objects;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    private EditText nameEditText, cnicEditText, emailEditText, phoneEditText, passwordEditText;
    DatabaseHelper myDB;
    private ImageView eyeIcon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        nameEditText = findViewById(R.id.signup_name);
        cnicEditText = findViewById(R.id.signup_cnic);
        emailEditText = findViewById(R.id.signup_email);
        phoneEditText = findViewById(R.id.signup_phone);
        passwordEditText = findViewById(R.id.signup_password);
       Button signupButton = findViewById(R.id.signup_button);
        TextView loginRedirectText = findViewById(R.id.loginRedirectText);
        myDB = new DatabaseHelper(this);
        signupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Get user input
                String name = nameEditText.getText().toString().trim();
                String cnic = cnicEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String phone = phoneEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString();



                // Validate user input
                if (name.isEmpty() || cnic.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!isValidName(name)) {
                    Toast.makeText(SignupActivity.this, "Invalid Name format", Toast.LENGTH_SHORT).show();
                } else if (!isValidCNIC(cnic)) {
                    Toast.makeText(SignupActivity.this, "Invalid CNIC format", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(phone)) {
                    Toast.makeText(SignupActivity.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(SignupActivity.this, "Invalid Email format", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(password)) {
                    Toast.makeText(SignupActivity.this, "Invalid Password format", Toast.LENGTH_SHORT).show();
                } else if (isUserExists(cnic)) {
                    Toast.makeText(SignupActivity.this, "User with the same CNIC already exists. Please sign up with another CNIC.", Toast.LENGTH_SHORT).show();
                } else {
                    // Store user information in the database
                    saveUserToDatabase(name, cnic, email, phone, password);

                    // Redirect to the login page (you  need to implement LoginActivity)
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                }

            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Redirect to the login page (you need to implement LoginActivity)
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    // Validate the name format
    private boolean isValidName(String name) {
        return name.matches("[A-Za-z ]+");
    }

    // Validate CNIC format
    private boolean isValidCNIC(String cnic) {
        // Remove any existing dashes and spaces
        cnic = cnic.replaceAll("[\\s-]+", "");

        // Check if the CNIC is valid (13 digits)
        return cnic.length() == 13 && Pattern.matches("[0-9]+", cnic);
    }
    private boolean isValidPhoneNumber(String phoneNumber) {
        // Remove any spaces or hyphens from the phone number
        phoneNumber = phoneNumber.replaceAll("[\\s-]+", "");

        // Check if the phone number contains only digits and is at least 9 digits long
        return (Pattern.matches("[0-9]+", phoneNumber) && phoneNumber.length() >9&&Pattern.matches("[0-9]+", phoneNumber) && phoneNumber.length() <12);
    }

    // Validate phone number format


    // Validate email format
    private boolean isValidEmail(String email) {
        // Define a regular expression pattern for a basic email validation
        String emailPattern = "^[A-Za-z0-9+_.-]+@(.+)$";

        return Pattern.matches(emailPattern, email);
    }

    // Validate password format
    private boolean isValidPassword(String password) {
        // Check if the password is at least 6 characters long
        return password.length() >= 6 && Pattern.matches(".*[A-Z].*", password)
                && Pattern.matches(".*[a-z].*", password)
                && Pattern.matches(".*\\d.*", password)
                && Pattern.matches(".*[!@#\\$%^&*].*", password);
    }

    // Check if a user with the same CNIC already exists
    private boolean isUserExists(String cnic) {
        SQLiteDatabase database = myDB.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Users WHERE CNIC=?", new String[]{cnic});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        database.close();
        return exists;
    }

    // Save user information in the SQLite database
    private void saveUserToDatabase(String name, String cnic, String email, String phone, String password) {
        SQLiteDatabase database = myDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("CNIC", cnic);
        values.put("Email", email);
        values.put("Phone", phone);

        values.put("Password", password);

        long result = database.insert("Users", null, values);

        if (result != -1) {
            Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }

        database.close();
    }
}
