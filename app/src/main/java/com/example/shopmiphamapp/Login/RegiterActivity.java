package com.example.shopmiphamapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegiterActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword, edtName, edtPhoneNumber, edtConfirmPassword, edtAddress;
    private Button btnRegiter;
    private RadioGroup rdoGroup;
    private RadioButton rdoMale, rdoFemale;
    private int gender = 0;

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);

        initUi();

        btnRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRegiter();
            }
        });
    }

    private void initUi() {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        edtName = findViewById(R.id.name);
        edtPhoneNumber = findViewById(R.id.phone_number);
        rdoGroup = findViewById(R.id.rdo_group);
        rdoMale = findViewById(R.id.rdo_male);
        rdoFemale = findViewById(R.id.rdo_female);
        btnRegiter = (Button) findViewById(R.id.btnRegiter);
        edtConfirmPassword = findViewById(R.id.confirm_password);
        edtAddress = findViewById(R.id.address);
    }

    private void onClickRegiter() {
        String email = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String name = edtName.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        boolean checkUser = validateUser(email, password, name, phoneNumber, confirmPassword, address);
        if (!checkUser) return;

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
//                            FirebaseUser user = mAuth.getCurrentUser();
                            addUser(email, password, name, phoneNumber, address);
                            Intent intent = new Intent(RegiterActivity.this, LoginActivity.class);
                            startActivity(intent);

                            // Đóng tất cả các Activity trước nó
                            finishAffinity();
                        } else {
                            Toast.makeText(RegiterActivity.this, "Đăng ký thất bại.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addUser(String username, String password, String name, String phoneNumber, String address) {
        rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rdo_male) {
                    gender = 0;
                } else if (checkedId == R.id.rdo_female) {
                    gender = 1;
                }
            }
        });

        User user = new User(username, password, R.drawable.img_avatar, gender, name, phoneNumber, address);
        ShopDatabase.getInstance(this).userDAO().insertUser(user);
//        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_LONG).show();
    }

    private boolean validateUser(String email, String password, String name, String phoneNumber, String confirmPassword, String address) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(name) || TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "Vui lòng nhâp đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "Vui lòng nhâp mật khẩu lớn hơn 6 ký tự.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (phoneNumber.length() != 10) {
            Toast.makeText(this, "Vui lòng nhâp đúng định dạng số điện thoại 10 số.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Nhập lại mật khẩu không đúng.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}