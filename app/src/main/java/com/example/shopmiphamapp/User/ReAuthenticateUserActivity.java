package com.example.shopmiphamapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ReAuthenticateUserActivity extends AppCompatActivity {
    private EditText username, password;
    private ProgressBar progressBar;

    private Button btnAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_authenticate_user);

        initUi();
        initListener();
    }

    private void initUi() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btnAuth = findViewById(R.id.btnAuth);
    }

    private void initListener() {
        btnAuth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickAuthUser();
            }
        });
    }

    private void clickAuthUser() {
        String usernameValue = username.getText().toString().trim();
        String passwordValue = password.getText().toString().trim();

        if (!validate(usernameValue, passwordValue)) {
            return;
        }
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider
                .getCredential(usernameValue, passwordValue);

        progressBar.setVisibility(View.VISIBLE);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(ReAuthenticateUserActivity.this, ChangePasswordActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ReAuthenticateUserActivity.this, "Tài khoản hoặc mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean validate(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Vui lòng nhập lớn hơn 6 ký tự.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}