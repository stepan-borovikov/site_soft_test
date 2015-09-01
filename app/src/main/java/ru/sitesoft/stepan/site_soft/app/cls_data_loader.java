package ru.sitesoft.stepan.site_soft.app;

import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import ru.sitesoft.stepan.site_soft.items.item_company;
import ru.sitesoft.stepan.site_soft.items.item_department;
import ru.sitesoft.stepan.site_soft.items.item_news;

/**
 * Created by Administrator on 8/31/2015.
 */
public class cls_data_loader {

    public List<item_company> get_items_companies(){
        List<item_company> return_array = new ArrayList<item_company>();
        Cursor c = cls_app.p_arket_db.query("company", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                item_company item = new item_company();
                    item.setId(c.getString(c.getColumnIndex("id")));
                    item.setName(c.getString(c.getColumnIndex("name")));
                return_array.add(item);
            } while (c.moveToNext());

        }
        return return_array;
    }


    public List<item_department> get_items_department(String id_company){
        List<item_department> return_array = new ArrayList<item_department>();
        Cursor c = cls_app.p_arket_db.rawQuery("SELECT id,id_company,name FROM departments WHERE id_company = " + id_company, null);
        if (c.moveToFirst()) {
            do {
                item_department item = new item_department();
                    item.setId(c.getString(c.getColumnIndex("id")));
                    item.setId_company(c.getString(c.getColumnIndex("id_company")));
                    item.setName(c.getString(c.getColumnIndex("name")));
                return_array.add(item);
            } while (c.moveToNext());
        }
        return return_array;
    }

    public List<item_news> get_items_news(){
        List<item_news> return_array = new ArrayList<item_news>();
        Cursor c = cls_app.p_arket_db.query("news", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                item_news item = new item_news();
                item.setId(c.getString(c.getColumnIndex("id")));
                item.setName(c.getString(c.getColumnIndex("name")));
                return_array.add(item);
            } while (c.moveToNext());

        }
        return return_array;
    }

    public String get_items_department_about(String id_company){
        String return_string        = "";
        String department_name      = "";
        String company_name         = "";
        Cursor c = cls_app.p_arket_db.rawQuery("SELECT departments.id,departments.name, departments.id_company, company.name as company_name FROM departments join company on departments.id_company = company.id WHERE departments.id = " + id_company, null);
        if (c.moveToFirst()) {
            do {
                department_name     = (c.getString(c.getColumnIndex("name")));
                company_name        = (c.getString(c.getColumnIndex("company_name")));
            } while (c.moveToNext());
        }
        return_string = "Отдел " + department_name + " компании " + company_name;
        return_string = return_string + "\n";
        return_string = return_string + " Один из самых значимых в нашей компании";
        return_string = return_string + "\n";
        return_string = return_string + " Отдел занят разработкой решений для...";
        return return_string;
    }
}
