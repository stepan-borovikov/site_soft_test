package ru.sitesoft.stepan.site_soft.app;

import android.app.Application;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Administrator on 8/31/2015.
 */
public class cls_app extends Application {
    private static String TAG = "site_soft";
    private static cls_SQL p_arket_cls;
    public  static SQLiteDatabase p_arket_db;

    @Override
    public void onCreate() {
        super.onCreate();
        log("запуск прложения", 0);
        p_arket_cls = new cls_SQL(this);
        p_arket_db  = p_arket_cls.getWritableDatabase();
        log("запись данных в базу данных", 0);
        insert_data_to_DB();
    }

    public static void log(String value, int level)
    {
        Log.w(TAG, value);
    }

    private void insert_data_to_DB(){
        p_arket_db.execSQL("DELETE FROM company");
        p_arket_db.execSQL("DELETE FROM departments");
        p_arket_db.execSQL("DELETE FROM news");

        ContentValues cv = new ContentValues();
        log("       шишем компании", 0);
        cv.clear();
        cv.put("id", "1");
        cv.put("name", "\"SITESOFT\"");
        p_arket_db.insert("company", null, cv);

        cv.clear();
        cv.put("id", "2");
        cv.put("name", "\"ИТ-Трэйдинг\"");
        p_arket_db.insert("company", null, cv);

        cv.clear();
        cv.put("id", "3");
        cv.put("name", "\"Огородники России\"");
        p_arket_db.insert("company", null, cv);

        log("       шишем подразделения", 0);
        cv.clear();
        cv.put("id", "1");
        cv.put("id_company",    "1");
        cv.put("name", "финансовый отдел");
        p_arket_db.insert("departments", null, cv);
        cv.clear();
        cv.put("id", "2");
        cv.put("id_company",    "1");
        cv.put("name", "торговый отдел");
        p_arket_db.insert("departments", null, cv);
        cv.clear();
        cv.put("id", "3");
        cv.put("id_company",    "1");
        cv.put("name", "it отдел");
        p_arket_db.insert("departments", null, cv);

        cv.clear();
        cv.put("id", "4");
        cv.put("id_company",    "2");
        cv.put("name", "управление персоналом");
        p_arket_db.insert("departments", null, cv);
        cv.clear();
        cv.put("id", "5");
        cv.put("id_company",    "2");
        cv.put("name", "менеджеры региональных продаж");
        p_arket_db.insert("departments", null, cv);

        cv.clear();
        cv.put("id", "6");
        cv.put("id_company",    "3");
        cv.put("name", "рекламмный отдел");
        p_arket_db.insert("departments", null, cv);

        cv.clear();
        cv.put("id", "1");
        cv.put("name", "О водных ресурсах");
        p_arket_db.insert("news", null, cv);

        cv.clear();
        cv.put("id", "2");
        cv.put("name", "О политике");
        p_arket_db.insert("news", null, cv);

        cv.clear();
        cv.put("id", "3");
        cv.put("name", "О власти");
        p_arket_db.insert("news", null, cv);
    }
}
