package com.example.shopmiphamapp.Login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
    private TextView tvRegiter, tv_forgetPassword;

    private ImageButton ibtn_eye;
    boolean show = false;
    int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUi();
        initListener();

        // Hỏi xem có cho phép quyền truy cập bo nhớ không
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);}

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


        tv_forgetPassword = findViewById(R.id.tv_forgetPassword);
    }

    private void initListener() {
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

        tv_forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
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
    }


    private boolean validateUser(String email, String password) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhâp đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}