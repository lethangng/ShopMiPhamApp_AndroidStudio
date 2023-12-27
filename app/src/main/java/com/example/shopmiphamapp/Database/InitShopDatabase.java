package com.example.shopmiphamapp.Database;

import androidx.annotation.NonNull;

import com.example.shopmiphamapp.Database.Carousel.Carousel;
import com.example.shopmiphamapp.Database.Cart.Cart;
import com.example.shopmiphamapp.Database.FavoriteProduct.FavoriteProduct;
import com.example.shopmiphamapp.Database.ListImage.ListImage;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ProductType.ProductType;
import com.example.shopmiphamapp.Database.User.User;
import com.example.shopmiphamapp.Helper.Helper;
import com.example.shopmiphamapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class InitShopDatabase {
    ShopDatabase database;
    int actionsCompleted = 0;
    int totalNumberOfActions = 6;
    public InitShopDatabase(ShopDatabase database) {
        this.database = database;
    }

    public void loadData() {
        deleteData();

        initUserTable();
        initProductTypeTable();
        initCarouselTable();
        initProductTable();
        initCartTable();
        initFavoriteProductTable();

//        Helper.OnImagesLoadedListener imagesLoadedListenerProductType = new Helper.OnImagesLoadedListener() {
//            @Override
//            public void onImagesLoaded(List<String> imageUrlList) {
//                initProductTypeTable(imageUrlList);
//
//            }
//        };
//        Helper.getImagesFromFirebaseStorage("productType", imagesLoadedListenerProductType);
//
//        Helper.OnImagesLoadedListener imagesLoadedListenerCarousel = new Helper.OnImagesLoadedListener() {
//            @Override
//            public void onImagesLoaded(List<String> imageUrlList) {
//                initCarouselTable(imageUrlList);
//            }
//        };
//        Helper.getImagesFromFirebaseStorage("carousel", imagesLoadedListenerCarousel);
//        Helper.OnImagesLoadedListener imagesLoadedListenerProduct = new Helper.OnImagesLoadedListener() {
//            @Override
//            public void onImagesLoaded(List<String> imageUrlList) {
//                initProductTable();
//                initCartTable();
////                initListImgTable();
//                initFavoriteProductTable();
//                database.isDatabaseInitialized.setValue(true);
//            }
//        };
//        Helper.getImagesFromFirebaseStorage("product", imagesLoadedListenerProduct);
    }

    private void deleteData() {
        database.userDAO().deleteAllUsers();
        database.billDAO().deleteAllBills();
        database.carouselDAO().deleteAllCarousels();
        database.cartDAO().deleteAllCarts();
        database.favoriteProductDAO().deleteAllFavoriteProducts();
//        database.listImageDAO().deleteAllListImages();
        database.productDAO().deleteAllProducts();
        database.productBillDAO().deleteAllProductBills();
        database.productTypeDAO().deleteAllProductTypes();

        database.userDAO().resetUserId();
        database.billDAO().resetBillId();
        database.carouselDAO().resetCarouselId();
        database.cartDAO().resetCartId();
        database.favoriteProductDAO().resetFavoriteProductId();
//        database.listImageDAO().deleteAllListImages();
        database.productDAO().resetProductId();
        database.productBillDAO().resetProductBillId();
        database.productTypeDAO().resetProductTypeId();
    }


    private void initProductTypeTable() {
//        String[] nameProductType = {
//                "Nước hoa hồng",
//                "Sữa rửa mặt",
//                "Kem chống nắng",
//                "Tẩy trang"
//        };
//        String[] productTypeImgURL = {
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/productType%2Fproduct_type_1.png?alt=media&token=6c3682b8-19de-432a-8de2-b171f7420182",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/productType%2Fproduct_type_2.png?alt=media&token=c229619a-1bbe-468a-92b8-ddd19fa866d0",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/productType%2Fproduct_type_3.png?alt=media&token=ca139627-808f-404d-9282-efc83396ed0c",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/productType%2Fproduct_type_4.png?alt=media&token=7fc71f98-a9ae-4191-9ab7-1705ea04133c"
//        };
//        String[] descriptionProductType = {
//                "Nước hoa hồng bình dân",
//                "Sữa rửa mặt cao cấp",
//                "Kem chống nắng bình dân",
//                "Tẩy trang cao cấp"
//        };
//
//        for (int i = 0; i < nameProductType.length; i++) {
//            ProductType productType = new ProductType(nameProductType[i], productTypeImgURL[i], descriptionProductType[i]);
//            database.productTypeDAO().insertProductType(productType);
//        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("productType");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot.isEmpty()) {
                        InitDatabaseFirebase.initDatabaseProductType();
                    }
                    if (querySnapshot != null) {
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            // Thực hiện các thao tác với từng tài liệu ở đây
                            String documentId = documentSnapshot.getId();

                            // Lấy dữ liệu từ các trường trong tài liệu
                            String name = documentSnapshot.getString("name");
                            String productTypeImgURL = documentSnapshot.getString("productTypeImgURL");
                            String description = documentSnapshot.getString("description");

                            ProductType productType = new ProductType(name, productTypeImgURL, description);
                            database.productTypeDAO().insertProductType(productType);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                } else {
                    // Xử lý khi đọc collection thất bại
                }
            }
        });
    }
    private void initUserTable() {
        // User table
//        String[] username = {
//                "admin@gmail.com",
//                "lethangng@gmail.com"
//        };
//        String[] password = {
//                "admin123",
//                "user123",
//        };
//        String imgFace = "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/img_avatar.png?alt=media&token=a18fb5b2-37dc-47af-8a6d-861ece6ca6a1";
//        int[] gender = {0, 1};
//        String[] nameUser = {
//                "Admin",
//                "Lê Ngọc Thắng"
//        };
//        String[] phoneNumber = {"0123", "0124"};
//        String[] address = {
//                "Thái Bình",
//                "Hà Nội"
//        };
//
//        for (int i = 0; i < username.length; i++) {
//            User user = new User(username[i], password[i], imgFace, gender[i], nameUser[i], phoneNumber[i], address[i]);
//            database.userDAO().insertUser(user);
//        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("user");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot.isEmpty()) {
                        InitDatabaseFirebase.initDatabaseUser();
                    }
                    if (querySnapshot != null) {
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            // Thực hiện các thao tác với từng tài liệu ở đây
                            String documentId = documentSnapshot.getId();
                            // Lấy dữ liệu từ các trường trong tài liệu
                            String username = documentSnapshot.getString("username");
                            String password = documentSnapshot.getString("password");
                            String imgFace = documentSnapshot.getString("imgFace");
                            int gender = documentSnapshot.get("gender", Integer.class);
                            String name = documentSnapshot.getString("name");
                            String phoneNumber = documentSnapshot.getString("phoneNumber");
                            String address = documentSnapshot.getString("address");

                            User user = new User(username, password, imgFace, gender, name, phoneNumber, address);
                            database.userDAO().insertUser(user);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                } else {
                    // Xử lý khi đọc collection thất bại
                }
            }
        });
    }
    private void initProductTable() {
//        String[] listProductURL = {
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_1.png?alt=media&token=f633b0a6-4435-42ea-a875-ea43a4fc21e1",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_2.png?alt=media&token=ed63b8f9-2167-4061-82eb-5d88af506ec2",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_3.png?alt=media&token=764d5d7e-4222-48ad-97d1-afed83b6a0da",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_4.png?alt=media&token=4a8d8cf0-5ff4-4a6b-809a-a258632bb4e5",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_5.png?alt=media&token=c7d02534-da2f-485f-9533-ce6ed50963e5",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/product%2Fproduct_6.png?alt=media&token=ae99a2af-79c4-4d9c-8f86-b88ffb36c9bb"
//        };
//
//        String[] name = {
//                "Product 1",
//                "Product 2",
//                "Product 3",
//                "Product 4",
//                "Product 5",
//                "Product 6"
//        };
//        String description = "Nước Hoa Shimang Bản Cao Cấp Eau De Cologne EDP 50M<br><br>Thương hiệu: Shimang<br>- " +
//                "Xuất xứ: Nội Địa Trung Quốc<br>- Tên và địa chỉ của cá nhân chịu trách nhiệm về hàng hóa:<br>" +
//                "Sản phẩm được phân phối bởi: Chustore2017<br>Địa chỉ: Tòa A3 chung cư An Bình City - 232 Phạm văn Đồng, Bắc Từ Liêm, Hà Nội<br><br>" +
//                "Dung tích: 50ML<br>Quy cách đóng gói: Full Box<br>Đối tượng sử dụng: Phù hợp với cả nam và nữ<br><br>" +
//                "E này đc khen là giữ hương 6-8 tiếng luôn đó. Mùi dịu dàng ngọt ngào chứ k hề hắc ạ.<br>Có cả mùi hoa quả. Mùi mang tính ấm phù hợp mùa thu và đông.<br><br>" +
//                "Phân loại mùi hương:<br>- Scent of tea: Hương thơm trà không ngọt dành cho nam và nữ. Hương đầu là mùi trà hoa cam.<br>" +
//                "Hương giữa là bạch đậu khấu.<br>Hương cuối là mùi hoa nhài và gỗ mun";
//        int imgProductListId = 1;
//        int[] price = {10000, 20000, 25000, 30000, 40000, 60000};
//        int[] sold = {1000, 600, 100, 1200, 500, 400};
//        int categoryId = 1;
//        int productTypeId[] = {1, 2, 1, 3, 1, 4};
//        int[] quantity = {1000, 200, 600, 800, 400, 100};
//
//        for (int i = 0; i < name.length; i++) {
//            Product product = new Product(name[i], description, listProductURL[i],
//                    imgProductListId, price[i], sold[i], categoryId, productTypeId[i], quantity[i]);
//            database.productDAO().insertProduct(product);
//        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("product");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot.isEmpty()) {
                        InitDatabaseFirebase.initDatabaseProduct();
                    }

                    if (querySnapshot != null) {
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            // Thực hiện các thao tác với từng tài liệu ở đây
                            String documentId = documentSnapshot.getId();
                            // Lấy dữ liệu từ các trường trong tài liệu
                            String name = documentSnapshot.getString("name");
                            String description = documentSnapshot.getString("description");
                            String imgProductURL = documentSnapshot.getString("imgProductURL");
                            int imgProductListId = documentSnapshot.get("imgProductListId", Integer.class);
                            int price = documentSnapshot.get("price", Integer.class);
                            int sold = documentSnapshot.get("sold", Integer.class);
                            int categoryId = documentSnapshot.get("categoryId", Integer.class);
                            int productTypeId = documentSnapshot.get("productTypeId", Integer.class);
                            int quantity = documentSnapshot.get("quantity", Integer.class);

                            Product product = new Product(name, description, imgProductURL,
                                    imgProductListId, price, sold, categoryId, productTypeId, quantity);
                            database.productDAO().insertProduct(product);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                } else {
                    // Xử lý khi đọc collection thất bại
                }
            }
        });
    }
    private void initCartTable() {
//        int[] productIdCart = {1, 2, 4};
//        int[] userIdCart = {1, 1, 1};
//        for (int i = 0; i < productIdCart.length; i++) {
//            Cart cart = new Cart(productIdCart[i], userIdCart[i]);
//            database.cartDAO().insertCart(cart);
//        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("cart");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot.isEmpty()) {
                        InitDatabaseFirebase.initDatabaseCart();
                    }

                    if (querySnapshot != null) {
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            // Thực hiện các thao tác với từng tài liệu ở đây
                            String documentId = documentSnapshot.getId();
                            // Lấy dữ liệu từ các trường trong tài liệu

                            int productId = documentSnapshot.get("productId", Integer.class);
                            int userId = documentSnapshot.get("userId", Integer.class);

                            Cart cart = new Cart(productId, userId);
                            database.cartDAO().insertCart(cart);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                } else {
                    // Xử lý khi đọc collection thất bại
                }
            }
        });
    }

    private void initListImgTable() {
        int[] productIdListImage = {1, 2, 3, 4, 5, 6, 1, 2, 3};
        int imgProduct1 = R.drawable.product_1;
        int imgProduct2 = R.drawable.product_2;
        int[] listImg = {imgProduct2, imgProduct1, imgProduct2, imgProduct1, imgProduct2, imgProduct1, imgProduct2, imgProduct1, imgProduct2};
        for (int i = 0; i < productIdListImage.length; i++) {
            ListImage listImage = new ListImage(productIdListImage[i], listImg[i]);
            database.listImageDAO().insertListImage(listImage);
        }
    }

    private void initCarouselTable() {
//        String[] carouselImg = {
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/carousel%2Fcarousel_1.png?alt=media&token=19edb44c-0ef0-43ab-b1da-7187435c2f2e",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/carousel%2Fcarousel_2.png?alt=media&token=c1391538-d6d3-4fba-a0bb-29a0b646ff12",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/carousel%2Fcarousel_3.png?alt=media&token=f52176eb-757a-4d6e-9926-f2fc2c8fccab",
//                "https://firebasestorage.googleapis.com/v0/b/shopmiphamapp.appspot.com/o/carousel%2Fcarousel_4.png?alt=media&token=3e6fa2db-b418-4d57-b0e9-194e82a0a9a7"
//        };
//
//        for (int i = 0; i < carouselImg.length; i++) {
//            Carousel carousel = new Carousel(carouselImg[i]);
//            database.carouselDAO().insertCarousel(carousel);
//        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("carousel");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot.isEmpty()) {
                        InitDatabaseFirebase.initDatabaseCarousel();
                    }

                    if (querySnapshot != null) {
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            // Thực hiện các thao tác với từng tài liệu ở đây
                            String documentId = documentSnapshot.getId();
                            // Lấy dữ liệu từ các trường trong tài liệu

                            String carouselImg = documentSnapshot.getString("carouselImg");

                            Carousel carousel = new Carousel(carouselImg);
                            database.carouselDAO().insertCarousel(carousel);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                } else {
                    // Xử lý khi đọc collection thất bại
                }
            }
        });
    }

    private void initFavoriteProductTable() {
//        int[] userIdFavoriteProduct = {1, 1};
//        int[] productIdFavoriteProduct = {1, 2};
//
//        for (int i = 0; i < userIdFavoriteProduct.length; i++) {
//            FavoriteProduct favoriteProduct = new FavoriteProduct(userIdFavoriteProduct[i], productIdFavoriteProduct[i]);
//            database.favoriteProductDAO().insertFavoriteProduct(favoriteProduct);
//        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("favoriteProduct");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (querySnapshot.isEmpty()) {
                        InitDatabaseFirebase.initDatabaseFavoriteProduct();
                    }

                    if (querySnapshot != null) {
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            // Thực hiện các thao tác với từng tài liệu ở đây
                            String documentId = documentSnapshot.getId();
                            // Lấy dữ liệu từ các trường trong tài liệu

                            int productId = documentSnapshot.get("productId", Integer.class);
                            int userId = documentSnapshot.get("userId", Integer.class);

                            FavoriteProduct favoriteProduct = new FavoriteProduct(userId, productId);
                            database.favoriteProductDAO().insertFavoriteProduct(favoriteProduct);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                } else {
                    // Xử lý khi đọc collection thất bại
                }
            }
        });
    }

}
