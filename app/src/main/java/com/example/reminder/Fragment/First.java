package com.example.reminder.Fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.reminder.Database.MyDatabase;
import com.example.reminder.Database.MyList;
import com.example.reminder.ListAdapter;
import com.example.reminder.MainActivity;
import com.example.reminder.R;

import java.util.ArrayList;
import java.util.Collections;

public class First extends Fragment {
    public static final String TAG = "message";
    private static final String NAME_DB = "MyList";
    private static final int DATABASE_VERSION = 1 ;


    public ListView lvList;
    private ArrayList<MyList> myLists;
    private ListAdapter adapter;

    public First(){}
    public First(ArrayList<MyList> a){
        this.myLists = a;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i("TAG","onCreateView in Fisrt");
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        lvList = (ListView) view.findViewById(R.id.lvList);
        adapter = new ListAdapter((MainActivity) getActivity(), R.layout.item_list_layout, myLists);
        lvList.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return view;
    }
}