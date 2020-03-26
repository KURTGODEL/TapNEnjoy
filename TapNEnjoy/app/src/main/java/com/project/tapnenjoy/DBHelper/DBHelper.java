package com.project.tapnenjoy.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.project.tapnenjoy.DBHelper.Constants.*;
import com.project.tapnenjoy.Models.*;


public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "TapNEnjoy.db";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* User Table Creation Script */
        db.execSQL(
                "CREATE TABLE " + Users.TABLE_USER_NAME + "(" +
                        Users.USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Users.USER_NAME + " TEXT," +
                        Users.USER_USERNAME + " TEXT," +
                        Users.USER_PASSWORD + " TEXT," +
                        Users.USER_ADDRESS + " TEXT," +
                        Users.USER_LATITUDE + " REAL," +
                        Users.USER_LONGITUDE + " REAL," +
                        Users.USER_SINLATITUDE + " REAL," +
                        Users.USER_COSLATITUDE + " REAL," +
                        Users.USER_SINLONGITUDE + " REAL," +
                        Users.USER_COSLONGITUDE + " REAL," +
                        Users.USER_CREATION + " TEXT," +
                        Users.USER_STATUS + " INTEGER)");

        /* Product Table Creation Script */
        db.execSQL(
                "CREATE TABLE " + Products.TABLE_PRODUCT_NAME + "(" +
                        Products.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Products.PRODUCT_TITLE + " TEXT," +
                        Products.PRODUCT_PRICE + " REAL," +
                        Products.PRODUCT_DESCRIPTION + " TEXT," +
                        Products.PRODUCT_IMAGE + " BLOB," +
                        Products.PRODUCT_STOCK + " INTEGER," +
                        Products.PRODUCT_SELLER + " INTEGER," +
                        Products.PRODUCT_CREATION + " TEXT," +
                        Products.PRODUCT_STATUS + " INTEGER)");

        /* Seller Table Creation Script */
        db.execSQL(
                "CREATE TABLE " + Sellers.TABLE_SELLER_NAME + "(" +
                        Sellers.SELLER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Sellers.SELLER_USER + " INTEGER)");

        /* User Orders Table Creation Script */
        db.execSQL(
                "CREATE TABLE " + UserOrders.TABLE_USER_ORDER_NAME + "(" +
                        UserOrders.USER_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        UserOrders.USER_ORDER_USER + " INTEGER," +
                        UserOrders.USER_ORDER_SELLER + " INTEGER," +
                        UserOrders.USER_ORDER_PRODUCT + " INTEGER," +
                        UserOrders.USER_ORDER_QUANTITY + " INTEGER," +
                        UserOrders.USER_ORDER_CREATION + " TEXT," +
                        UserOrders.USER_ORDER_UPDATION + " TEXT," +
                        UserOrders.USER_ORDER_STATUS + " INTEGER)");

        /* User Offers Table Creation Script */
        db.execSQL(
                "CREATE TABLE " + UserOffers.TABLE_USER_OFFER_NAME + "(" +
                        UserOffers.USER_OFFER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        UserOffers.USER_OFFER_USER + " INTEGER," +
                        UserOffers.USER_OFFER_SELLER + " INTEGER," +
                        UserOffers.USER_OFFER_PRODUCT + " INTEGER," +
                        UserOffers.USER_OFFER_PRICE + " REAL," +
                        UserOffers.USER_OFFER_NEWPRICE + " REAL," +
                        UserOffers.USER_OFFER_UPDATION + " TEXT," +
                        UserOffers.USER_OFFER_CREATION + " TEXT," +
                        UserOffers.USER_OFFER_STATUS + " INTEGER)");

        /* User Watchs Table Creation Script */
        db.execSQL(
                "CREATE TABLE " + UserWatchs.TABLE_USER_WATCH_NAME + "(" +
                        UserWatchs.USER_WATCH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        UserWatchs.USER_WATCH_USER + " INTEGER," +
                        UserWatchs.USER_WATCH_PRODUCT + " INTEGER," +
                        UserWatchs.USER_WATCH_CREATION + " TEXT," +
                        UserWatchs.USER_WATCH_UPDATION + " TEXT," +
                        UserWatchs.USER_WATCH_STATUS + " INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Users.TABLE_USER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Products.TABLE_PRODUCT_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Sellers.TABLE_SELLER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserWatchs.TABLE_USER_WATCH_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserOrders.TABLE_USER_ORDER_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserOffers.TABLE_USER_OFFER_NAME);

        onCreate(db);
    }


    /* User CRUD */

    /**
     * @param user Model containing system's user data
     * @return Boolean true if success
     */
    public boolean insertUserData(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Users.USER_NAME, user.name);
        contentValues.put(Users.USER_USERNAME, user.userName);
        contentValues.put(Users.USER_PASSWORD, user.password);
        contentValues.put(Users.USER_ADDRESS, user.address);
        contentValues.put(Users.USER_LATITUDE, user.latitude);
        contentValues.put(Users.USER_LONGITUDE, user.longitude);
        contentValues.put(Users.USER_COSLATITUDE, Math.cos(Math.toRadians(user.latitude)));
        contentValues.put(Users.USER_SINLATITUDE, Math.sin(Math.toRadians(user.latitude)));
        contentValues.put(Users.USER_COSLONGITUDE, Math.cos(Math.toRadians(user.longitude)));
        contentValues.put(Users.USER_SINLONGITUDE, Math.sin(Math.toRadians(user.longitude)));
        contentValues.put(Users.USER_STATUS, (user.status ? 1 : 0));
        contentValues.put(Users.USER_CREATION,
                new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));

        long result = db.insert(Users.TABLE_USER_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            if(user.isSeller){
                return insertSellerData(new Seller((int)result));
            }

            return true;
        }
    }

    /**
     * @param seller Model containing system's seller data
     * @return Boolean true if success
     */
    private boolean insertSellerData(Seller seller){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Sellers.SELLER_USER, seller.userId);

        long result = db.insert(Sellers.TABLE_SELLER_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * @param username String containing username to be checked
     * @return Boolean true if username is in the database
     */
    public boolean checkUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT 1 FROM " +
                Users.TABLE_USER_NAME +
                " WHERE " + Users.USER_USERNAME + " = ?",
                new String[] {username});

        if(res.moveToLast()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @param username String containing username to be checked
     * @param password String containing password to be checked
     * @return Boolean true if user is in the database
     */
    public boolean checkUsernameAndPassword(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "select 1 from " + Users.TABLE_USER_NAME +
                        " where " + Users.USER_USERNAME + " = ?" +
                        " and " + Users.USER_PASSWORD + " = ?",
                new String[] {username, password});

        if(res.moveToLast()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * @param user Model containing system's user data
     * @return Boolean true if success
     */
    public boolean updateUserData(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Users.USER_NAME, user.name);
        contentValues.put(Users.USER_USERNAME, user.userName);
        contentValues.put(Users.USER_PASSWORD, user.password);
        contentValues.put(Users.USER_ADDRESS, user.address);
        contentValues.put(Users.USER_LATITUDE, user.latitude);
        contentValues.put(Users.USER_LONGITUDE, user.longitude);
        contentValues.put(Users.USER_COSLATITUDE, Math.cos(Math.toRadians(user.latitude)));
        contentValues.put(Users.USER_SINLATITUDE, Math.sin(Math.toRadians(user.latitude)));
        contentValues.put(Users.USER_COSLONGITUDE, Math.cos(Math.toRadians(user.longitude)));
        contentValues.put(Users.USER_SINLONGITUDE, Math.sin(Math.toRadians(user.longitude)));
        contentValues.put(Users.USER_STATUS, (user.status ? 1 : 0));
        contentValues.put(Users.USER_UPDATION,
                new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));

        long result =
                db.update(Users.TABLE_USER_NAME,
                contentValues,
                Users.USER_ID + " = ?",
                new String[] { user.Id.toString() });

        if(result == -1){
            return false;
        }else{
            if(user.isSeller){
                deleteSeller((int)result); // delete if exists
                return insertSellerData(new Seller((int)result));
            }

            return true;
        }
    }

    /**
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing user rows from the database
     */
    public Cursor getUsers(Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + Users.TABLE_USER_NAME + " LIMIT 10 OFFSET " + offset, null);
        return res;
    }

    /**
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing seller rows from the database
     */
    public Cursor getSellers(Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " + Sellers.TABLE_SELLER_NAME + " LIMIT 10 OFFSET " + offset, null);
        return res;
    }

    public Integer deleteSeller(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(
                Sellers.TABLE_SELLER_NAME,
                Sellers.SELLER_USER + " = ?",
                new String[] { id.toString() });
    }


    public Integer deleteUser(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(
                Users.TABLE_USER_NAME,
                Users.USER_ID + " = ?",
                new String[] { id.toString() });
    }


    /* Product CRUD */

    /**
     * @param product Model containing system's product data
     * @return Boolean true if success
     */
    public boolean insertProductData(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Products.PRODUCT_TITLE, product.title);
        contentValues.put(Products.PRODUCT_PRICE, product.price);
        contentValues.put(Products.PRODUCT_DESCRIPTION, product.description);
        contentValues.put(Products.PRODUCT_IMAGE, product.image);
        contentValues.put(Products.PRODUCT_SELLER, product.sellerId);
        contentValues.put(Products.PRODUCT_STOCK, product.stock);
        contentValues.put(Products.PRODUCT_STATUS, (product.status ? 1 : 0));
        contentValues.put(Products.PRODUCT_CREATION,
                new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));

        try {
            long result = db.insert(Products.TABLE_PRODUCT_NAME, null, contentValues);

            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }catch (Exception ex){
            return false;
        }
    }

    /**
     * @param product Model containing system's product data
     * @return Boolean true if success
     */
    public boolean updateProductData(Product product){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Products.PRODUCT_TITLE, product.title);
        contentValues.put(Products.PRODUCT_PRICE, product.price);
        contentValues.put(Products.PRODUCT_DESCRIPTION, product.description);
        contentValues.put(Products.PRODUCT_IMAGE, product.image);
        contentValues.put(Products.PRODUCT_SELLER, product.sellerId);
        contentValues.put(Products.PRODUCT_STOCK, product.stock);
        contentValues.put(Products.PRODUCT_STATUS, (product.status ? 1 : 0));
        contentValues.put(Users.USER_UPDATION,
                new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));

        db.update(Products.TABLE_PRODUCT_NAME,
                contentValues,
                Products.PRODUCT_ID + " = ?",
                new String[] { product.Id.toString() });

        return true;
    }

    /**
     * @param orderByPrice True or False for price ordination
     * @param orderDirection ASC or DESC for price ordination direction
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing product rows
     */
    public Cursor getProducts(Boolean orderByPrice, String orderDirection, Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT product_id as _id, image, title, price, description, stock, seller_id, status " +
                        "FROM " + Products.TABLE_PRODUCT_NAME + (orderByPrice ?
                            " ORDER BY " + Products.PRODUCT_PRICE + " " + (orderDirection.isEmpty() ? "ASC" : orderDirection) :
                            "") +
                    " LIMIT 10 OFFSET " + offset, null);

        return res;
    }

    /**
     * @param orderByPrice True or False for price ordination
     * @param orderDirection ASC or DESC for price ordination direction
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing product rows
     */
    public Cursor getProductsByTitle(Boolean orderByPrice, String orderDirection, Integer offset, String title){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT product_id as _id, image, title, price, description, stock, seller_id, status FROM " + Products.TABLE_PRODUCT_NAME +
                        " WHERE title LIKE '%" + title + "%'" +
                        (orderByPrice ?
                        " ORDER BY " + Products.PRODUCT_PRICE + " " + (orderDirection.isEmpty() ? "ASC" : orderDirection) :
                        "") + " LIMIT 10 OFFSET " + offset, null);

        return res;
    }

    /**
     * @param productId Integer containing product ID to be returned
     * @return Cursor containing product rows
     */
    public Product getProductById(Integer productId){
        SQLiteDatabase db = this.getReadableDatabase();
        Product product = null;
        Cursor res = db.rawQuery(
                "SELECT product_id as _id, image, title, price, description, stock, seller_id, status FROM " + Products.TABLE_PRODUCT_NAME +
                        " WHERE product_id = ?", new String[]{ String.valueOf(productId) });


        try {
            Field field = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            field.setAccessible(true);
            field.set(null, 4 * 1024 * 1024); //the 4MB is the new size
        } catch (Exception e) {
            Log.e("SYSERROR", e.getMessage());
        }

        try {
            while (res.moveToNext()) {
                Integer id = 0;

                // I do not know why the cursor is returning a wrong column name
                try{
                    id = res.getInt(res.getColumnIndex(Products.PRODUCT_ID));
                }catch (Exception e){
                    id = res.getInt(res.getColumnIndex("_id"));
                }

                product =
                        new Product(
                                id,
                                res.getString(res.getColumnIndex(Products.PRODUCT_TITLE)),
                                res.getDouble(res.getColumnIndex(Products.PRODUCT_PRICE)),
                                res.getString(res.getColumnIndex(Products.PRODUCT_DESCRIPTION)),
                                res.getBlob(res.getColumnIndex(Products.PRODUCT_IMAGE)),
                                res.getInt(res.getColumnIndex(Products.PRODUCT_STOCK)),
                                res.getInt(res.getColumnIndex(Products.PRODUCT_SELLER)),
                                (res.getInt(res.getColumnIndex(Products.PRODUCT_STATUS)) == 1)
                        );
            }
        }catch (Exception ex){
            Log.e("SYSERROR", ex.getMessage());
        }

        return product;
    }

    /**
     * @param orderByPrice True or False for price ordination
     * @param orderDirection ASC or DESC for price ordination direction
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing product rows by its popularity
     */
    public Cursor getProductsByPopularity(Boolean orderByPrice, String orderDirection, Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT P.* " +
                " FROM " + Products.TABLE_PRODUCT_NAME + " P INNER JOIN " + UserOrders.TABLE_USER_ORDER_NAME + " O " +
                " ON P." + Products.PRODUCT_ID + " = O." + UserOrders.USER_ORDER_PRODUCT  +
                " AND O." + UserOrders.USER_ORDER_STATUS + " = 1 " +
                " GROUP BY O." + UserOrders.USER_ORDER_PRODUCT +
                " ORDER BY COUNT(O." + UserOrders.USER_ORDER_PRODUCT + ") DESC " +
                (orderByPrice ?
                    " , P." + Products.PRODUCT_PRICE + " " + (orderDirection.isEmpty() ? "ASC" : orderDirection) :
                                "") +
                " LIMIT 10 OFFSET " + offset, null);

        return res;
    }

    /**
     * @param orderByPrice True or False for price ordination
     * @param orderDirection ASC or DESC for price ordination direction
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return ArrayList<Product> containing product rows by its distance from seller and customer
     */
    public ArrayList<Product> getProductsByDistance(Boolean orderByPrice, String orderDirection, Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor res = db.rawQuery(
            " SELECT P.*, " + formatDistanceQuery() + " as distance " +
            " FROM " + Products.TABLE_PRODUCT_NAME + " P INNER JOIN " + UserOrders.TABLE_USER_ORDER_NAME + " O " +
            " ON P." + Products.PRODUCT_ID + " = O." + UserOrders.USER_ORDER_PRODUCT + " " +
            " AND O." + UserOrders.USER_ORDER_STATUS + " = 1 INNER JOIN " + Users.TABLE_USER_NAME + " U " +
            " ON U." + Users.USER_ID + " = O." + UserOrders.USER_ORDER_USER + " INNER JOIN " + Users.TABLE_USER_NAME + " S " +
            " ON S." + Users.USER_ID + " = O." + UserOrders.USER_ORDER_SELLER + " " +
            " ORDER BY distance ASC " +
                    (orderByPrice ?
                    " , P." + Products.PRODUCT_PRICE + " " + (orderDirection.isEmpty() ? "ASC" : orderDirection) :
                    "") +
            " LIMIT 10 OFFSET " + offset, null);


        ArrayList<Product> products = new ArrayList();

        res.moveToFirst();

        while(res.isAfterLast() == false){
            Product product = new Product(
                    res.getInt(res.getColumnIndex(Products.PRODUCT_ID)),
                    res.getString(res.getColumnIndex(Products.PRODUCT_TITLE)),
                    res.getDouble(res.getColumnIndex(Products.PRODUCT_PRICE)),
                    res.getString(res.getColumnIndex(Products.PRODUCT_DESCRIPTION)),
                    res.getBlob(res.getColumnIndex(Products.PRODUCT_IMAGE)),
                    res.getInt(res.getColumnIndex(Products.PRODUCT_STOCK)),
                    res.getInt(res.getColumnIndex(Products.PRODUCT_SELLER)),
                    true,
                    res.getDouble(res.getColumnIndex("distance"))
            );

            product.distance = Math.acos(product.distance);
            product.distance = Math.toDegrees(product.distance);
            product.distance = product.distance * 60 * 1.1515 * 1.609344; // distance in KM

            products.add(product);
            res.moveToNext();
        }

        return products;
    }

    private static String formatDistanceQuery(){
        return " U." + Users.USER_SINLATITUDE + " * S." + Users.USER_SINLATITUDE +
                " + U." + Users.USER_COSLATITUDE + " * S." + Users.USER_COSLATITUDE +
                " * (S." + Users.USER_COSLONGITUDE + " * U." + Users.USER_COSLONGITUDE +
                " + S." + Users.USER_SINLONGITUDE + " * U." + Users.USER_SINLONGITUDE + ") ";
    }

    public Integer deleteProduct(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(
                Products.TABLE_PRODUCT_NAME,
                Products.PRODUCT_ID + " = ?",
                new String[] { id.toString() });
    }


    /* User Order CRUD */

    /**
     * @param userOrder Model containing system's user order data
     * @return Boolean true if success
     */
    public boolean insertUserOrderData(UserOrder userOrder){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserOrders.USER_ORDER_USER, userOrder.userId);
        contentValues.put(UserOrders.USER_ORDER_SELLER, userOrder.sellerId);
        contentValues.put(UserOrders.USER_ORDER_PRODUCT, userOrder.productId);
        contentValues.put(UserOrders.USER_ORDER_QUANTITY, userOrder.quantity);
        contentValues.put(UserOrders.USER_ORDER_STATUS, (userOrder.status ? 1 : 0));
        contentValues.put(UserOrders.USER_ORDER_CREATION,
                new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));

        long result = db.insert(UserOrders.TABLE_USER_ORDER_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * @param userId Integer containing user Id for WHERE clause
     * @return Integer containing number of user orders found
     */
    public Integer getUserOrdersNumber(Integer userId){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,
                UserOrders.TABLE_USER_ORDER_NAME,
                UserOrders.USER_ORDER_USER + " = ?" +
                " AND " + UserOrders.USER_ORDER_STATUS + " = 1 ",
                new String[] { userId.toString() });
    }

    /**
     * @param userId Integer containing user Id for WHERE clause
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing user orders rows from the database
     */
    public Cursor getUserOrders_old(Integer userId, Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " +
                        UserOrders.TABLE_USER_ORDER_NAME +
                        " WHERE " + UserOrders.USER_ORDER_USER + " = ?" +
                        " AND " + UserOrders.USER_ORDER_STATUS + " = 1 " +
                        " LIMIT 10 OFFSET " + offset,
                new String[] { userId.toString() });
        return res;
    }

    /**
     * @param userId Integer containing user Id for WHERE clause
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing user orders rows from the database
     */
    public Cursor getUserOrders(Integer userId, Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " +
                        UserOrders.TABLE_USER_ORDER_NAME +
                        " WHERE " + UserOrders.USER_ORDER_USER + " = ?" +
                        " AND " + UserOrders.USER_ORDER_STATUS + " = 1 " +
                        " LIMIT 10 OFFSET " + offset,
                new String[] { userId.toString() });
        return res;
    }

    /* User Watch CRUD */

    /**
     * @param userWatch Model containing system's user watch data
     * @return Boolean true if success
     */
    public boolean insertUserWatchData(UserWatch userWatch){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserWatchs.USER_WATCH_USER, userWatch.userId);
        contentValues.put(UserWatchs.USER_WATCH_PRODUCT, userWatch.productId);
        contentValues.put(UserWatchs.USER_WATCH_STATUS, (userWatch.status ? 1 : 0));
        contentValues.put(UserWatchs.USER_WATCH_CREATION,
                new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));

        long result = db.insert(UserWatchs.TABLE_USER_WATCH_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * @param userId Integer containing user Id for WHERE clause
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing user watchs rows from the database
     */
    public Cursor getUserWatchs_old(Integer userId, Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " +
                        UserWatchs.TABLE_USER_WATCH_NAME +
                        " WHERE " + UserWatchs.USER_WATCH_USER + " = ? " +
                        " AND " + UserWatchs.USER_WATCH_STATUS + " = 1 " +
                        " LIMIT 10 OFFSET " + offset,
                new String[] { userId.toString() });
        return res;
    }

    /**
     * @param userId Integer containing user Id for WHERE clause
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing user watchs rows from the database
     */
    public Cursor getUserWatchs(Integer userId, Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT W.user_watchs_id, W.user_id, P.title, P.image, P.title, P.price, P.stock" +
                        " FROM " + UserWatchs.TABLE_USER_WATCH_NAME + " W, " + Products.TABLE_PRODUCT_NAME + " P " +
                        " WHERE W.user_watchs_id = P.product_id" +
                        " AND " + UserWatchs.USER_WATCH_USER + " = ? " +
                        " AND P." + UserWatchs.USER_WATCH_STATUS + " = 1 " +
                        " LIMIT 10 OFFSET " + offset,
                new String[] { userId.toString() });
        return res;
    }

    /**
     * @param userId Integer containing user Id for WHERE clause
     * @return Integer containing number of user watchs found
     */
    public Integer getUserWatchsNumber(Integer userId){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,
                UserWatchs.TABLE_USER_WATCH_NAME,
                UserWatchs.USER_WATCH_USER + " = ?" +
                        " AND " + UserWatchs.USER_WATCH_STATUS + " = 1 ",
                new String[] { userId.toString() });
    }


    /* User Offer CRUD */

    /**
     * @param userOffer Model containing system's user offer data
     * @return Boolean true if success
     */
    public boolean insertUserOfferData(UserOffer userOffer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(UserOffers.USER_OFFER_USER, userOffer.userId);
        contentValues.put(UserOffers.USER_OFFER_SELLER, userOffer.sellerId);
        contentValues.put(UserOffers.USER_OFFER_PRODUCT, userOffer.productId);
        contentValues.put(UserOffers.USER_OFFER_PRICE, userOffer.price);
        contentValues.put(UserOffers.USER_OFFER_NEWPRICE, userOffer.newPrice);
        contentValues.put(UserOffers.USER_OFFER_STATUS, (userOffer.status ? 1 : 0));
        contentValues.put(UserOffers.USER_OFFER_CREATION,
                new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));

        long result = db.insert(UserOffers.TABLE_USER_OFFER_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    /**
     * @param userId Integer containing user Id for WHERE clause
     * @param offset Integer containing index where the SELECT query should start retrieving rows
     * @return Cursor containing user offers rows from the database
     */
    public Cursor getUserOffers(Integer userId, Integer offset){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " +
                        UserOffers.TABLE_USER_OFFER_NAME +
                        " WHERE " + UserOffers.USER_OFFER_USER + " = ? " +
                        " AND " + UserOffers.USER_OFFER_STATUS + " = 1 " +
                        " LIMIT 10 OFFSET " + offset,
                new String[] { userId.toString() });
        return res;
    }

    /**
     * @param userId Integer containing user Id for WHERE clause
     * @return Integer containing number of user offers found
     */
    public Integer getUserOffersNumber(Integer userId){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,
                UserOffers.TABLE_USER_OFFER_NAME,
                UserOffers.USER_OFFER_USER + " = ?" +
                        " AND " + UserOffers.USER_OFFER_STATUS + " = 1 ",
                new String[] { userId.toString() });
    }

}