package com.example.charts.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.charts.productBuilder.Product;
import com.example.charts.activities.Rating;
import com.example.charts.user_Builder.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.charts.dataBase.DatabaseHelper.*;

public class Databasemanger {
    private static Databasemanger instance=null;
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Databasemanger(Context context) {
        this.context = context;
        open();
    }

    public static synchronized Databasemanger getInstance(Context context) {
        if (instance == null) {
            instance = new Databasemanger(context.getApplicationContext());
        }
        return instance;
    }

    private Databasemanger open() {
        databaseHelper = new DatabaseHelper(context);
        sqLiteDatabase = databaseHelper.getWritableDatabase();
        return this;
    }


    public boolean inserttdata(String titel,byte[]image,String descreption,int salary,String catagory,String size ,float sale,int number){

        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.titel,titel);
        contentValues.put(DatabaseHelper.image,image);
        contentValues.put(DatabaseHelper.description,descreption);
        contentValues.put(DatabaseHelper.salary,salary);
        contentValues.put(DatabaseHelper.category,catagory);
        contentValues.put(DatabaseHelper.size,size);
        contentValues.put(DatabaseHelper.sale,sale);
        contentValues.put(DatabaseHelper.itemNumber,number);

        long in=sqLiteDatabase.insert(DatabaseHelper.Tablename,null,contentValues);
        if (in==-1){
            return false;
        }
        else return true;

    }
    public boolean insert_rating(String EMAIL,String PRODUCT_NAME,int RATING) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_CUST_email, EMAIL);
        contentValues.put(DatabaseHelper.COL_PRODUCT_NAME, PRODUCT_NAME);
        contentValues.put(DatabaseHelper.COL_RATING,RATING );
        long in;
        in = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME_Rating, null, contentValues);
        return in != -1;
    }
    public boolean insert_FEEDBACK(String EMAIL,String PRODUCT_NAME,String feedback) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_CUST_email_FEEDBACK, EMAIL);
        contentValues.put(DatabaseHelper.COL_PRODUCT_NAME_FEEDBACK, PRODUCT_NAME);
        contentValues.put(DatabaseHelper.COL_FEEDBACK,feedback );
        long in;
        in = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME_FEEDBACK, null, contentValues);
        return in != -1;
    }

    public List<Rating> Display_feedbacks(String product_name)
    {

        String[] columns = {DatabaseHelper.COL_CUST_email_FEEDBACK, DatabaseHelper.COL_PRODUCT_NAME_FEEDBACK, DatabaseHelper.COL_FEEDBACK};
        String selection = DatabaseHelper.COL_PRODUCT_NAME_FEEDBACK + "=?";
        String[] selectionArgs = {product_name};
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_FEEDBACK, columns, selection, selectionArgs, null, null, null);
        List<Rating> all_comments=new ArrayList<>();
        if(cursor!=null&&cursor.moveToFirst())
            do{

                int cust_name_index = cursor.getColumnIndex(COL_CUST_email_FEEDBACK);
                int product_name_index=cursor.getColumnIndex(DatabaseHelper.COL_PRODUCT_NAME_FEEDBACK);
                int comment_index=cursor.getColumnIndex(COL_FEEDBACK);
                String cust_name = cursor.getString(cust_name_index);
                String productname = cursor.getString(product_name_index);
                String comment = cursor.getString(comment_index);
                Rating r=new Rating(cust_name,productname,comment);
                all_comments.add(r);


            }while(cursor.moveToNext());
        cursor.close();



        return all_comments;
    }

    public boolean checkrate(String email, String product) {
        String[] columns = {DatabaseHelper.COL_CUST_email};
        String selection = DatabaseHelper.COL_CUST_email + "=? and " + DatabaseHelper.COL_PRODUCT_NAME + "=?";
        String[] selectionArgs = {email, product};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME_Rating, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        return count >0;
    }

    public int Product_count_rating(String Title)
    {



        String[] projection = {DatabaseHelper.COL_RATING};
        String selection = DatabaseHelper.COL_PRODUCT_NAME + "=?";
        String[] selectionArgs = {String.valueOf(Title)};
        // Execute the query to get the cursor
        Cursor cursor = sqLiteDatabase.query(
                DatabaseHelper.TABLE_NAME_Rating,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        int total_rating_for_product = 0;
        int count=0;

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int rating_for_product = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_RATING));
                total_rating_for_product += rating_for_product;
                count+=1;
            }
            cursor.close();
        }
        if(count==0)
            return 0;
        else
            return total_rating_for_product/count;

    }


    public boolean updaterate(String email, String product_name,int RATING)
    {


        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COL_CUST_email, email);
        contentValues.put(DatabaseHelper.COL_PRODUCT_NAME, product_name);
        contentValues.put(DatabaseHelper.COL_RATING, RATING);
        String whereClause = DatabaseHelper.COL_CUST_email + "=? and " + DatabaseHelper.COL_PRODUCT_NAME + "=?";
        String[] whereArgs = {email,product_name};

        int rowsAffected = sqLiteDatabase.update(DatabaseHelper.TABLE_NAME_Rating, contentValues, whereClause, whereArgs);
        return rowsAffected > 0;
    }
    public boolean insertProductData(Product product) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.titel, product.getTitel());
        contentValues.put(DatabaseHelper.image, product.getImage());
        contentValues.put(DatabaseHelper.description, product.getDescription());
        contentValues.put(DatabaseHelper.salary, product.getSalary());
        contentValues.put(DatabaseHelper.category, product.getCategory());
        contentValues.put(DatabaseHelper.size, product.getSize());
        contentValues.put(sale, product.getSale());
        contentValues.put(DatabaseHelper.itemNumber, product.getItemNumber());
        contentValues.put(DatabaseHelper.qr_code,product.getQr_code());

        long in = sqLiteDatabase.insert(DatabaseHelper.Tablename, null, contentValues);
        return in != -1;
    }

    public boolean updateProductData(long id, Product product) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.titel, product.getTitel());
        contentValues.put(DatabaseHelper.image, product.getImage());
        contentValues.put(DatabaseHelper.description, product.getDescription());
        contentValues.put(DatabaseHelper.salary, product.getSalary());
        contentValues.put(DatabaseHelper.category, product.getCategory());
        contentValues.put(DatabaseHelper.size, product.getSize());
        contentValues.put(sale, product.getSale());
        contentValues.put(DatabaseHelper.itemNumber, product.getItemNumber());
        contentValues.put(DatabaseHelper.qr_code,product.getQr_code());

        String whereClause = DatabaseHelper.id + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        int rowsAffected = sqLiteDatabase.update(DatabaseHelper.Tablename, contentValues, whereClause, whereArgs);
        return rowsAffected > 0;
    }

    public Cursor fetch() {
        String columns[] = new String[]{ DatabaseHelper.id,DatabaseHelper.titel, DatabaseHelper.image,DatabaseHelper.description,DatabaseHelper.salary,DatabaseHelper.category,DatabaseHelper.size, sale,DatabaseHelper.itemNumber,DatabaseHelper.qr_code};
        return sqLiteDatabase.query(DatabaseHelper.Tablename, columns, null, null, null, null, null);
    }

    public Cursor fetchProductsByCategory(String categoryName) {
        String columns[] = new String[]{DatabaseHelper.id, DatabaseHelper.titel, DatabaseHelper.image, DatabaseHelper.description, DatabaseHelper.salary, DatabaseHelper.category, DatabaseHelper.size,sale, DatabaseHelper.itemNumber,DatabaseHelper.qr_code};
        String selection = DatabaseHelper.category + "=?";
        String selectionArgs[] = new String[]{categoryName};

        return sqLiteDatabase.query(DatabaseHelper.Tablename, columns, selection, selectionArgs, null, null, null);
    }
    public Cursor fetchProductsByTitel(String titel_name) {
        String columns[] = new String[]{DatabaseHelper.id, DatabaseHelper.titel, DatabaseHelper.image, DatabaseHelper.description, DatabaseHelper.salary,sale, DatabaseHelper.category, DatabaseHelper.size,sale, DatabaseHelper.itemNumber};
        String selection = DatabaseHelper.titel + "=?";
        String selectionArgs[] = new String[]{titel_name};

        return sqLiteDatabase.query(DatabaseHelper.Tablename, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor fetchWithId(long id) {
        String columns[] = new String[]{DatabaseHelper.id, DatabaseHelper.titel, DatabaseHelper.image, DatabaseHelper.description, DatabaseHelper.salary,sale,DatabaseHelper.category, DatabaseHelper.size, DatabaseHelper.itemNumber,DatabaseHelper.qr_code};
        String selection = DatabaseHelper.id + "=?";
        String[] selectionArgs = new String[]{String.valueOf(id)};
        return sqLiteDatabase.query(DatabaseHelper.Tablename, columns, selection, selectionArgs, null, null, null);
    }

    public int updateData(long id, String title,int salary, byte[] image, String description, String category, String size ,float sale, int itemNumber) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.titel, title);
        values.put(DatabaseHelper.image, image);
        values.put(DatabaseHelper.salary,salary);
        values.put(DatabaseHelper.description, description);
        values.put(DatabaseHelper.category, category);
        values.put(DatabaseHelper.size, size);
        values.put(DatabaseHelper.sale, sale);
        values.put(DatabaseHelper.itemNumber, itemNumber);

        String whereClause = DatabaseHelper.id + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};

        return sqLiteDatabase.update(DatabaseHelper.Tablename, values, whereClause, whereArgs);
    }

    public int deletat(long id){

        String whereclase=DatabaseHelper.id +"=?";
        String args[]=new String[]{String.valueOf(id)};

        return sqLiteDatabase.delete(DatabaseHelper.Tablename,whereclase,args);
    }

    public int updateDataonAlldata(String oldCategoryName, String newCategoryName) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.category, newCategoryName);

        String whereClause = DatabaseHelper.category + "=?";
        String[] whereArgs = new String[]{oldCategoryName};

        return sqLiteDatabase.update(DatabaseHelper.Tablename, values, whereClause, whereArgs);
    }
    public int deleteDataByCategoryFromall(String categoryToDelete) {
        String whereClause = DatabaseHelper.category + "=?";
        String[] whereArgs = new String[]{categoryToDelete};

        return sqLiteDatabase.delete(DatabaseHelper.Tablename, whereClause, whereArgs);
    }

    /////method for table of catagory
    public boolean inserttdataoncatagory(String catagoryname){

        ContentValues contentValues=new ContentValues();
        contentValues.put(DatabaseHelper.catagoryname,catagoryname);
        long in=sqLiteDatabase.insert(DatabaseHelper.catagoryTable,null,contentValues);
        if (in==-1){
            return false;
        }
        else return true;

    }
    public Cursor fetchcatagory() {
        String columns[] = new String[]{ DatabaseHelper.id,DatabaseHelper.catagoryname};
        return sqLiteDatabase.query(DatabaseHelper.catagoryTable, columns, null, null, null, null, null);
    }
    public int deletcatagoryat(long id){

        String whereclase=DatabaseHelper.id +"=?";
        String args[]=new String[]{String.valueOf(id)};

        return sqLiteDatabase.delete(DatabaseHelper.catagoryTable,whereclase,args);
    }
    public int updateData(long id,String nameofcatagory) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.catagoryname, nameofcatagory);
        String whereClause = DatabaseHelper.id + "=?";
        String[] whereArgs = new String[]{String.valueOf(id)};
        return sqLiteDatabase.update(DatabaseHelper.Tablename, values, whereClause, whereArgs);
    }

    public int updateDataonCatagory(String oldCategoryName, String newCategoryName) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.catagoryname, newCategoryName);

        String whereClause = DatabaseHelper.catagoryname + "=?";
        String[] whereArgs = new String[]{oldCategoryName};

        return sqLiteDatabase.update(DatabaseHelper.catagoryTable, values, whereClause, whereArgs);
    }

    public boolean isCategoryExists(String specificCategory) {
        String columns[] = new String[]{DatabaseHelper.id, DatabaseHelper.catagoryname};
        String selection = DatabaseHelper.catagoryname + "=?";
        String selectionArgs[] = new String[]{specificCategory};

        Cursor cursor = sqLiteDatabase.query(DatabaseHelper.catagoryTable, columns, selection, selectionArgs, null, null, null);

        boolean categoryExists = cursor.moveToFirst();

        // Close the cursor to release resources
        cursor.close();

        return categoryExists;
    }
    public int deleteDataByCategoryFromCatagores(String categoryToDelete) {
        String whereClause = DatabaseHelper.catagoryname + "=?";
        String[] whereArgs = new String[]{categoryToDelete};

        return sqLiteDatabase.delete(DatabaseHelper.catagoryTable, whereClause, whereArgs);
    }

    /////////////////////////////////////////////////////////////////
    //method for cart
    public Cursor fetchCart() {
        String[] columns = new String[]{
                DatabaseHelper.COLUMN_USER_ID,
                DatabaseHelper.COLUMN_PRODUCT_NAME,
                DatabaseHelper.COLUMN_PRODUCT_IMAGE,
                DatabaseHelper.COLUMN_PRODUCT_DES,
                DatabaseHelper.COLUMN_PRODUCT_PRICE,
                COLUMN_PRODUCT_SALE,
                DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER
        };

        String selection = null; // Specify your selection criteria if needed
        String[] selectionArgs = null; // Provide arguments for the selection if needed
        String groupBy = null; // Specify group by clause if needed
        String having = null; // Specify having clause if needed
        String orderBy = null; // Specify order by clause if needed

        return sqLiteDatabase.query(
                DatabaseHelper.TABLE_NAME_CARTS,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy
        );
    }
    public Cursor fetchchartbyEmail(String email) {
        String columns[] = new String[]{DatabaseHelper.COLUMN_USER_ID, DatabaseHelper.COLUMN_PRODUCT_NAME, DatabaseHelper.COLUMN_PRODUCT_IMAGE, DatabaseHelper.COLUMN_PRODUCT_DES, DatabaseHelper.COLUMN_PRODUCT_PRICE,COLUMN_PRODUCT_SALE,DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER};
        String selection = COLUMN_USER_EMAIL + "=?";
        String[] selectionArgs = new String[]{String.valueOf(email)};
        return sqLiteDatabase.query(TABLE_NAME_CARTS, columns, selection, selectionArgs, null, null, null);
    }

    public void addItemToCart(String userEmail, String productName, String productDescription, byte[] productImage, double productPrice , float sale, int productItemNumber) {
        // Check if the item already exists in the cart
        Cursor cursor = sqLiteDatabase.query(
                DatabaseHelper.TABLE_NAME_CARTS,
                new String[]{DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER},
                DatabaseHelper.COLUMN_PRODUCT_NAME + "=?",
                new String[]{productName},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            // Item exists, increment the item number
            int existingItemNumber = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER));
            productItemNumber += existingItemNumber;

            ContentValues updateValues = new ContentValues();
            updateValues.put(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER, productItemNumber);

            String whereClause =DatabaseHelper.COLUMN_PRODUCT_NAME + "=?";
            String[] whereArgs = {productName};

            sqLiteDatabase.update(DatabaseHelper.TABLE_NAME_CARTS, updateValues, whereClause, whereArgs);

            cursor.close();
        } else {
            // Item doesn't exist, insert a new record
            ContentValues values = new ContentValues();
            values.put(COLUMN_USER_EMAIL, userEmail);
            values.put(DatabaseHelper.COLUMN_PRODUCT_NAME, productName);
            values.put(DatabaseHelper.COLUMN_PRODUCT_DES, productDescription);
            values.put(DatabaseHelper.COLUMN_PRODUCT_IMAGE, productImage);
            values.put(DatabaseHelper.COLUMN_PRODUCT_PRICE, productPrice);
            values.put(COLUMN_PRODUCT_SALE, sale);
            values.put(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER, productItemNumber);
            sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME_CARTS, null, values);
        }
    }

    public int getTotalItemForUser(String userEmail) {
        // Define the columns to retrieve and the condition
        String[] projection = {DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER};
        String selection = COLUMN_USER_EMAIL + "=?";
        String[] selectionArgs = {String.valueOf(userEmail)};
        // Execute the query to get the cursor
        Cursor cursor = sqLiteDatabase.query(
                DatabaseHelper.TABLE_NAME_CARTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        int totalitem = 0;
        // Iterate through the cursor to calculate the total price
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int productitem = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER));
                totalitem += productitem;
            }
        }
        return totalitem;
    }

    public double getTotalPriceForUser(String userEmail) {
        // Define the columns to retrieve and the condition
        String[] projection = {DatabaseHelper.COLUMN_PRODUCT_PRICE,DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER,COLUMN_PRODUCT_SALE};
        String selection = COLUMN_USER_EMAIL + "=?";
        String[] selectionArgs = {String.valueOf(userEmail)};
        // Execute the query to get the cursor
        Cursor cursor = sqLiteDatabase.query(
                DatabaseHelper.TABLE_NAME_CARTS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        double totalPrice = 0.0;
        // Iterate through the cursor to calculate the total price
        if (cursor != null) {
            while (cursor.moveToNext()) {
                float productPrice = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_PRICE));
                int item = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER));
                totalPrice += (productPrice*item);
                float x= cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_PRODUCT_SALE));
                float n = (float) ((x/100)*totalPrice);

                totalPrice-=n;
            }
            cursor.close();
        }
        return totalPrice;
    }

    public int updateCartItemNumber(String productName, int newProductItemNumber) {
        // Assuming this is a method in your DatabaseHelper class

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER, newProductItemNumber);

        String whereClause = DatabaseHelper.COLUMN_PRODUCT_NAME + "=?";
        String[] whereArgs = {productName};

        // Perform the update
        int rowsAffected = sqLiteDatabase.update(DatabaseHelper.TABLE_NAME_CARTS, values, whereClause, whereArgs);

        // Query for the updated item count
        Cursor cursor = sqLiteDatabase.query(
                DatabaseHelper.TABLE_NAME_CARTS,
                new String[]{DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER},
                DatabaseHelper.COLUMN_PRODUCT_NAME + "=?",
                new String[]{productName},
                null,
                null,
                null
        );

        int updatedItemCount = -1; // Default value if not found

        if (cursor != null && cursor.moveToFirst()) {
            updatedItemCount = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER));
            cursor.close();
        }
        // Return the updated item count or -1 if not found
        return updatedItemCount;
    }

    public void deleteItemFromCart(String productName) {
        String whereClause = DatabaseHelper.COLUMN_PRODUCT_NAME + "=?";
        String[] whereArgs = {productName};
        sqLiteDatabase.delete(DatabaseHelper.TABLE_NAME_CARTS, whereClause, whereArgs);
    }
    public void updateProductItemNumber( Cursor cartCursor) {
        while (cartCursor.moveToNext()) {
            // Extract data from the cart cursor
            String productName = cartCursor.getString(cartCursor.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME));
            int productItemNumber = cartCursor.getInt(cartCursor.getColumnIndexOrThrow(COLUMN_PRODUCT_ITEM_NUMBER));

            // Update the product table by subtracting the item number
            String updateQuery = "UPDATE " + Tablename +
                    " SET " + itemNumber + " = " + itemNumber + " - " + productItemNumber +
                    " WHERE " + titel + " = '" + productName + "'";

            sqLiteDatabase.execSQL(updateQuery);
        }
        // Close the cursor after use
        cartCursor.close();
}


    public int getProductItemNumber(String productName) {

        if (sqLiteDatabase == null) {
            return -1; // Unable to get a readable database
        }

        String[] projection = {DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER};
        String selection = DatabaseHelper.COLUMN_PRODUCT_NAME + " = ?";
        String[] selectionArgs = {productName};
        Cursor cursor = null;
        int productItemNumber = -1; // Default value if not found

        try {
            cursor = sqLiteDatabase.query(
                    DatabaseHelper.TABLE_NAME_CARTS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                productItemNumber = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRODUCT_ITEM_NUMBER));
            }
        } catch (Exception e) {
            // Handle exceptions, log, or notify about errors
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return productItemNumber;
    }

    /////////////////////////////////////////////////
    //for user

    public boolean addUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, user.getUsername());
        contentValues.put(COL_EMAIL, user.getEmail());
        contentValues.put(COL_PASSWORD, user.getPassword());
        contentValues.put(COL_USER_TYPE, user.getUserType());
        contentValues.put(COL_SSN, user.getSsn());
        contentValues.put(COL_PHONE, user.getPhone());
        contentValues.put(COL_BIRTHDAY, user.getBirthday());
        contentValues.put(COL_GENDER, user.getGender());
        long result = sqLiteDatabase.insert(DatabaseHelper.TABLE_NAME_USER, null, contentValues);
        return result != -1;
    }
