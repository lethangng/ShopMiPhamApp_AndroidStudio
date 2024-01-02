package com.example.shopmiphamapp.Database;

import androidx.annotation.NonNull;

import com.example.shopmiphamapp.Database.Carousel.Carousel;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ProductType.ProductType;
import com.example.shopmiphamapp.Database.User.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class InitDatabaseFirebase {
    public static void initDatabaseProduct() {
        String[] imgUrl = {
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_1.png?alt=media&token=f633b0a6-4435-42ea-a875-ea43a4fc21e1",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_2.png?alt=media&token=ed63b8f9-2167-4061-82eb-5d88af506ec2",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_3.png?alt=media&token=764d5d7e-4222-48ad-97d1-afed83b6a0da",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_4.png?alt=media&token=4a8d8cf0-5ff4-4a6b-809a-a258632bb4e5",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_5.png?alt=media&token=c7d02534-da2f-485f-9533-ce6ed50963e5",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_6.png?alt=media&token=ae99a2af-79c4-4d9c-8f86-b88ffb36c9bb"
        };

        String[] name = {
                "Product 1",
                "Product 2",
                "Product 3",
                "Product 4",
                "Product 5",
                "Product 6"
        };
        String description = "Nước Hoa Shimang Bản Cao Cấp Eau De Cologne EDP 50M<br><br>Thương hiệu: Shimang<br>- " +
                "Xuất xứ: Nội Địa Trung Quốc<br>- Tên và địa chỉ của cá nhân chịu trách nhiệm về hàng hóa:<br>" +
                "Sản phẩm được phân phối bởi: Chustore2017<br>Địa chỉ: Tòa A3 chung cư An Bình City - 232 Phạm văn Đồng, Bắc Từ Liêm, Hà Nội<br><br>" +
                "Dung tích: 50ML<br>Quy cách đóng gói: Full Box<br>Đối tượng sử dụng: Phù hợp với cả nam và nữ<br><br>" +
                "E này đc khen là giữ hương 6-8 tiếng luôn đó. Mùi dịu dàng ngọt ngào chứ k hề hắc ạ.<br>Có cả mùi hoa quả. Mùi mang tính ấm phù hợp mùa thu và đông.<br><br>" +
                "Phân loại mùi hương:<br>- Scent of tea: Hương thơm trà không ngọt dành cho nam và nữ. Hương đầu là mùi trà hoa cam.<br>" +
                "Hương giữa là bạch đậu khấu.<br>Hương cuối là mùi hoa nhài và gỗ mun";
        int[] price = {10000, 20000, 25000, 30000, 40000, 60000};
        int[] sold = {1000, 600, 100, 1200, 500, 400};
        int productTypeId[] = {1, 2, 1, 3, 1, 0};
        int[] quantity = {1000, 200, 600, 800, 400, 100};

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for (int i = 0; i < name.length; i++) {
            Product product = new Product(i, name[i], description, imgUrl[i],
                     price[i], sold[i], productTypeId[i], quantity[i]);

            db.collection("product").add(product).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý khi thêm thất bại
                            throw new RuntimeException(e.toString());
                        }
                    });;
        }
    }

    public static void initDatabaseProductType() {
        String[] name = {
                "Nước hoa hồng",
                "Sữa rửa mặt",
                "Kem chống nắng",
                "Tẩy trang"
        };
        String[] imgUrl = {
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/productType%2Fproduct_type_1.png?alt=media&token=6c3682b8-19de-432a-8de2-b171f7420182",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/productType%2Fproduct_type_2.png?alt=media&token=c229619a-1bbe-468a-92b8-ddd19fa866d0",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/productType%2Fproduct_type_3.png?alt=media&token=ca139627-808f-404d-9282-efc83396ed0c",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/productType%2Fproduct_type_4.png?alt=media&token=7fc71f98-a9ae-4191-9ab7-1705ea04133c"
        };
        String[] description = {
                "Nước hoa hồng bình dân",
                "Sữa rửa mặt cao cấp",
                "Kem chống nắng bình dân",
                "Tẩy trang cao cấp"
        };

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for (int i = 0; i < name.length; i++) {
            ProductType productType = new ProductType(i, name[i], imgUrl[i], description[i]);

            db.collection("productType").add(productType).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý khi thêm thất bại
                            throw new RuntimeException(e.toString());
                        }
                    });;
        }
    }

    public static void initDatabaseCarousel() {
        String[] carouselImg = {
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/carousel%2Fcarousel_1.png?alt=media&token=19edb44c-0ef0-43ab-b1da-7187435c2f2e",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/carousel%2Fcarousel_2.png?alt=media&token=c1391538-d6d3-4fba-a0bb-29a0b646ff12",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/carousel%2Fcarousel_3.png?alt=media&token=f52176eb-757a-4d6e-9926-f2fc2c8fccab",
                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/carousel%2Fcarousel_4.png?alt=media&token=3e6fa2db-b418-4d57-b0e9-194e82a0a9a7"
        };

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        for (int i = 0; i < carouselImg.length; i++) {
            Carousel carousel = new Carousel(i, carouselImg[i]);
            db.collection("carousel").add(carousel).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            //
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Xử lý khi thêm thất bại
                            throw new RuntimeException(e.toString());
                        }
                    });;
        }
    }
}
