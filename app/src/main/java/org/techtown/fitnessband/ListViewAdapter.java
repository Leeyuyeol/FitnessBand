package org.techtown.fitnessband;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by e1-572g on 2018-05-18.
 */

public class ListViewAdapter extends BaseAdapter {

    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();


    // Adapter에 사용되는 데이터의 개수 리턴
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    // 지정한 위치(position)에 있는 데이터 리턴
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView number = (TextView) convertView.findViewById(R.id.number);
        TextView counter = (TextView) convertView.findViewById(R.id.counter);

        ListViewItem listViewItem = listViewItemList.get(position);

        number.setText(listViewItem.getNumber());
        counter.setText(listViewItem.getCounter());

        return convertView;
    }

    public void addItem(String number, String counter) {
        ListViewItem item = new ListViewItem();
        item.setCounter(counter);
        item.setNumber(number);

        listViewItemList.add(item);
    }
}
