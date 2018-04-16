package com.example.user.trackingmapusingfrosquire.Controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.trackingmapusingfrosquire.Model.Tb_User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 06-01-2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="SmartTraveller";

    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_USER = "Tb_User";

    private static final String TABLE_USER2 = "Tb_UserNew";

    private static final String TABLELoc = "Tb_Loc";

    String pics=new String();

    SQLiteDatabase db;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db= this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        String CREATE_USER_TABLE="create table "+TABLE_USER+"(Id integer primary key autoincrement,Name text,Email text,Phone text,Username text,Password text,Food text,Place text)";

        String CreateTableLoc="create table "+TABLELoc+"(Id integer primary key autoincrement,Uid integer,map text)";

        String CREATE_USER_TABLE2="create table "+TABLE_USER2+"(Id integer primary key autoincrement,Name text,Email text,Phone text,Username text,Password text,Food text,Place text,Holy text,TopPics text)";

        db.execSQL(CreateTableLoc);
        db.execSQL(CREATE_USER_TABLE2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLELoc);
        onCreate(db);


    }


    public String AddLoc(JSONArray array,String uid)
    { String out=new String();
        try {




            String qurey = "select * from " + TABLELoc + " where Uid='" + uid + "'";
            Cursor cursor = db.rawQuery(qurey, null);
            if (cursor.moveToFirst()) {

                do {
                    ContentValues values = new ContentValues();
                    values.put("map",array.toString());


                    db.update(TABLELoc,values,"Uid="+uid,null);
                    out = "Inserted Successfully";
                } while (cursor.moveToNext());

            } else {
                ContentValues values = new ContentValues();
                values.put("Uid",uid);
                values.put("map",array.toString());
                String qq = "insert into " + TABLELoc + " values(null,'" + uid + "','" + array.toString() + "')";

                db.insert(TABLELoc,null,values);
                out = "Inserted Successfully";
            }
            db.close();

        }catch (Exception e)
        {
            Log.e("Exception @ Click",e+"");
        }
        return out;
    }

   public String addUser(Tb_User tb_user)
    {
        String out;


        String qurey="select * from "+TABLE_USER2;

        Cursor cursor=db.rawQuery(qurey,null);

        if(cursor.moveToFirst()) {

            do {

                if(cursor.getString(4).equals(tb_user.getUsername())||cursor.getString(5).equals(tb_user.getPassword()))
                {
                    out="UserName or Password already taken";

                }
                else
                    {
                        ContentValues values = new ContentValues();
                        values.put("Name", tb_user.getName());
                        values.put("Email", tb_user.getEmail());
                        values.put("Phone", tb_user.getPhone());
                        values.put("Username", tb_user.getUsername());
                        values.put("Password", tb_user.getPassword());
                        values.put("Food", tb_user.getFood());
                        values.put("Place", tb_user.getPlace());
                        values.put("Holy", tb_user.getHoly());
                        values.put("TopPics", tb_user.getTopPics());
                        db.insert(TABLE_USER2, null, values);

                        out="Inserted Successfully";
                    }

            }while(cursor.moveToNext());

        }
        else {
            ContentValues values = new ContentValues();
            values.put("Name", tb_user.getName());
            values.put("Email", tb_user.getEmail());
            values.put("Phone", tb_user.getPhone());
            values.put("Username", tb_user.getUsername());
            values.put("Password", tb_user.getPassword());
            values.put("Food", tb_user.getFood());
            values.put("Place", tb_user.getPlace());
            values.put("Holy", tb_user.getHoly());
            values.put("TopPics", tb_user.getTopPics());
            db.insert(TABLE_USER2, null, values);

            out="Inserted Successfully";
        }
        db.close();
       return out;
    }

    public String updateUser(Tb_User tb_user,String uid)
    {
        String out;


        String qurey="update "+TABLE_USER2+" set Name='"+tb_user.getName()+"',Email='"+tb_user.getEmail()+"',Phone='"+tb_user.getPhone()+"',Username='"+tb_user.getUsername()+"',Password='"+tb_user.getPassword()+"',Food='"+tb_user.getFood()+"',Place='"+tb_user.getPlace()+"',Holy='"+tb_user.getHoly()+"',TopPics='"+tb_user.getTopPics()+"' where Id='"+uid+"'";
        db.execSQL(qurey);
        out="Success";
             db.close();
        return out;
    }


    public String login_check(String usrname,String password)
    {

        String Query="select * from "+TABLE_USER2+" where Username='"+usrname+"' and Password='"+password+"'";

        String out="";
        Cursor cursor=db.rawQuery(Query,null);
        if(cursor.moveToFirst())
        {
            do{

                out=cursor.getString(0)+"&"+"Success";

            }while(cursor.moveToNext());

        }
        else
        {

            out="Faild"+"&"+"Faild";


        }
        db.close();
        return out;

    }




    public List<Tb_User> getAllContacts() {
        List<Tb_User> tb_usersList = new ArrayList<Tb_User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER2;


        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Tb_User tb_User = new Tb_User();
                tb_User.setId(Integer.parseInt(cursor.getString(0)));
                tb_User.setName(cursor.getString(1));
                tb_User.setEmail(cursor.getString(2));
                tb_User.setPhone(cursor.getString(3));
                tb_User.setUsername(cursor.getString(4));
                tb_User.setPassword(cursor.getString(5));
                tb_User.setFood(cursor.getString(6));
                tb_User.setPlace(cursor.getString(7));

                tb_usersList.add(tb_User);
            } while (cursor.moveToNext());
        }

        // return contact list
        db.close();
        return tb_usersList;
    }


    public String getAllIntrests(String id) {


        String selectQuery = "SELECT  * FROM " + TABLE_USER2+" WHERE Id='"+id+"'";


        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {


              String place=cursor.getString(7);
              String food=cursor.getString(6);
              String holy=cursor.getString(8);
              String toppics=cursor.getString(9);

                pics=place+","+food+","+holy+","+toppics;

            } while (cursor.moveToNext());
        }

        db.close();
        return pics;
    }

    public String GetALLLoc(String id) {

        String place=new String();
        String selectQuery = "SELECT  * FROM " + TABLELoc+" WHERE Uid='"+id+"'";


        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {


                place =cursor.getString(2);



            } while (cursor.moveToNext());
        }

        db.close();
        return place;
    }


    public String Delete(String id) {
        SQLiteDatabase db2=getWritableDatabase();
        String place=new String();
        String selectQuery = "DELETE FROM " + TABLELoc+" WHERE Uid='"+id+"'";
Log.e("Query",selectQuery);
        db2.execSQL(selectQuery);
//
        db2.close();
        return place;
    }

}
