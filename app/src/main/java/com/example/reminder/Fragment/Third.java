package com.example.reminder.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.reminder.Database.MyList;
import com.example.reminder.ListAdapter;
import com.example.reminder.MainActivity;
import com.example.reminder.R;

import java.util.ArrayList;

public class Third extends Fragment {
    public static final String TAG = "message";
    private static final String NAME_DB = "MyList";
    private static final int DATABASE_VERSION = 1 ;


    public ListView lvList;
    private ArrayList<MyList> myLists;
    private ListAdapter adapter;

    public Third(){}
    public Third(ArrayList<MyList> arr){
        this.myLists = arr;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("TAG","onCreateView in Third");
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        lvList = (ListView) view.findViewById(R.id.lvList);
        adapter = new ListAdapter((MainActivity) getActivity(), R.layout.item_list_layout, Incomplete());
        lvList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return view;
    }

    private ArrayList<MyList> Incomplete(){
        ArrayList<MyList> arr = new ArrayList<>();
        for(int i=0;i<myLists.size();i++){
            if(myLists.get(i).getStatus().equals("complete")){
                arr.add(myLists.get(i));
            }
        }
        return  arr;
    }
}