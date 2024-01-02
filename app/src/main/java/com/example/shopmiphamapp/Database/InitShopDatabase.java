package com.example.shopmiphamapp.Database;

import androidx.annotation.NonNull;

import com.example.shopmiphamapp.Database.Bill.Bill;
import com.example.shopmiphamapp.Database.Carousel.Carousel;
import com.example.shopmiphamapp.Database.Cart.Cart;
import com.example.shopmiphamapp.Database.FavoriteProduct.FavoriteProduct;
import com.example.shopmiphamapp.Database.Product.Product;
import com.example.shopmiphamapp.Database.ProductType.ProductType;
import com.example.shopmiphamapp.Database.Product_Bill.Product_Bill;
import com.example.shopmiphamapp.Database.User.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class InitShopDatabase {
    ShopDatabase database;
    int actionsCompleted = 0;
    int totalNumberOfActions = 8;
    public InitShopDatabase(ShopDatabase database) {
        this.database = database;
    }
    private String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void loadData() {
        deleteData();

        initUserTable();
        initProductTypeTable();
        initCarouselTable();
        initProductTable();
        initCartTable();
        initFavoriteProductTable();
        initBillTable();
        initProductBillTable();
    }

    private void deleteData() {
        database.userDAO().deleteAllUsers();
        database.billDAO().deleteAllBills();
        database.carouselDAO().deleteAllCarousels();
        database.cartDAO().deleteAllCarts();
        database.favoriteProductDAO().deleteAllFavoriteProducts();
        database.productDAO().deleteAllProducts();
        database.productBillDAO().deleteAllProductBills();
        database.productTypeDAO().deleteAllProductTypes();
    }

    private void initProductTypeTable() {
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
                            int id = documentSnapshot.get("id", Integer.class);
                            String name = documentSnapshot.getString("name");
                            String imgUrl = documentSnapshot.getString("imgUrl");
                            String description = documentSnapshot.getString("description");

                            ProductType productType = new ProductType(id, name, imgUrl, description);
                            database.productTypeDAO().insertProductType(productType);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                }
            }
        });
    }
    private void initUserTable() {
        CollectionReference collectionRef = db.collection("user");
        collectionRef.document(userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            if (documentSnapshot != null && documentSnapshot.exists()) {
                                // Lấy dữ liệu từ các trường trong tài liệu
                                String id = documentSnapshot.getId();
                                String username = documentSnapshot.getString("username");
                                String password = documentSnapshot.getString("password");
                                String imgFace = documentSnapshot.getString("imgFace");
                                int gender = documentSnapshot.get("gender", Integer.class);
                                String name = documentSnapshot.getString("name");
                                String phoneNumber = documentSnapshot.getString("phoneNumber");
                                String address = documentSnapshot.getString("address");

                                User user = new User(id, username, password, imgFace, gender, name, phoneNumber, address);
                                database.userDAO().insertUser(user);
                            }
                            actionsCompleted++;

                            // Kiểm tra nếu đã hoàn thành tất cả các hành động
                            if (actionsCompleted == totalNumberOfActions) {
                                database.isDatabaseInitialized.setValue(true);
                            }
                        }
                    }
                });
    }
    private void initProductTable() {
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
                            int id = documentSnapshot.get("id", Integer.class);
                            String name = documentSnapshot.getString("name");
                            String description = documentSnapshot.getString("description");
                            String imgUrl = documentSnapshot.getString("imgUrl");
                            int price = documentSnapshot.get("price", Integer.class);
                            int sold = documentSnapshot.get("sold", Integer.class);
                            int productTypeId = documentSnapshot.get("productTypeId", Integer.class);
                            int quantity = documentSnapshot.get("quantity", Integer.class);

                            Product product = new Product(id, name, description, imgUrl, price, sold, productTypeId, quantity);
                            database.productDAO().insertProduct(product);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                }
            }
        });
    }
    private void initCartTable() {
        CollectionReference collectionRef = db.collection("cart");
        collectionRef.whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();

                            if (querySnapshot != null) {
                                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                                    // Thực hiện các thao tác với từng tài liệu ở đây
                                    String documentId = documentSnapshot.getId();

                                    // Lấy dữ liệu từ các trường trong tài liệu
                                    String id = documentId;
                                    int productId = documentSnapshot.get("productId", Integer.class);
                                    String userId = documentSnapshot.getString("userId");

                                    Cart cart = new Cart(id, productId, userId);
                                    database.cartDAO().insertCart(cart);
                                }
                            }
                            actionsCompleted++;

                            // Kiểm tra nếu đã hoàn thành tất cả các hành động
                            if (actionsCompleted == totalNumberOfActions) {
                                database.isDatabaseInitialized.setValue(true);
                            }
                        }
                    }
                });
    }

    private void initCarouselTable() {
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
                            int id = documentSnapshot.get("id", Integer.class);
                            String imgUrl = documentSnapshot.getString("imgUrl");

                            Carousel carousel = new Carousel(id, imgUrl);
                            database.carouselDAO().insertCarousel(carousel);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                }
            }
        });
    }

    private void initFavoriteProductTable() {
        CollectionReference collectionRef = db.collection("favoriteProduct");
        collectionRef.whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();

                            if (querySnapshot != null) {
                                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                                    // Thực hiện các thao tác với từng tài liệu ở đây
                                    String documentId = documentSnapshot.getId();

                                    // Lấy dữ liệu từ các trường trong tài liệu
                                    String id = documentId;
                                    int productId = documentSnapshot.get("productId", Integer.class);
                                    String userId = documentSnapshot.getString("userId");

                                    FavoriteProduct favoriteProduct = new FavoriteProduct(id, userId, productId);
                                    database.favoriteProductDAO().insertFavoriteProduct(favoriteProduct);
                                }
                            }
                            actionsCompleted++;

                            // Kiểm tra nếu đã hoàn thành tất cả các hành động
                            if (actionsCompleted == totalNumberOfActions) {
                                database.isDatabaseInitialized.setValue(true);
                            }
                        }
                    }
                });
    }

    private void initBillTable() {
        CollectionReference collectionRef = db.collection("bill");
        collectionRef.whereEqualTo("userId", userId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();

                            if (querySnapshot != null) {
                                for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                                    // Thực hiện các thao tác với từng tài liệu ở đây
                                    String documentId = documentSnapshot.getId();

                                    // Lấy dữ liệu từ các trường trong tài liệu
                                    String id = documentId;
                                    long purchaseDate = documentSnapshot.get("purchaseDate", Long.class);
                                    String deliveryAddress = documentSnapshot.getString("deliveryAddress");
                                    int totalMoney = documentSnapshot.get("totalMoney", Integer.class);
                                    String userId = documentSnapshot.getString("userId");
                                    String payMethod = documentSnapshot.getString("payMethod");
                                    String note = documentSnapshot.getString("note");

                                    Bill bill = new Bill(id, purchaseDate, deliveryAddress, totalMoney, userId, payMethod, note);
                                    database.billDAO().insertBill(bill);
                                }
                            }
                            actionsCompleted++;

                            // Kiểm tra nếu đã hoàn thành tất cả các hành động
                            if (actionsCompleted == totalNumberOfActions) {
                                database.isDatabaseInitialized.setValue(true);
                            }
                        }
                    }
                });
    }

    private void initProductBillTable() {
        CollectionReference collectionRef = db.collection("productBill");
        collectionRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();

                    if (querySnapshot != null) {
                        for (QueryDocumentSnapshot documentSnapshot : querySnapshot) {
                            // Thực hiện các thao tác với từng tài liệu ở đây
                            String documentId = documentSnapshot.getId();

                            // Lấy dữ liệu từ các trường trong tài liệu
                            String id = documentId;
                            String billId = documentSnapshot.getString("billId");
                            int productId = documentSnapshot.get("productId", Integer.class);
                            int quantity = documentSnapshot.get("quantity", Integer.class);

                            Product_Bill productBill = new Product_Bill(id, billId, productId, quantity);
                            database.productBillDAO().insertProductBill(productBill);
                        }
                    }
                    actionsCompleted++;

                    // Kiểm tra nếu đã hoàn thành tất cả các hành động
                    if (actionsCompleted == totalNumberOfActions) {
                        database.isDatabaseInitialized.setValue(true);
                    }
                }
            }
        });
    }
}
