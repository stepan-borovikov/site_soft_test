package ru.sitesoft.stepan.site_soft.fragments;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.sitesoft.stepan.site_soft.R;
import ru.sitesoft.stepan.site_soft.app.cls_data_loader;
import ru.sitesoft.stepan.site_soft.items.item_company;

public class fragment_companies extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<item_company> companies_list;

    private OnSelectCompanies mListener;

    public static fragment_companies newInstance(String param1, String param2) {
        fragment_companies fragment = new fragment_companies();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_companies() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        companies_list = new ArrayList<item_company>();
    }

    @Override
    public void onResume() {
        super.onResume();
        cls_data_loader loader = new cls_data_loader();
        companies_list = loader.get_items_companies();
        mAdapter.setData(companies_list);
        mAdapter.notifyDataSetChanged();

        ui_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item_company item = (item_company)parent.getAdapter().getItem(position);
                mListener.OnSelectCompanies(item.getId());
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
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSelectCompanies) activity;
        } catch (ClassCastException e) {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSelectCompanies {
        public void OnSelectCompanies(String id_company);
    }


    class adapter extends ArrayAdapter<item_company> {
        private Context                     p_context;
        private List<item_company>          p_values;
        private ArrayList<DataSetObserver>  p_observers;

        public adapter(Context context, int resource) {
            super(context, resource);
            p_context           = context;
            p_observers         = new ArrayList<DataSetObserver>(1);
            p_values            = new ArrayList<item_company>();
        }

        public void setData(List<item_company> myServices){
            p_values.clear();
            for(item_company ms : myServices){
                p_values.add(ms);
            }

            for(DataSetObserver observer : p_observers){
                observer.onChanged();
            }
        }

        @Override
        public item_company getItem(int position) {
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
            item_company oi = new item_company();
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
