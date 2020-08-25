package com.example.reminder.Setting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.reminder.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class SettingListAdapter extends BaseAdapter {
    private ArrayList<ItemSetting> list;
    private LayoutInflater inflater;
    private SettingActivity context;

    public SettingListAdapter(SettingActivity context1,ArrayList<ItemSetting> arr){
        this.context = context1;
        this.list = arr;
        inflater = LayoutInflater.from(context1);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder;
        if(view == null){
            view = inflater.inflate(R.layout.item_list_setting,null);
            holder = new Holder();
            holder.iconView = view.findViewById(R.id.imageItemSetting);
            holder.itemSetting = view.findViewById(R.id.txtSetting);
            holder.Details =  view.findViewById(R.id.txtSettingDetails);
            holder.image = view.findViewById(R.id.image01);
            view.setTag(holder);
        }
        else {
            holder =(Holder) view.getTag();
        }

        ItemSetting item = this.list.get(i);
        holder.iconView.setImageResource(item.getIcon());
        holder.itemSetting.setText(item.getName());
        holder.Details.setText(item.getDetail());
        holder.image.setImageResource(R.drawable.next);

        return view;
    }

    public static class Holder{
        ImageView iconView,image;
        TextView itemSetting;
        TextView Details;
    }
}
