package com.example.shopmiphamapp.Helper;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.shopmiphamapp.Database.User.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kotlin.jvm.Throws;

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

}
