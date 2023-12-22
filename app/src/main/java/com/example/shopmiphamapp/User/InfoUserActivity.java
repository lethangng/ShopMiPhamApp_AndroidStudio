package com.example.shopmiphamapp.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.R;
import com.google.gson.Gson;

public class InfoUserActivity extends AppCompatActivity {

    private TextView username, name, phone_number, address, gender;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

//        String userJson = getIntent().getStringExtra("user");
//        User user = new Gson().fromJson(userJson, User.class);

        User user = HomeActivity.userPublic;

        initUi();
        backNavigation();
        setUi(user);
    }

    private void initUi() {
        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        phone_number = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        toolbar = findViewById(R.id.back_navigation);
    }

    private void setUi(User user) {
        username.setText(user.getUsername());
        name.setText(user.getName());
        phone_number.setText(user.getPhoneNumber());
        address.setText(user.getAddress());
        int gender = user.getGender();
        if (gender == 1) {
            this.gender.setText("Nữ");
        } else {
            this.gender.setText("Nam");
        }
    }

    private void backNavigation() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}