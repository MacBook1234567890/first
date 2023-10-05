package com.example.first;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ForgotPasswordActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText edtPhone, edtCode;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        edtCode = (EditText) findViewById(R.id.edtOTPAuth);
        edtPhone = (EditText) findViewById(R.id.edtPhoneNumberAuth);
        Button btnGetOTP = (Button) findViewById(R.id.btnGetOTP);
        btnLogin = (Button) findViewById(R.id.btnLoginPhoneAuth);
        btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = edtPhone.getText().toString().trim();
                if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
                    edtPhone.setError("please enter valid phone");
                } else {
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+92" + phoneNumber, 60, TimeUnit.SECONDS, ForgotPasswordActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                    signInUser(phoneAuthCredential);
                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    edtCode.setError("Verification Failed!!");
                                }

                                @Override
                                public void onCodeSent(@NonNull final String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    super.onCodeSent(verificationId, forceResendingToken);

                                    btnLogin.setOnClickListener(v -> {
                                        String verificationCode = edtCode.getText().toString();
                                        if (verificationId.isEmpty()) return;
                                        //create a credential
                                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, verificationCode);
                                        signInUser(credential);
                                    });
                                }
                            }
                    );
                }
            }
        });
    }

    private void signInUser(PhoneAuthCredential phoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener((OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(ForgotPasswordActivity.this, MainActivity.class));
                        finish();
                    } else {
                         Log.d(TAG, "onComplete:"+ Objects.requireNonNull(task.getException()).getLocalizedMessage());
                    }
                });
    }
}

