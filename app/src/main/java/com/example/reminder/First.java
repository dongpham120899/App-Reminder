package com.example.reminder;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reminder.Database.Database;
import com.example.reminder.Database.MyList;

import java.util.ArrayList;

public class First extends Fragment {
    public static final String TAB = "message";


    //private MainActivity context;

    public ListView lvList;
    private ArrayList<MyList> arrayList;
    MainActivity mContext;
    ListAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAB,"onCreateView in Fisrt");
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        lvList = (ListView) view.findViewById(R.id.lvList);
        arrayList = new ArrayList<>();
        adapter = new ListAdapter((MainActivity) getActivity(), R.layout.item_list_layout, arrayList);
        lvList.setAdapter(adapter);



        mContext.GetDataList();
        return view;
    }
    //lay du lieu







}