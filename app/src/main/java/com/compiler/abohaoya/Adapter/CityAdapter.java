package com.compiler.abohaoya.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.compiler.abohaoya.R;
import com.compiler.abohaoya.model.City;

import java.util.ArrayList;

/**
 * Created by User on 8/6/2016.
 */
public class CityAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<City> cities;

    public CityAdapter(Context context, ArrayList<City> cities) {
        super();
        this.context = context;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView cityNameListTextView;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.city_list_row, null);

            viewHolder.cityNameListTextView = (TextView) convertView.findViewById(R.id.cityNameListTextView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.cityNameListTextView.setText(cities.get(i).getCityName());

        return convertView;
    }
}
