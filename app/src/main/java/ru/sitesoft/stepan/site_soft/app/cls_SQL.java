package ru.sitesoft.stepan.site_soft.app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by stepan on 26.01.15.
 */
public class cls_SQL extends SQLiteOpenHelper {

    private static String p_db_name     = "DB";
    private static int p_db_version     = 2;

    public cls_SQL(Context context) {
        super(context, p_db_name, null, p_db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(p_db_version == 2)
        {
            db.execSQL("create table company ("
                    + "id       text,"
                    + "name     text"
                    + ");");

            db.execSQL("create table departments ("
                    + "id               text,"
                    + "id_company       text,"
                    + "name             text"
                    + ");");

            db.execSQL("create table news ("
                    + "id       text,"
                    + "name     text"
                    + ");");

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if          (oldVersion == 1 && newVersion == 2) {
            db.execSQL("create table news ("
                    + "id       text,"
                    + "name     text"
                    + ");");
        }



    }

}
