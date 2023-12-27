package com.example.shopmiphamapp;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Note {
    //        FrameLayout progressLayout = findViewById(R.id.progress_layout);
//
//        // Hiển thị màn hình xám với ProgressBar
//        progressLayout.setVisibility(View.VISIBLE);
//
//        shopDatabase.isDatabaseInitialized().observe(this, new Observer<Boolean>() {
//            @Override
//            public void onChanged(Boolean isInitialized) {
//                if (isInitialized) {
//                    // Update lại count cart
//                    updateCountCart();
//
//                    navigate();
//
//                    category();
//                    recyclerViewProduct();
//                    carousel();
//
//                    // Xử lý khi số lượng cart thay đổi thì update lại count cart bên ngoài HomeActivity
//                    handleUpdateCart();
//
//                    // Ẩn màn hình xám
//                    progressLayout.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.GONE);
//                }
//            }
//        });




//    public interface OnImagesLoadedListener {
//        void onImagesLoaded(List<String> imageUrlList);
//    }
//
//    public static void getImagesFromFirebaseStorage(String folderName,
//                                                    final OnImagesLoadedListener listener) {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference().child(folderName);
//
//        // Lấy danh sách các ảnh trong thư mục
//        storageRef.listAll()
//                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
//                    @Override
//                    public void onSuccess(ListResult listResult) {
//                        List<String> imageUrlList = new ArrayList<>();
//
//                        for (StorageReference item : listResult.getItems()) {
//                            item.getDownloadUrl()
//                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            String imageUrl = uri.toString();
////                                            Log.d("imageUrl", imageUrl);
//                                            imageUrlList.add(imageUrl);
//                                            // Kiểm tra xem đã lấy đủ URL của tất cả ảnh hay chưa
//                                            if (imageUrlList.size() == listResult.getItems().size()) {
////                                                listImgURL = new ArrayList<>(imageUrlList);
//                                                listener.onImagesLoaded(imageUrlList);
//                                            }
//                                        }
//                                    })
//                                    .addOnFailureListener(new OnFailureListener() {
//                                        @Override
//                                        public void onFailure(@NonNull Exception e) {
//                                            // Xử lý khi có lỗi xảy ra trong quá trình lấy URL của ảnh
//                                            Log.d("errorImageURL", e.toString());
//                                        }
//                                    });
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Xử lý khi có lỗi xảy ra trong quá trình lấy danh sách ảnh
//                        Log.d("errorListImages", e.toString());
//                    }
//                });
//    }
}
