package com.example.reminder;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.reminder.Database.MyList;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private static final String TAG = "message";
    private  MainActivity mContext;
    private int layout;
    private ArrayList<MyList> myLists =  new ArrayList<>();

    public ListAdapter(MainActivity mContext, int layout, ArrayList<MyList> myLists) {
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
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.textList = (TextView) view.findViewById(R.id.tvList);
            holder.imageDelete = (ImageView) view.findViewById(R.id.imageDelete);
            holder.imageEdit = (ImageView) view.findViewById(R.id.imageEdit);
            holder.imageIsComplete = (ImageView) view.findViewById(R.id.imageIsComplete);
            holder.textDateTime = (TextView) view.findViewById(R.id.txtDateTime);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final MyList list = myLists.get(i);
        //Toast.makeText(this,"ss",Toast.LENGTH_LONG).show();
        Log.i("TAG","getView id "+list.getId()+ " status :"+list.getStatus()+" i = "+i);
        holder.textList.setText((list.getName()));

        if(list.getDate().equals("27-7-2020")&&list.getTime().equals("8:00")){}
        else holder.textDateTime.setText(list.getTime()+"   "+ list.getDate());

        if(list.getStatus().equals("incomplete")) holder.imageIsComplete.setImageResource(R.drawable.iscomplete);
        else if(list.getStatus().equals("complete")){
            holder.imageIsComplete.setImageResource(R.drawable.complete);
        }


        //edit list
        if(list.getStatus().equals("incomplete")) {
            holder.imageEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(mContext, "Edit "+list.getListName(), Toast.LENGTH_SHORT).show();
                    mContext.DialogEdit(list.getName(), list.getId(), list.getDate(), list.getTime());
                }
            });
        }
        else{
            //mContext.cancleAlarm();
        }

        //xoa list
        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.dialogDelete(list.getName(),list.getId());
            }
        });
        //bam hoan thanh
        holder.imageIsComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.imageIsComplete.setImageResource(R.drawable.delete);
                mContext.isComplete(list.getId());
            }
        });
        return view;
   }


    // xu ly ViewHolder, listview
    private class ViewHolder{
        TextView textList;
        TextView textDateTime;
        ImageView imageEdit, imageDelete,imageIsComplete;
    }

}
