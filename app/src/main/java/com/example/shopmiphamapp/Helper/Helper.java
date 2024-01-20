package com.example.shopmiphamapp.Helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.shopmiphamapp.Database.ShopDatabase;
import com.example.shopmiphamapp.Login.LoginActivity;
import com.example.shopmiphamapp.R;
import com.example.shopmiphamapp.User.ChangePasswordActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {
    public static String formatPrice(int price) {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        return decimalFormat.format(price) + "đ";
    }

    public static String formatDate(long dateLong) {
        Date date = new Date(dateLong);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateFormat.format(date);
    }

    public static String formatSold(int sold) {
        String sSold;
        if (sold >= 1000) {
            sold /= 1000;
            DecimalFormat decimalFormat = new DecimalFormat("#.#"); // Định dạng số sau dấu phẩy
            sSold = decimalFormat.format(sold) + "k";
        } else {
            sSold = String.valueOf((int) sold);
        }
        return "Đã bán " + sSold + " sp.";
    }

    public static String formatString(String s, int length) {
        if (s.length() <= length) {
            return s;  // Trả về chuỗi gốc nếu độ dài của nó nhỏ hơn hoặc bằng length
        } else {
            String substring = s.substring(0, length);  // Cắt chuỗi đến vị trí được chỉ định
            int lastSpaceIndex = substring.lastIndexOf(" ");  // Tìm vị trí của dấu cách cuối cùng trong chuỗi cắt
            if (lastSpaceIndex != -1) {
                substring = substring.substring(0, lastSpaceIndex);  // Loại bỏ từ nằm giữa nếu có dấu cách
            }
            return substring + "...";  // Thêm "..." vào cuối chuỗi cắt
        }
    }


    public static void loadImage(String urlImage, ImageView imageView, ProgressBar progressBar) {
        Picasso.get()
                .load(urlImage)
                .placeholder(R.drawable.layout_none) // Ảnh placeholder hiển thị trong quá trình tải
                .error(R.drawable.layout_none) // Ảnh hiển thị khi có lỗi xảy ra
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Quá trình tải ảnh thành công, ẩn ProgressBar và hiển thị ImageView
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
                        progressBar.setVisibility(View.GONE);
//                        throw new RuntimeException(e);
                    }
                });
    }

    public static void loadImageUri(Uri uriImage, ImageView imageView, ProgressBar progressBar) {
        Picasso.get()
                .load(uriImage)
                .placeholder(R.drawable.layout_none) // Ảnh placeholder hiển thị trong quá trình tải
                .error(R.drawable.layout_none) // Ảnh hiển thị khi có lỗi xảy ra
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        // Quá trình tải ảnh thành công, ẩn ProgressBar và hiển thị ImageView
                        progressBar.setVisibility(View.GONE);
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        // Xử lý khi có lỗi xảy ra trong quá trình tải ảnh
                        progressBar.setVisibility(View.GONE);
//                        throw new RuntimeException(e);
                    }
                });
    }

    public static void handleLoading(boolean loading, FrameLayout progressLayout, ProgressBar progressBar) {
        if (loading) {
            // Hiển thị màn hình xám với ProgressBar
            progressLayout.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            // Ẩn màn hình xám
            progressLayout.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        String regex = "(03|05|07|08|09|01[2689])([0-9]{8})\\b";
        if (phoneNumber.length() < 0 || phoneNumber.length() > 10) return false;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        String regex = "([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
