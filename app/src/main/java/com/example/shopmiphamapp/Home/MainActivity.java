package com.example.shopmiphamapp.Home;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.shopmiphamapp.Login.LoginActivity;
import com.example.shopmiphamapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 2000);
    }
    private void nextActivity() {
        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            // Chua login
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        } else {
            // Da login
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }
        finish();
    }
}