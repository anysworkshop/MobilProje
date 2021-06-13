package tr.edu.yildiz.mywardrobe;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

public class dbOperation extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ytuDB";

    private static final String TABLE_NAME = "Users";
    private static String USERID = "userID";
    private static String NAME = "name";
    private static String SURNAME = "surName";
    private static String EMAIL = "email";
    private static String PASSWORD = "password";
    private static String PROFILEPICTURE = "profilePicture";

    private static final String TABLE_NAME2 = "Clothes";
    private static String CLOTHES_ID = "clothesID";
    private static String PART = "part";
    private static String TYPE = "type";
    private static String COLOR = "color";
    private static String PATTERN = "pattern";
    private static String DATE = "date";
    private static String PRICE = "price";
    private static String CLOTHES_IMAGE = "clothesImage";
    private static String DRAWER_ID = "drawerID";

    private static final String TABLE_NAME_3 = "Drawers";
    //DRAWER ID INITIALIZED BEFORE
    private static String USER_ID = "userID";
    private static String DRAWER_NAME = "name";

    private static final String TABLE_NAME_4 = "Parts";
    private static String PART_ID = "partID";
    //PART INITIALIZED BEFORE

    private static final String TABLE_NAME_5 = "Combinations";
    private static String COMBINATION_ID = "combinationID";
    //userID
    //name
    private static String HEAD = "head";
    private static String FACE = "face";
    private static String TOP = "top";
    private static String BOTTOM = "bottom";
    private static String FOOTWEAR = "footwear";


    public dbOperation(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {  // Databesi oluşturuyoruz.Bu methodu biz çağırmıyoruz. Databese de obje oluşturduğumuzda otamatik çağırılıyor.

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + USERID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NAME + " VARCHAR,"
                + SURNAME + " VARCHAR,"
                + EMAIL + " VARCHAR UNIQUE,"
                + PASSWORD + " VARCHAR,"
                + PROFILEPICTURE + " BLOB " + ")";
        db.execSQL(CREATE_TABLE);

        String CREATE_TABLE4 = "CREATE TABLE " + TABLE_NAME_4 + "("
                + PART_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PART + " VARCHAR" + ")";
        db.execSQL(CREATE_TABLE4);

        String INSERT_PART = "INSERT INTO " + TABLE_NAME_4 + "("+PART_ID+ ","+ PART + ") VALUES (1,'head'),(2,'face'),(3,'top'),(4,'bottom'),(5,'footwear')";
        db.execSQL(INSERT_PART);
        String CREATE_TABLE2 = "CREATE TABLE " + TABLE_NAME2 + "("
                + CLOTHES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PART + " INTEGER,"
                + TYPE + " VARCHAR,"
                + COLOR + " VARCHAR,"
                + PATTERN + " VARCHAR,"
                + DATE + " VARCHAR, "
                + PRICE + " INTEGER, "
                + CLOTHES_IMAGE + " BLOB, "
                + DRAWER_ID + " INTEGER, "
                + " FOREIGN KEY ("+PART+") REFERENCES "+TABLE_NAME_4+"("+PART_ID+"))";
        db.execSQL(CREATE_TABLE2);
        String CREATE_TABLE3 = "CREATE TABLE " + TABLE_NAME_3 + "("
                + DRAWER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USER_ID + " INTEGER,"
                + DRAWER_NAME + " VARCHAR UNIQUE" + ")";
        db.execSQL(CREATE_TABLE3);

        String CREATE_TABLE5 = "CREATE TABLE " + TABLE_NAME_5 + "("
                + COMBINATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + USERID + " INTEGER,"
                + NAME + " VARCHAR,"
                + HEAD + " INTEGER,"
                + FACE + " INTEGER,"
                + TOP + " INTEGER, "
                + BOTTOM + " INTEGER, "
                + FOOTWEAR + " INTEGER" + ")";
        db.execSQL(CREATE_TABLE5);

    }

    public boolean createCombination(int userID, String name) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERID, userID);
        values.put(NAME, name);

        long result = db.insert(TABLE_NAME_5, null, values);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }

    public boolean combinationUpdateHead(int combinationID,int clothesID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(HEAD, clothesID);


        long result = db.update(TABLE_NAME_5,values,"combinationID=?",new String[] {String.valueOf(combinationID)});
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean signUpUser(String name, String surName,String email,String password,byte[] byteArray) {
        //üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(SURNAME, surName);
        values.put(EMAIL, email);
        values.put(PASSWORD, password);
        values.put(PROFILEPICTURE, byteArray);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }


        //Database Bağlantısını kapattık*/
    }

    public boolean clothesAdd(Clothes clothes) {
        //üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PART, clothes.getPart());
        values.put(TYPE, clothes.getType());
        values.put(COLOR, clothes.getColor());
        values.put(PATTERN, clothes.getPattern());
        values.put(DATE, clothes.getDate());
        values.put(PRICE, clothes.getPrice());
        values.put(CLOTHES_IMAGE,clothes.getByteArrays());
        values.put(DRAWER_ID, clothes.getDrawerID());

        long result = db.insert(TABLE_NAME2, null, values);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
        //Database Bağlantısını kapattık*/
    }

    public boolean clothesDelete(Clothes clothes){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME2,"clothesID=?",new String[]{String.valueOf(clothes.getClothesID())});
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean drawerAdd(Drawer drawer) {
        //üstünde Databese veri eklemek için
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERID, drawer.getUserID());
        values.put(DRAWER_NAME, drawer.getName());

        long result = db.insert(TABLE_NAME_3, null, values);
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
        //Database Bağlantısını kapattık*/
    }

    public boolean drawerDelete(Drawer drawer){

        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_3,"drawerID=?",new String[]{String.valueOf(drawer.getDrawerID())});
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public int drawerTakeID(String drawerName){

        int tempDrawerID = 999;

        String drawerQuery = "SELECT * FROM Drawers WHERE name = '" + drawerName + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(drawerQuery, null);
        if (cursor.getCount() <= 0) {

            cursor.close();
            return 999;
        } else {
            int i=1;
            int count=0;
            cursor.moveToFirst();
            while(count<cursor.getCount()){
                count++;
                tempDrawerID = cursor.getInt(0);
                cursor.moveToNext();
            }
        }

        return tempDrawerID;
    }

    public int userTakeID(String email){

        int tempDrawerID = 999;

        String drawerQuery = "SELECT * FROM Users WHERE email = '" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(drawerQuery, null);
        if (cursor.getCount() <= 0) {

            cursor.close();
            return 999;
        } else {
            int i=1;
            int count=0;
            cursor.moveToFirst();
            while(count<cursor.getCount()){
                count++;
                tempDrawerID = cursor.getInt(0);
                cursor.moveToNext();
            }
        }

        return tempDrawerID;
    }

    public boolean clothesUpdate(Clothes clothes,int clothesID){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PART, clothes.getPart());
        values.put(TYPE, clothes.getType());
        values.put(COLOR, clothes.getColor());
        values.put(PATTERN, clothes.getPattern());
        values.put(DATE, clothes.getDate());
        values.put(PRICE, clothes.getPrice());
        values.put(CLOTHES_IMAGE,clothes.getByteArrays());
        values.put(DRAWER_ID, clothes.getDrawerID());


        long result = db.update(TABLE_NAME2,values,"clothesID=?",new String[] {String.valueOf(clothesID)});
        db.close();
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<Clothes> takeAllClothes(int tempDrawerID){
        ArrayList<Clothes> clothes = new ArrayList<>();


        String clothesQuery = "SELECT * FROM Clothes WHERE drawerID = '" + tempDrawerID + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(clothesQuery, null);
        if (cursor.getCount() <= 0) {

            cursor.close();
            return null;
        } else {
            int i=1;
            int count=0;
            cursor.moveToFirst();
            while(count<cursor.getCount()){
                count++;
                clothes.add(new Clothes(cursor.getInt(0),
                        cursor.getString(i),
                        cursor.getString(i+1),
                        cursor.getString(i+2),
                        cursor.getString(i+3),
                        cursor.getString(i+4),
                        cursor.getInt(i+5),
                        cursor.getBlob(i+6),
                        cursor.getInt(i+7)));
                cursor.moveToNext();
            }
        }

        return clothes;
    }

    public ArrayList<Drawer> takeAllDrawers(String tempUserEmail){
        ArrayList<Drawer> drawers = new ArrayList<>();


        //String questionsQuery = "SELECT * FROM Drawers WHERE userID = " +"(SELECT userID FROM Users WHERE email = '" + tempUserEmail + "')";
        String questionsQuery = "SELECT * FROM Drawers WHERE EXISTS (SELECT * FROM Users WHERE  Users.userID = Drawers.userID and Users.email = '"+tempUserEmail+"');";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(questionsQuery, null);
        if (cursor.getCount() <= 0) {

            cursor.close();
            return null;
        } else {
            int i=1;
            int count=0;
            cursor.moveToFirst();
            while(count<cursor.getCount()){
                count++;
                drawers.add(new Drawer(cursor.getInt(0),
                        cursor.getInt(i),
                        cursor.getString(i+1)));
                cursor.moveToNext();
            }
        }

        return drawers;
    }


    public ArrayList<Drawer> takeAllDrawers(int tempUserID){
        ArrayList<Drawer> drawers = new ArrayList<>();


        String questionsQuery = "SELECT * FROM Drawers WHERE userID = '" + tempUserID + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(questionsQuery, null);
        if (cursor.getCount() <= 0) {

            cursor.close();
            return null;
        } else {
            int i=1;
            int count=0;
            cursor.moveToFirst();
            while(count<cursor.getCount()){
                count++;
                drawers.add(new Drawer(cursor.getInt(0),
                        cursor.getInt(i),
                        cursor.getString(i+1)));
                cursor.moveToNext();
            }
        }

        return drawers;
    }

    public User login(String email,String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        String loginQuery = "SELECT * FROM Users WHERE email = '" + email + "' and password = '" + password + "'";
        Cursor cursor = db.rawQuery(loginQuery, null);

        User user = new User();

        if (cursor.getCount() <= 0) {
            cursor.close();
            return null;
        } else {
            cursor.moveToFirst();
            user.setName(cursor.getString(1));
            user.setSurName(cursor.getString(2));
            user.setEmail(cursor.getString(3));
            user.setPassword(cursor.getString(4));

            byte[] bytes = cursor.getBlob(5);
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
            user.setImage(bitmap);
            cursor.close();
            return user;
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}