//    public boolean checkUser(String email, String password) {
//        String[] columns = {COL_EMAIL};
//        String selection = COL_EMAIL + "=? and " + COL_PASSWORD + "=?";
//        String[] selectionArgs = {email, password};
//        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_USER, columns, selection, selectionArgs, null, null, null);
//        int count = cursor.getCount();
//        return count > 0;
//    }
    public boolean checkUser(String email, String password) {
        String[] columns = {COL_EMAIL};
        String selection;
        String[] selectionArgs;

        if (password != null) {
            // Password is provided, include it in the selection
            selection = COL_EMAIL + "=? and " + COL_PASSWORD + "=?";
            selectionArgs = new String[]{email, password};
        } else {
            // Password is not provided, check only the email
            selection = COL_EMAIL + "=?";
            selectionArgs = new String[]{email};
        }

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_USER, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();  // Close the cursor to avoid potential resource leaks
        return count > 0;
    }

    public boolean updateUser(String email, String newUsername, String newPassword,
                                String newSSN, String newPhone,String userbirthday , String newGender) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, newUsername);
        contentValues.put(COL_PASSWORD, newPassword);
        contentValues.put(COL_SSN, newSSN);
        contentValues.put(COL_PHONE, newPhone);
        contentValues.put(COL_BIRTHDAY,userbirthday);
        contentValues.put(COL_GENDER, newGender);

        String whereClause = COL_EMAIL + "=?";
        String[] whereArgs = {email};

        int rowsAffected = sqLiteDatabase.update(TABLE_NAME_USER, contentValues, whereClause, whereArgs);
        return rowsAffected > 0;
    }


    public User retrieveUser(String email) {
        String[] columns = {COL_USERNAME, COL_EMAIL, COL_PASSWORD, COL_USER_TYPE, COL_SSN, COL_PHONE,COL_BIRTHDAY, COL_GENDER};
        String selection = COL_EMAIL + "=?";
        String[] selectionArgs = {email};

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME_USER, columns, selection, selectionArgs, null, null, null);

        User user = null;

        if (cursor.moveToFirst()) {
            int usernameIndex = cursor.getColumnIndex(COL_USERNAME);
            int passwordIndex = cursor.getColumnIndex(COL_PASSWORD);
            int userTypeIndex = cursor.getColumnIndex(COL_USER_TYPE);
            int ssnIndex = cursor.getColumnIndex(COL_SSN);
            int phoneIndex = cursor.getColumnIndex(COL_PHONE);
            int birthdayIndex = cursor.getColumnIndex(COL_BIRTHDAY);
            int genderIndex = cursor.getColumnIndex(COL_GENDER);

            if (usernameIndex != -1 && passwordIndex != -1 && userTypeIndex != -1
                    && ssnIndex != -1 && phoneIndex != -1 && genderIndex != -1) {

                String username = cursor.getString(usernameIndex);
                String password = cursor.getString(passwordIndex);
                String userType = cursor.getString(userTypeIndex);
                String ssn = cursor.getString(ssnIndex);
                String phone = cursor.getString(phoneIndex);
                String birth = cursor.getString(birthdayIndex);
                String gender = cursor.getString(genderIndex);

                user = new User(username, email, password, userType, ssn, phone,birth , gender);
            }
        }
        return user;
    }

    public List<String> getAllUsernames() {
        List<String> usernameList = new ArrayList<>();

        try {
            Cursor cursor = sqLiteDatabase.query(TABLE_NAME_USER, new String[]{COL_USERNAME}, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int usernameIndex = cursor.getColumnIndex(COL_USERNAME);
                    if (usernameIndex != -1) {
                        String username = cursor.getString(usernameIndex);
                        usernameList.add(username);
                    }
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error retrieving usernames: " + e.getMessage());

        }

        return usernameList;
    }

    public boolean deleteUserByUsername(String username) {
        String whereClause = COL_USERNAME + "=?";
        String[] whereArgs = {username};
        int rowsAffected = sqLiteDatabase.delete(TABLE_NAME_USER, whereClause, whereArgs);
        return rowsAffected > 0;
    }
    public boolean updateUsername(String oldUsername, String newUsername) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, newUsername);
        String whereClause = COL_USERNAME + "=?";
        String[] whereArgs = {oldUsername};
        int rowsAffected = sqLiteDatabase.update(TABLE_NAME_USER, contentValues, whereClause, whereArgs);
        return rowsAffected > 0;
    }


    public boolean updatePassword(String email, String newPassword) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PASSWORD, newPassword);

        String whereClause = COL_EMAIL + "=?";
        String[] whereArgs = {email};

        int rowsAffected = sqLiteDatabase.update(TABLE_NAME_USER, contentValues, whereClause, whereArgs);

        return rowsAffected > 0;
    }

