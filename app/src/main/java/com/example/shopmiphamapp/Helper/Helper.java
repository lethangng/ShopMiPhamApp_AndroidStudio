package com.example.shopmiphamapp.Helper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
