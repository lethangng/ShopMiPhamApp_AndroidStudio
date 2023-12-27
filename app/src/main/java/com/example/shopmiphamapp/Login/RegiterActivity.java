package com.example.shopmiphamapp.Login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegiterActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword, edtName, edtPhoneNumber, edtConfirmPassword, edtAddress;
    private Button btnRegiter;

    private ImageButton btn_eye_confirmPassword, btn_eye_password;
    private RadioGroup rdoGroup;
    private RadioButton rdoMale, rdoFemale;

    private TextView tv_login;
    private int gender = 0;

    private boolean showPassword = false;
    private boolean showConfirmPassword = false;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regiter);

        initUi();

        initListener();
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

        btn_eye_confirmPassword = findViewById(R.id.btn_eye_confirmPassword);
        btn_eye_password = findViewById(R.id.btn_eye_password);
        tv_login = findViewById(R.id.tv_login);
    }

    private void initListener() {
        btnRegiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRegiter();
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegiterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btn_eye_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassword = !showPassword;
                if (showPassword) {
                    btn_eye_password.setImageResource(R.drawable.ic_eye);
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    btn_eye_password.setImageResource(R.drawable.ic_eye2);
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_eye_confirmPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmPassword = !showConfirmPassword;
                if (showConfirmPassword) {
                    btn_eye_confirmPassword.setImageResource(R.drawable.ic_eye);
                    edtConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    btn_eye_confirmPassword.setImageResource(R.drawable.ic_eye2);
                    edtConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
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

        User user = new User(username, password,
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/img_avatar.png?alt=media&token=a18fb5b2-37dc-47af-8a6d-861ece6ca6a1"
                , gender, name, phoneNumber, address);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("user").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(RegiterActivity.this, "Đăng ký thành công.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi thêm thất bại
                        throw new RuntimeException(e.toString());
                    }
                });;

//        ShopDatabase.getInstance(this).userDAO().insertUser(user);
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