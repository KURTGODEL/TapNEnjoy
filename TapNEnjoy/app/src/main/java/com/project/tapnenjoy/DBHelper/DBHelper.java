package com.project.tapnenjoy.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
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

    public boolean insertUserData(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Users.USER_NAME, user.name);
        contentValues.put(Users.USER_USERNAME, user.userName);
        contentValues.put(Users.USER_PASSWORD, user.password);
        contentValues.put(Users.USER_ADDRESS, user.address);
        contentValues.put(Users.USER_LATITUDE, user.latitude);
        contentValues.put(Users.USER_LONGITUDE, user.longitude);
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

    public boolean updateUserData(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Users.USER_NAME, user.name);
        contentValues.put(Users.USER_USERNAME, user.userName);
        contentValues.put(Users.USER_PASSWORD, user.password);
        contentValues.put(Users.USER_ADDRESS, user.address);
        contentValues.put(Users.USER_LATITUDE, user.latitude);
        contentValues.put(Users.USER_LONGITUDE, user.longitude);
        contentValues.put(Users.USER_STATUS, (user.status ? 1 : 0));
        contentValues.put(Users.USER_UPDATION,
                new SimpleDateFormat("yyyyMMddHHmmssSS").format(new Date()));

        db.update(Users.TABLE_USER_NAME,
                contentValues,
                Users.USER_ID + " = ?",
                new String[] { user.Id.toString() });

        return true;
    }

    public Cursor getUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Users.TABLE_USER_NAME, null);
        return res;
    }

    public Cursor getSellers(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Sellers.TABLE_SELLER_NAME, null);
        return res;
    }

    public Integer deleteUser(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(
                Users.TABLE_USER_NAME,
                Users.USER_ID + " = ?",
                new String[] { id.toString() });
    }


    /* Product CRUD */

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

        long result = db.insert(Products.TABLE_PRODUCT_NAME, null, contentValues);

        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

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

    public Cursor getProducts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + Products.TABLE_PRODUCT_NAME, null);
        return res;
    }

    public Integer deleteProduct(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(
                Products.TABLE_PRODUCT_NAME,
                Products.PRODUCT_ID + " = ?",
                new String[] { id.toString() });
    }


    /* User Order CRUD */

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

    public Integer getUserOrdersNumber(Integer userId){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,
                UserOrders.TABLE_USER_ORDER_NAME,
                UserOrders.USER_ORDER_USER + " = ?" +
                " AND " + UserOrders.USER_ORDER_STATUS + " = 1 ",
                new String[] { userId.toString() });
    }

    public Cursor getUserOrders(Integer userId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " +
                        UserOrders.TABLE_USER_ORDER_NAME +
                        " WHERE " + UserOrders.USER_ORDER_USER + " = ?" +
                        " AND " + UserOrders.USER_ORDER_STATUS + " = 1 ",
                new String[] { userId.toString() });
        return res;
    }


    /* User Watch CRUD */

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

    public Cursor getUserWatchs(Integer userId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " +
                        UserWatchs.TABLE_USER_WATCH_NAME +
                        " WHERE " + UserWatchs.USER_WATCH_USER + " = ? " +
                        " AND " + UserWatchs.USER_WATCH_STATUS + " = 1 ",
                new String[] { userId.toString() });
        return res;
    }

    public Integer getUserWatchsNumber(Integer userId){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,
                UserWatchs.TABLE_USER_WATCH_NAME,
                UserWatchs.USER_WATCH_USER + " = ?" +
                        " AND " + UserWatchs.USER_WATCH_STATUS + " = 1 ",
                new String[] { userId.toString() });
    }


    /* User Offer CRUD */

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

    public Cursor getUserOffers(Integer userId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery(
                "SELECT * FROM " +
                        UserOffers.TABLE_USER_OFFER_NAME +
                        " WHERE " + UserOffers.USER_OFFER_USER + " = ? " +
                        " AND " + UserOffers.USER_OFFER_STATUS + " = 1 ",
                new String[] { userId.toString() });
        return res;
    }

    public Integer getUserOffersNumber(Integer userId){
        SQLiteDatabase db = this.getReadableDatabase();
        return (int)DatabaseUtils.queryNumEntries(db,
                UserOffers.TABLE_USER_OFFER_NAME,
                UserOffers.USER_OFFER_USER + " = ?" +
                        " AND " + UserOffers.USER_OFFER_STATUS + " = 1 ",
                new String[] { userId.toString() });
    }

}
