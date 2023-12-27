package com.example.shopmiphamapp.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class InfoUserActivity extends AppCompatActivity {

    private TextView username, name, phone_number, address, gender, btnResetPassword;
    private RoundedImageView imageUser;

    private ProgressBar progressBar;

    private Toolbar toolbar;

    private ImageButton btnUpdateInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

//        String userJson = getIntent().getStringExtra("user");
//        User user = new Gson().fromJson(userJson, User.class);
        FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();
//        Uri uriImageUser = userFirebase.getPhotoUrl();

        User user = HomeActivity.userPublic;

        initUi();
        backNavigation();
        setUi(user, userFirebase);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoUserActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InfoUserActivity.this, UpdateInfoActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initUi() {
        username = findViewById(R.id.username);
        name = findViewById(R.id.name);
        phone_number = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        gender = findViewById(R.id.gender);
        toolbar = findViewById(R.id.back_navigation);
        imageUser = findViewById(R.id.imageUser);
        progressBar = findViewById(R.id.progressBar);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        btnUpdateInfo = findViewById(R.id.btn_update_info);
    }

    private void setUi(User user, FirebaseUser userFirebase) {
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

        if (userFirebase.getPhotoUrl() == null) {
            progressBar.setVisibility(View.GONE);
            imageUser.setImageResource(R.drawable.img_avatar);
        } else {
            Uri uriImageUser = userFirebase.getPhotoUrl();
            Picasso.get()
                    .load(uriImageUser)
                    .placeholder(R.drawable.img_avatar) // Ảnh placeholder hiển thị trong quá trình tải
                    .error(R.drawable.img_avatar) // Ảnh hiển thị khi có lỗi xảy ra
                    .into(imageUser, new Callback() {
                        @Override
                        public void onSuccess() {
                            // Quá trình tải ảnh thành công, ẩn ProgressBar và hiển thị ImageView
                            progressBar.setVisibility(View.GONE);
                            imageUser.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                            // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
                            progressBar.setVisibility(View.GONE);
                            throw new RuntimeException(e);
                        }
                    });
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
                HomeActivity.navigationView.setCheckedItem(R.id.nav_home);
            }
        });
    }
}