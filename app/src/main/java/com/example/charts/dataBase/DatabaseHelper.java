package com.example.charts.dataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    //creat Database Name
    public static final  String databasename="DataBaseforimage";

    // Define the table and its columns
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ORDER_USER_EMAIL = "email";
    public static final String COLUMN_PRODUCT_ORDER_NAME = "product_name";
    public static final String COLUMN_ORDER_ITEMS = "product_item";
    public static final String COLUMN_ORDER_DATE = "order_date";



    // SQL statement to create the table
    private static final String DATABASE_ORDER =
            "CREATE TABLE " + TABLE_ORDERS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ORDER_USER_EMAIL + " TEXT NOT NULL, " +
                    COLUMN_PRODUCT_ORDER_NAME + " TEXT NOT NULL, " +
                    COLUMN_ORDER_ITEMS + " TEXT NOT NULL, " +
                    COLUMN_ORDER_DATE + " TEXT);";


    //create Tabelname of product  :
    public static final String Tablename="Conteres";

    //create Columns for product
    public static final String titel = "Titel";
    public static final String id = "_id";
    public static final String image = "Image";
    public static final String category = "Category";
    public static final String salary = "Salary";
    public static final String description = "Description";
    public static final String size = "Size";
    public static final String sale = "sale";
    public static final String itemNumber = "ItemNumber";
    public static final String qr_code = "qr_code";

    /////////////////////////////////////////////////////////////////

    //create table of product catagory
    public static final String catagoryTable="CatagoryTable";

    //create columns

    public static final String catagoryname="CatagoryName";

    ///////////////////////////////////////////////////////////
    //table for cart
    public static final String TABLE_NAME_CARTS = "UserCarts";

    // table colum for cart
    public static  final String  COLUMN_USER_ID="_id";
    public static final String COLUMN_USER_EMAIL = "_email";
    public static final String COLUMN_PRODUCT_NAME = "Product_Nam";
    public static final String COLUMN_PRODUCT_DES = "Product_Description";
    public static final String COLUMN_PRODUCT_IMAGE = "Product_Image";
    public static final String COLUMN_PRODUCT_PRICE = "Product_Price";
    public static final String COLUMN_PRODUCT_SALE = "Product_Sale";
    public static final String COLUMN_PRODUCT_ITEM_NUMBER = "Product_item_number";

    //create table of cart
    private  static final String createtableforcart = "CREATE TABLE " + TABLE_NAME_CARTS + " ("
            + COLUMN_USER_ID + " INTEGER, "
            + COLUMN_USER_EMAIL + " TEXT, "
            + COLUMN_PRODUCT_NAME + " TEXT, "
            + COLUMN_PRODUCT_DES + " TEXT, "
            + COLUMN_PRODUCT_IMAGE + " BLOB, "  // Add space before TEXT
            + COLUMN_PRODUCT_PRICE + " INTEGER, "
            + COLUMN_PRODUCT_SALE + " REAL, "
            + COLUMN_PRODUCT_ITEM_NUMBER + " INTEGER "
            +")";

    private  static  final String droptableofcart ="drop table if exists "+TABLE_NAME_CARTS;

    private  static  final String droporderdata ="drop table if exists "+TABLE_ORDERS;

    ///////////////////////////////////////////////////////////////////////////
    //table for user
    static final String TABLE_NAME_USER = "user_table";
    static final String COL_USERNAME = "USERNAME";
    static final String COL_EMAIL = "EMAIL";
    static final String COL_PASSWORD = "PASSWORD";
    static final String COL_USER_TYPE = "USER_TYPE";
    static final String COL_SSN = "SSN";
    static final String COL_PHONE = "PHONE";
    static final String COL_BIRTHDAY = "Birthday";
    static final String COL_GENDER = "GENDER";


    String createTable_for_user = "CREATE TABLE " + TABLE_NAME_USER + " (" +
            COL_EMAIL + " TEXT PRIMARY KEY, " +
            COL_USERNAME + " TEXT UNIQUE, " +
            COL_PASSWORD + " TEXT, " +
            COL_USER_TYPE + " TEXT, " +
            COL_SSN + " TEXT, " +
            COL_PHONE + " TEXT, " +
            COL_BIRTHDAY + " Text, "+
            COL_GENDER + " TEXT)";



    static final int version=3;

    public DatabaseHelper(Context context) {
        super(context,databasename,null,version);
    }



    //create table of products
    private static final String createtableofproduct = "CREATE TABLE " + Tablename + "(" +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            titel + " TEXT NOT NULL, " +
            image + " BLOB, " +
            category + " TEXT, " +
            salary + " INTEGER, " +
            description + " TEXT, " +
            size + " TEXT, " +
            sale + " REAL, " +
            itemNumber + " INTEGER," +
            qr_code  +" TEXT );";


    //create table of catagory

    private  static  final  String createtableofcatagory= "CREATE TABLE "+ catagoryTable + "(" +
            id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            catagoryname + " TEXT NOT NULL);";
    ///youssefffffffffffffffffffffffffffffffffffffffffffffffffff
    ///////////////////////////////////////////////////////////////////////////
    public static final String TABLE_NAME_Rating = "RATING_TABLE";
    public static final String COL_RATE_ID = "RATE_ID";
    public static final String COL_CUST_email = "CUST";
    public static final String COL_PRODUCT_NAME = "PRODUCTS_id";
    public static final String COL_RATING = "RATING";


    private  static  final  String createTable_for_RATES= "CREATE TABLE "+ TABLE_NAME_Rating + "(" +
            COL_RATE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_CUST_email + " TEXT, " +
            COL_PRODUCT_NAME + " TEXT, " +
            COL_RATING + " INTEGER NOT NULL);";

    public static final String TABLE_NAME_FEEDBACK = "FEEDBACK_TABLE";
    public static final String COL_FEEDBACK_ID = "FEEDBACK_ID";
    public static final String COL_CUST_email_FEEDBACK = "CUST_FEEDBACK";
    public static final String COL_PRODUCT_NAME_FEEDBACK = "PRODUCTS_NAME";
    public static final String COL_FEEDBACK = "FEEDBACK";
    private  static  final  String createTable_for_FEEDBACK= "CREATE TABLE "+ TABLE_NAME_FEEDBACK + "(" +
            COL_FEEDBACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_CUST_email_FEEDBACK + " TEXT, " +
            COL_PRODUCT_NAME_FEEDBACK + " TEXT, " +
            COL_FEEDBACK + " TEXT NOT NULL);";
    ///////////////////////////////////////////////////////////////////////////


    private  static  final String droptableofproduct ="drop table if exists "+Tablename;
    private  static  final String droptableofcatagory ="drop table if exists "+catagoryTable;
    private  static  final String droptableofuser ="drop table if exists "+ TABLE_NAME_USER;
    private  static  final String droptableofrating ="drop table if exists "+TABLE_NAME_Rating;
    private  static  final String droptableofFEEDBACK ="drop table if exists "+TABLE_NAME_FEEDBACK;

    private void addAdminUser(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_USERNAME, "admin");
        contentValues.put(COL_EMAIL, "admin@gmail.com");
        contentValues.put(COL_PASSWORD, "123");
        contentValues.put(COL_USER_TYPE, "admin");
        contentValues.put(COL_SSN, "30203201401596");
        contentValues.put(COL_PHONE, "01147301457");
        contentValues.put(COL_BIRTHDAY,"17/8/2002");
        contentValues.put(COL_GENDER, "male");
        db.insert(TABLE_NAME_USER, null, contentValues);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createtableofproduct);
        db.execSQL(createtableofcatagory);
        db.execSQL(createtableforcart);
        db.execSQL(createTable_for_user);
        db.execSQL(DATABASE_ORDER);
        db.execSQL(createTable_for_RATES);
        db.execSQL(createTable_for_FEEDBACK);
        addAdminUser(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(droptableofproduct);
        db.execSQL(droptableofcatagory);
        db.execSQL(droptableofcart);
        db.execSQL(droptableofuser);
        db.execSQL(droporderdata);
        db.execSQL(droptableofrating);
        db.execSQL(droptableofFEEDBACK);
        onCreate(db);

    }
}