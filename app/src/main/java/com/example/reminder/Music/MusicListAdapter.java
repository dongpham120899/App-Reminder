package com.example.reminder.Music;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reminder.R;
import com.example.reminder.Setting.ItemSetting;
import com.example.reminder.Setting.SettingActivity;

import java.util.ArrayList;

public class MusicListAdapter extends BaseAdapter {
    private ArrayList<Music> list;
    private LayoutInflater inflater;
    private MusicActivity context;

    public MusicListAdapter(MusicActivity context1, ArrayList<Music> arr){
        this.context = context1;
        this.list = arr;
        inflater = LayoutInflater.from(context1);
    }
    @Override
    public int getCount() {
        //Log.i("Music","size: "+ getCount());
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
            view = inflater.inflate(R.layout.item_list_music,null);
            holder = new Holder();
            holder.image = view.findViewById(R.id.image02);
            holder.title = view.findViewById(R.id.txtTitleMusic);
            holder.detail = view.findViewById(R.id.txtMusicDetails);
            view.setTag(holder);
        }
        else{
            holder = (Holder) view.getTag();
        }

        Music music = this.list.get(i);
        holder.title.setText(music.getTitle());
        holder.detail.setText(music.getDetail());
        holder.image.setImageResource(R.drawable.next);

        return view;
    }

    public static class Holder{
        TextView title, detail;
        ImageView image;
    }
}
