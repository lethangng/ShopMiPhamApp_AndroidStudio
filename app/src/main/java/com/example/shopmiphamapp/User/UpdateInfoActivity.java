package com.example.shopmiphamapp.User;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Home.HomeActivity;
import com.example.shopmiphamapp.Login.LoginActivity;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class UpdateInfoActivity extends AppCompatActivity {
    private EditText name, phoneNumber, address;
    private TextView btnLogin;
    private Button btnUpdate;
    private RoundedImageView imgAvatar;
    private RadioGroup rdoGroup;
    private RadioButton rdoMale, rdoFemale;
    private int gender;
    private ProgressBar progressBar, progressBarImage;

    private Toolbar toolbar;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri uriImageAvatar;

    private boolean checkUpdateImg = false;

    private User user;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        initUi();
        user = HomeActivity.userPublic;
        gender = user.getGender();

        setUi();

        backNavigation();
        initListener();
    }

    private void initUi() {
        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phone_number);
        address = findViewById(R.id.address);
        btnUpdate = findViewById(R.id.btnUpdate);
        imgAvatar = findViewById(R.id.img_avatar);
        rdoGroup = findViewById(R.id.rdo_group);
        rdoMale = findViewById(R.id.rdo_male);
        rdoFemale = findViewById(R.id.rdo_female);
        toolbar = findViewById(R.id.back_navigation);
        btnLogin = findViewById(R.id.btn_login);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        progressBarImage = findViewById(R.id.progressBarImage);
        progressBarImage.setVisibility(View.GONE);
    }

    private void setUi() {
        if (firebaseUser == null) {
            return;
        }
        name.setText(user.getName());
        phoneNumber.setText(user.getPhoneNumber());
        address.setText(user.getAddress());
        if (gender == 0) {
            rdoMale.setChecked(true);
        } else {
            rdoFemale.setChecked(true);
        }

        if (firebaseUser.getPhotoUrl() == null) {
            imgAvatar.setImageResource(R.drawable.img_avatar);
        } else {
            setImageAvatar(firebaseUser.getPhotoUrl());
        }
//        Log.d("imageUri", String.valueOf(firebaseUser.getPhotoUrl()));
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

    private void setImageAvatar(Uri urlImage) {
        Picasso.get()
                .load(urlImage)
                .placeholder(R.drawable.layout_none) // Ảnh placeholder hiển thị trong quá trình tải
                .error(R.drawable.layout_none) // Ảnh hiển thị khi có lỗi xảy ra
                .into(imgAvatar, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Quá trình tải ảnh thành công, ẩn ProgressBar và hiển thị ImageView
                        progressBarImage.setVisibility(View.GONE);
                        imgAvatar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
                        progressBarImage.setVisibility(View.GONE);
                        throw new RuntimeException(e);
                    }
                });
    }

    private void initListener() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(UpdateInfoActivity.this, LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
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
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameValue = name.getText().toString().trim();
                String phoneNumberValue = phoneNumber.getText().toString().trim();
                String addressValue = address.getText().toString().trim();
//                Log.d("phoneNumberValue", phoneNumberValue);

                boolean check = validate(nameValue, phoneNumberValue, addressValue);
                if (!check) {
                    return;
                }

                // Kiểm tra xem có chọn ảnh không, có thì mới update
                if (checkUpdateImg) {
                    uploadImage(uriImageAvatar);
                }
                updateInfo(nameValue, phoneNumberValue, addressValue, gender);
            }
        });
    }

    private boolean validate(String name, String phoneNumber, String address) {
        if (name.isEmpty() || phoneNumber.isEmpty()
                || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhâp đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (phoneNumber.length() != 10) {
            Log.d("sizePhone", String.valueOf(phoneNumber.length()));
            Toast.makeText(this, "Vui lòng nhâp đúng định dạng số điện thoại 10 số.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {

            checkUpdateImg = true;

            Uri selectedImageUri = data.getData();

            setImageAvatar(selectedImageUri);

            uriImageAvatar = selectedImageUri;
        }
    }
    private void uploadImage(Uri imageUri) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        progressBar.setVisibility(View.VISIBLE);
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(imageUri)
                .build();

        user.updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateInfoActivity.this, "Cập nhập thành công.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void updateInfo(String name, String phoneNumber, String address, int gender) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Tạo truy vấn
        Query query = db.collection("user")
                .whereEqualTo("username", firebaseUser.getEmail());

        // Thực hiện truy vấn
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot querySnapshot) {
                        // Tạo một Batch để thực hiện các thao tác cập nhật
                        WriteBatch batch = db.batch();

                        // Lặp qua các tài liệu phù hợp
                        for (QueryDocumentSnapshot document : querySnapshot) {
                            // Lấy tham chiếu đến tài liệu
                            DocumentReference docRef = document.getReference();

                            // Cập nhật trường trong tài liệu
                            batch.update(docRef, "name", name);
                            batch.update(docRef, "phoneNumber", phoneNumber);
                            batch.update(docRef, "address", address);
                            batch.update(docRef, "gender", gender);
                        }

                        // Thực hiện Batch Writes
                        batch.commit()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        // Cập nhật thành công
                                        // ...
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        // Xử lý khi cập nhật thất bại
                                        throw new RuntimeException(e.toString());
                                    }
                                });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi truy vấn thất bại
                        throw new RuntimeException(e.toString());
                    }
                });
    }
}