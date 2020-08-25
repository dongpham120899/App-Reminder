package com.example.reminder.Setting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.reminder.Fragment.First;
import com.example.reminder.MainActivity;
import com.example.reminder.Music.MusicActivity;
import com.example.reminder.R;

import java.util.ArrayList;

import static android.graphics.Color.BLUE;

public class SettingActivity extends AppCompatActivity {

    private ArrayList<ItemSetting> list;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        list = getListData();
        listView = findViewById(R.id.listSetting);
        listView.setAdapter(new SettingListAdapter(this,list));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("TAG", String.valueOf(i));
                if(i==0) setInterface();
                else if(i==1) setNotification();
                else if(i==2) setHelp();

            }
        });
    }

    private ArrayList<ItemSetting> getListData(){
        ArrayList<ItemSetting> list = new ArrayList<ItemSetting>();
        ItemSetting item1 = new ItemSetting(R.drawable.interface0,"Chocolate Mode","Change interface to chocolate mode");
        ItemSetting item2 = new ItemSetting(R.drawable.notification,"Notification","Change date, time, music");
        ItemSetting item3 = new ItemSetting(R.drawable.help,"Help","Support device");

        list.add(item1);
        list.add(item2);
        list.add(item3);
        return list;
    }
    public void setInterface(){
        Toast.makeText(this, "cak", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("color","dark");
        startActivity(intent);
    }
    public void setNotification(){
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }
    public void setHelp(){
        Toast.makeText(this, "Sorry, 'Help' be not developed.", Toast.LENGTH_SHORT).show();
    }
}