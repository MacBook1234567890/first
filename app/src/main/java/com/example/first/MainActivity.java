package com.example.first;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button LogOut;
    public  static final String SHARED_PREFS="sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogOut=findViewById(R.id.logOut);
        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("name","");
                editor.apply();
                Intent intent=new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}