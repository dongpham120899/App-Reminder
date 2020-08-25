package com.example.reminder.Music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reminder.MainActivity;
import com.example.reminder.R;
import com.example.reminder.Reminder.Receiver;
import com.example.reminder.Setting.SettingActivity;

import java.util.ArrayList;

public class MusicActivity extends AppCompatActivity {
    private ArrayList<Music> list;
    private ListView listView;
    private MusicListAdapter adapter;
    private TextView musicChoosen;

    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        list = new ArrayList<>();
        listView = findViewById(R.id.listMusic);
        checkBox = findViewById(R.id.chipIsMusic);
        musicChoosen = findViewById(R.id.titleMusic);
        adapter = new MusicListAdapter(this,list);
        listView.setAdapter(adapter);
        //checkbox check
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(checkBox.isChecked()) {
                    Toast.makeText(MusicActivity.this, "true", Toast.LENGTH_SHORT).show();
                    getDatalist();

                    //listview item click
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Music music = (Music) list.get(i);
                            Log.i("Music",music.getTitle());
                            musicChoosen.setText(music.getTitle());
                            sendData(i);
                        }
                    });
                }
                else{
                    Toast.makeText(MusicActivity.this, "false", Toast.LENGTH_SHORT).show();
                    list.clear();
                    adapter.notifyDataSetChanged();
                }
            }
        });





    }
    private void getDatalist(){
        Music music1 = new Music("Love yourself","music");
        Music music2 = new Music("Love hihi","music");
        Music music3 = new Music("yourself","music");

        list.add(music1);
        list.add(music2);
        list.add(music3);
        adapter.notifyDataSetChanged();
    }

    public void sendData(int i){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("music",String.valueOf(i));
        intent.putExtra("on","on");
        startActivity(intent);
    }
}