package com.example.shopmiphamapp.Database;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;

import androidx.room.RoomDatabase;
import com.example.shopmiphamapp.R;

import java.io.IOException;

public class ShopDatabaseCallback extends RoomDatabase.Callback {
    @Override
    public void onCreate(SupportSQLiteDatabase db) {
        super.onCreate(db);

        // Thực hiện việc khởi tạo dữ liệu ban đầu ở đây
        // User table
        String[] username = {
                "admin@gmail.com",
                "user1@gmail.com",
                "user2@gmail.com",
                "user3@gmail.com"
        };
        String[] password = {
                "admin123",
                "user123",
                "user123",
                "user123"
        };
        int imgFace = R.drawable.img_avatar;
        int[] gender = {0, 0, 1, 1};
        String[] nameUser = {
                "Admin",
                "User 1",
                "User 2",
                "User 3"
        };
        String[] phoneNumber = {"0123", "0124", "0124", "0124"};
        String[] address = {
                "Thái Bình",
                "Nam Định",
                "Hà Nội",
                "Thanh Hóa"
        };

        SupportSQLiteStatement statementUser = db.compileStatement("INSERT INTO user (username, password, imgFace, gender, name, phoneNumber, address) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");

        // Lặp lại quá trình chèn dữ liệu nhiều lần
        for (int i = 0; i < username.length; i++) {
            // Gán giá trị cho các tham số
            statementUser.bindString(1, username[i]);
            statementUser.bindString(2, password[i]);
            statementUser.bindLong(3, imgFace);
            statementUser.bindLong(4, gender[i]);
            statementUser.bindString(5, nameUser[i]);
            statementUser.bindString(6, phoneNumber[i]);
            statementUser.bindString(7, address[i]);

            // Thực thi câu truy vấn
            statementUser.executeInsert();
        }

        // Product table
        int[] img_product = {R.drawable.product_1, R.drawable.product_2,
                R.drawable.product_3, R.drawable.product_4, R.drawable.product_5, R.drawable.product_6};

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
        int imgProductListId = 1;
        int[] price = {10000, 20000, 25000, 30000, 40000, 60000};
        int[] sold = {1000, 600, 100, 1200, 500, 400};
        int categoryId = 1;
        int productTypeId[] = {1, 2, 1, 3, 1, 4};
        int[] quantity = {1000, 200, 600, 800, 400, 100};

        SupportSQLiteStatement statementProduct = db.compileStatement("INSERT INTO product (name, description, imgProduct, imgProductListId, price, sold, categoryId, productTypeId, quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

        // Lặp lại quá trình chèn dữ liệu nhiều lần
        for (int i = 0; i < img_product.length; i++) {
            // Gán giá trị cho các tham số
            statementProduct.bindString(1, name[i]);
            statementProduct.bindString(2, description);
            statementProduct.bindLong(3, img_product[i]);
            statementProduct.bindLong(4, imgProductListId);
            statementProduct.bindLong(5, price[i]);
            statementProduct.bindLong(6, sold[i]);
            statementProduct.bindLong(7, categoryId);
            statementProduct.bindLong(8, productTypeId[i]);
            statementProduct.bindLong(9, quantity[i]);

            // Thực thi câu truy vấn
            statementProduct.executeInsert();
        }

        // Product type
        String[] nameProductType = {
                "Nước hoa hồng",
                "Sữa rửa mặt",
                "Kem chống nắng",
                "Tẩy trang"
        };
        int[] productTypeImg = {
                R.drawable.product_1,
                R.drawable.product_2,
                R.drawable.product_3,
                R.drawable.product_4
        };
        String[] descriptionProductType = {
                "Nước hoa hồng bình dân",
                "Sữa rửa mặt cao cấp",
                "Kem chống nắng bình dân",
                "Tẩy trang cao cấp"
        };
        SupportSQLiteStatement statementProductType = db.compileStatement("INSERT INTO product_type (name, productTypeImg, description)" +
                "VALUES (?, ?, ?)");
        for (int i = 0; i < nameProductType.length; i++) {
            statementProductType.bindString(1, nameProductType[i]);
            statementProductType.bindLong(2, productTypeImg[i]);
            statementProductType.bindString(3, descriptionProductType[i]);

            statementProductType.executeInsert();
        }

        // Cart
        int[] productIdCart = {1, 2, 4};
        int[] userIdCart = {1, 1, 1};
        SupportSQLiteStatement statementCart = db.compileStatement("INSERT INTO cart (productId, userId)" +
                "VALUES (?, ?)");
        for (int i = 0; i < productIdCart.length; i++) {
            statementCart.bindLong(1, productIdCart[i]);
            statementCart.bindLong(2, userIdCart[i]);
//            statementCart.bindLong(2, 1);

            statementCart.executeInsert();
        }

        // List Image
        int[] productIdListImage = {1, 2, 3, 4, 5, 6, 1, 2, 3};
        int imgProduct1 = R.drawable.product_1;
        int imgProduct2 = R.drawable.product_2;
        int[] listImg = {imgProduct2, imgProduct1, imgProduct2, imgProduct1, imgProduct2, imgProduct1, imgProduct2, imgProduct1, imgProduct2};
        SupportSQLiteStatement statementListImage = db.compileStatement("INSERT INTO list_image (productId, listImg)" +
                "VALUES (?, ?)");
        for (int i = 0; i < productIdListImage.length; i++) {
            statementListImage.bindLong(1, productIdListImage[i]);
            statementListImage.bindLong(2, listImg[i]);

            statementListImage.executeInsert();
        }

        // Category
        int[] carousel_img = {R.drawable.product_1, R.drawable.product_2,
                R.drawable.product_3, R.drawable.product_4, R.drawable.product_5, R.drawable.product_6};

        SupportSQLiteStatement statementCarousel = db.compileStatement("INSERT INTO carousel (carouselImg)" +
                "VALUES (?)");
        for (int i = 0; i < carousel_img.length; i++) {
            statementCarousel.bindLong(1, carousel_img[i]);

            statementCarousel.executeInsert();
        }

        // Favorite Product
        int[] userIdFavoriteProduct = {1, 1};
        int[] productIdFavoriteProduct = {1, 2};

        SupportSQLiteStatement statementFovariteProduct = db.compileStatement("INSERT INTO favorite_product (userId, productId)" +
                "VALUES (?, ?)");
        for (int i = 0; i < userIdFavoriteProduct.length; i++) {
            statementFovariteProduct.bindLong(1, userIdFavoriteProduct[i]);
            statementFovariteProduct.bindLong(2, productIdFavoriteProduct[i]);

            statementFovariteProduct.executeInsert();
        }

        // Đóng SQLiteStatement sau khi hoàn thành
        try {
            statementProduct.close();
            statementProductType.close();
            statementCart.close();
            statementUser.close();
            statementListImage.close();
            statementCarousel.close();
            statementFovariteProduct.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
