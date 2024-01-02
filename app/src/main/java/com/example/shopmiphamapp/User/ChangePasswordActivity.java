package com.example.shopmiphamapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.appcompat.widget.Toolbar;

import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.Login.LoginActivity;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText password, confirmPassword;
    private TextView btnLogin;
    private Button btnChangePassword;
    private ProgressBar progressBar;
    private ImageButton btn_eye_confirmPassword, btn_eye_password;
    private Toolbar toolbar;

    private boolean showPassword = false;
    private boolean showConfirmPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        initUi();
        backNavigation();

        initListener();
    }

    private void initUi() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        toolbar = findViewById(R.id.back_navigation);
        btn_eye_confirmPassword = findViewById(R.id.btn_eye_confirmPassword);
        btn_eye_password = findViewById(R.id.btn_eye_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickChangePassword();
            }
        });

        btn_eye_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassword = !showPassword;
                if (showPassword) {
                    btn_eye_password.setImageResource(R.drawable.ic_eye);
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    btn_eye_password.setImageResource(R.drawable.ic_eye2);
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_eye_confirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmPassword = !showConfirmPassword;
                if (showConfirmPassword) {
                    btn_eye_confirmPassword.setImageResource(R.drawable.ic_eye);
                    confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    btn_eye_confirmPassword.setImageResource(R.drawable.ic_eye2);
                    confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
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

    private void onClickChangePassword() {
        String passwordValue = password.getText().toString().trim();
        String confirmPasswordValue = confirmPassword.getText().toString().trim();

        if (!validatePassword(passwordValue, confirmPasswordValue)) {
            return;
        }

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newPassword = passwordValue;

        progressBar.setVisibility(View.VISIBLE);
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(ChangePasswordActivity.this, "Thay đổi mật khẩu thành công.", Toast.LENGTH_SHORT).show();
                        } else {
                            Intent intent = new Intent(ChangePasswordActivity.this, ReAuthenticateUserActivity.class);
                            startActivity(intent);
                            Toast.makeText(ChangePasswordActivity.this, "Vui lòng xác thực người dùng.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validatePassword(String password, String confirmPassword) {
        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng nhâp đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu lớn hơn 6 ký tự .", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Nhập lại mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}