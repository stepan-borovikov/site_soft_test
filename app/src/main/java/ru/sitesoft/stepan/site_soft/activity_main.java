package ru.sitesoft.stepan.site_soft;

import android.app.Fragment;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ru.sitesoft.stepan.site_soft.app.cls_app;
import ru.sitesoft.stepan.site_soft.fragments.fragment_companies;
import ru.sitesoft.stepan.site_soft.fragments.fragment_contact;
import ru.sitesoft.stepan.site_soft.fragments.fragment_department_about;
import ru.sitesoft.stepan.site_soft.fragments.fragment_departments;
import ru.sitesoft.stepan.site_soft.fragments.fragment_news;
import ru.sitesoft.stepan.site_soft.items.item_company;

public class activity_main extends AppCompatActivity implements fragment_companies.OnSelectCompanies, fragment_departments.OnSelectDepartment {
    Toolbar toolbar;
    private DrawerLayout                        MainLayout;
    private FrameLayout                         leftMNU;
    private ListView                            menu_list;
    private ActionBarDrawerToggle               myDrawerToggle;
    private android.support.v7.app.ActionBar    actionBar;

    private Boolean MNU_is_open     = false;

    // navigation drawer title
    private CharSequence myDrawerTitle;
    private CharSequence myTitle;

    private String[] MNU_items_array;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_main);

        MNU_items_array = getResources().getStringArray(R.array.MNU_items);
        MainLayout      = (DrawerLayout) findViewById(R.id.drawer_layout);

        leftMNU         = (FrameLayout) findViewById(R.id.left_frame);
        menu_list       = (ListView) findViewById(R.id.left_drawer);

        adapter ad = new adapter(this,0);
        List<String> wordList = Arrays.asList(MNU_items_array);
        ad.setData(wordList);
        menu_list.setAdapter(ad);
        ad.notifyDataSetChanged();

        menu_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(position);
            }
        });



        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setTitle(R.string.app_name);

        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(R.string.app_name);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        myDrawerToggle = new ActionBarDrawerToggle(this, MainLayout, 0, 0 ) {
            public void onDrawerClosed(View view) {
                MNU_is_open = false;
            }

            public void onDrawerOpened(View drawerView) {
                MNU_is_open = true;
            }

        };


        MainLayout.setDrawerListener(myDrawerToggle);

        if (savedInstanceState == null) {
            displayView(0);
        }
    }

    String id_company       = "0";
    String id_department    = "0";

    private static final int STATE0 = 0;
    private static final int STATE1 = 1;
    private static final int STATE2 = 2;
    private int current_state           = STATE0;

    @Override
    public void OnSelectCompanies(String i_id_company) {
        cls_app.log("Выбрали компанию", 0);
        id_company = i_id_company;
        current_state = STATE1;
        display_distonary();
    }

    @Override
    public void OnSelectDepartment(String i_id_department) {
        cls_app.log("Выбрали подразделение", 0);
        id_department = i_id_department;
        current_state = STATE2;
        display_distonary();
    }

    private void display_distonary(){
        Fragment fragment = null;
        android.app.FragmentManager fragmentManager = null;
        switch (current_state){
            case STATE0:
                displayView(0);
                break;
            case STATE1:
                fragment_departments f_fragment_departments = new fragment_departments();
                f_fragment_departments.setId_company(id_company);
                actionBar.setTitle(R.string.second_screen);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, f_fragment_departments).commit();
                break;
            case STATE2:
                fragment_department_about f_fragment_department_about = new fragment_department_about();
                f_fragment_department_about.setId_company(id_department);
                actionBar.setTitle(R.string.third_screen);
                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, f_fragment_department_about).commit();
                break;
        }
    }

    int current_frgment = 0;
    private void displayView(int position) {

        MainLayout.closeDrawer(leftMNU);

        current_frgment = position;

        // update the main content by replacing fragments
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new fragment_companies();
                actionBar.setTitle(R.string.first_screen);
                break;
            case 1:
                fragment = new fragment_news();
                actionBar.setTitle("Новости");
                break;
            case 2:
                fragment = new fragment_contact();
                actionBar.setTitle("Контакты");
                break;
            default:
                break;
        }

        if (fragment != null) {

            android.app.FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

            menu_list.setItemChecked(position, true);
            menu_list.setSelection(position);
            //setTitle(MNU_items_array[position]);


        } else {

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            if (current_frgment == 0 && (current_state == STATE1 || current_state == STATE2)){
                current_state           = STATE0;
                display_distonary();
                return false;
            }else if (current_frgment == 1 || current_frgment == 2) {
                current_state           = STATE0;
                displayView(0);
                return false;
            }else{
                return super.onKeyDown(keyCode, event);
            }

        }else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            if (!MNU_is_open) {
                MainLayout.openDrawer(leftMNU);
            }else {
                MainLayout.closeDrawer(leftMNU);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    class adapter extends ArrayAdapter<String> {
        private Context p_context;
        private List<String> p_values;
        private ArrayList<DataSetObserver> p_observers;

        public adapter(Context context, int resource) {
            super(context, resource);
            p_context           = context;
            p_observers         = new ArrayList<DataSetObserver>(1);
            p_values            = new ArrayList<String>();
        }

        public void setData(List<String> myServices){
            p_values.clear();
            for(String ms : myServices){
                p_values.add(ms);
            }

            for(DataSetObserver observer : p_observers){
                observer.onChanged();
            }
        }

        @Override
        public String getItem(int position) {
            return p_values.get(position);
        }

        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            if(p_observers.contains(observer))
                return;

            p_observers.add(observer);
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            if(p_observers.contains(observer))
                p_observers.remove(observer);
        }

        @Override
        public int getCount() {
            if(p_values == null)
                return 0;

            return p_values.size();
        }

        @Override
        public long getItemId(int position) {
            return -1;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) p_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            String value = p_values.get(position);

            View        rowView     = null;
            TextView textView    = null;
            rowView = inflater.inflate(R.layout.list_item_company, parent, false);
            textView = (TextView) rowView.findViewById(R.id.name);
            textView.setText(value);
            return rowView;
        }
    }
}
