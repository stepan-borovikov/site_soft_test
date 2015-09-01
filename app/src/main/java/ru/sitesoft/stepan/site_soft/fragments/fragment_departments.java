package ru.sitesoft.stepan.site_soft.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.sitesoft.stepan.site_soft.R;
import ru.sitesoft.stepan.site_soft.app.cls_data_loader;
import ru.sitesoft.stepan.site_soft.items.item_company;
import ru.sitesoft.stepan.site_soft.items.item_department;

public class fragment_departments extends Fragment {
    private String id_company;
    private List<item_department> department_list;

    private OnSelectDepartment mListener;

    public static fragment_departments newInstance() {
        fragment_departments fragment = new fragment_departments();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_departments() {

    }

    public void setId_company(String id){
        id_company = id;
        //refresh();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        department_list = new ArrayList<item_department>();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void refresh(){
        cls_data_loader loader = new cls_data_loader();
        department_list = loader.get_items_department(id_company);
        mAdapter.setData(department_list);
        mAdapter.notifyDataSetChanged();

        ui_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item_department item = (item_department)parent.getAdapter().getItem(position);
                mListener.OnSelectDepartment(item.getId());
            }
        });
    }

    View        v;
    ListView    ui_list;
    adapter     mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_fragment_companies, container, false);
        ui_list = (ListView)v.findViewById(R.id.listView);
        mAdapter = new adapter(this.getActivity(),0);
        ui_list.setAdapter(mAdapter);
        refresh();
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSelectDepartment) activity;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSelectDepartment {
        public void OnSelectDepartment(String id_department);
    }


    class adapter extends ArrayAdapter<item_department> {
        private Context                     p_context;
        private List<item_department>          p_values;
        private ArrayList<DataSetObserver>  p_observers;

        public adapter(Context context, int resource) {
            super(context, resource);
            p_context           = context;
            p_observers         = new ArrayList<DataSetObserver>(1);
            p_values            = new ArrayList<item_department>();
        }

        public void setData(List<item_department> myServices){
            p_values.clear();
            for(item_department ms : myServices){
                p_values.add(ms);
            }

            for(DataSetObserver observer : p_observers){
                observer.onChanged();
            }
        }

        @Override
        public item_department getItem(int position) {
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
            item_department oi = new item_department();
            oi = p_values.get(position);

            View        rowView     = null;
            TextView    textView    = null;
                rowView = inflater.inflate(R.layout.list_item_company, parent, false);
                textView = (TextView) rowView.findViewById(R.id.name);
                textView.setText(oi.getName());
            return rowView;
        }
    }
}
