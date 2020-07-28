package com.example.reminder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reminder.Database.MyList;

import java.util.List;

public class ListAdapter extends BaseAdapter {
    private static final String TAB = "message";
    private  MainActivity mContext;
    private First first;
    private int layout;
    private List<MyList> myLists;

    public ListAdapter(MainActivity mContext, int layout, List<MyList> myLists) {
        this.mContext = mContext;
        this.layout = layout;
        this.myLists = myLists;
    }

    @Override
    public int getCount() {
        return myLists.size();
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
        ViewHolder holder;
        Log.i(TAB, "getView ListAdapter");
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.textList = (TextView) view.findViewById(R.id.tvList);
            holder.imageDelete = (ImageView) view.findViewById(R.id.imageDelete);
            holder.imageEdit = (ImageView) view.findViewById(R.id.imageEdit);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final MyList list = myLists.get(i);
        //Toast.makeText(this,"ss",Toast.LENGTH_LONG).show();

        holder.textList.setText((list.getName()));

        //edit list
        holder.imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, "Edit "+list.getListName(), Toast.LENGTH_SHORT).show();
                mContext.DialogEdit(list.getName(),list.getId(),list.getDate());
            }
        });

        //xoa list
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.dialogDelete(list.getName(),list.getId());
            }
        });
        return view;
    }


    // xu ly ViewHolder, listview
    private class ViewHolder{
        TextView textList;
        ImageView imageEdit, imageDelete;
    }
}
