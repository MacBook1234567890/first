package com.example.first;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class SplashActivity extends Activity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the window to be black and fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setBackgroundDrawableResource(android.R.color.black);

        // Create a text view and set its text to "Facefall"
        textView = new TextView(this);
        textView.setText("Facefall");
        textView.setGravity(Gravity.CENTER);

        // Add the text view to the layout
        setContentView(textView);

        // Create a handler to display the popup screen for 10 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);

                // Finish the popup activity
                finish();
            }
        }, 1000); // 1000 milliseconds = 1 seconds
    }
}