/////////////////////////////////////////////////////////////////
// Insert a new order into the database
public void insertOrder(String userEmail, String productName, int item, Date orderDate) {
    ContentValues values = new ContentValues();
    values.put(COLUMN_ORDER_USER_EMAIL, userEmail);
    values.put(COLUMN_PRODUCT_ORDER_NAME, productName);
    values.put(COLUMN_ORDER_ITEMS, item);
    values.put(COLUMN_ORDER_DATE, String.valueOf(orderDate));
    sqLiteDatabase.insert(TABLE_ORDERS, null, values);
}

    // Update an existing order in the database
    public void updateOrder(int orderId, String newProductName, int Numberitem, long newOrderDate) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_PRODUCT_ORDER_NAME, newProductName);
        values.put(COLUMN_ORDER_ITEMS, Numberitem);
        values.put(COLUMN_ORDER_DATE, newOrderDate);

        sqLiteDatabase.update(TABLE_ORDERS, values, COLUMN_ID + " = ?", new String[]{String.valueOf(orderId)});

    }

    // Delete an order from the database
    public void deleteOrder(String userEmail) {
        sqLiteDatabase.delete(TABLE_ORDERS, COLUMN_ORDER_USER_EMAIL + " = ?", new String[]{userEmail});
    }

    // Example method to retrieve all orders from the database
    public Cursor getAllOrders() {
        return sqLiteDatabase.query(TABLE_ORDERS, null, null, null, null, null, null);
    }
    public Cursor productsSales(){
        return sqLiteDatabase.query(TABLE_ORDERS, null, null, null, null, null, null);
    }

}

