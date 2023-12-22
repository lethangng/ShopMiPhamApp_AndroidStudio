package com.example.shopmiphamapp.Login;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvRegiter;

    private ImageButton ibtn_eye;
    boolean show = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();

        ibtn_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show = !show;
                if (show) {
                    ibtn_eye.setImageResource(R.drawable.ic_eye);
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    ibtn_eye.setImageResource(R.drawable.ic_eye2);
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();

            }
        });

        tvRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegiterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUi() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        mAuth = FirebaseAuth.getInstance();

        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegiter = findViewById(R.id.tv_regiter);
        ibtn_eye = findViewById(R.id.ibtn_eye);
    }
    private void login() {
        String email = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        boolean checkUser = validateUser(email, password);
        if (!checkUser) return;

        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Đăng nhập thất bại.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


//        User user = checkLogin(username, password);
//        String userJson = new Gson().toJson(user);
//
//        if (user != null) {
//            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            intent.putExtra("user", userJson);
//            startActivity(intent);
//        } else {
//            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_LONG).show();
//        }
    }


//    private User checkLogin(String username, String password) {
//        User user = ShopDatabase.getInstance(this).userDAO().checkUser(username);
//        if (user != null) {
//            if (password.equals(user.getPassword())) {
//                return user;
//            }
//        }
//        return null;
//    }

    private boolean validateUser(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhâp đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}