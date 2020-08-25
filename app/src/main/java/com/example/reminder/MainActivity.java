package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.reminder.Database.MyDatabase;
import com.example.reminder.Database.MyList;
import com.example.reminder.Fragment.First;
import com.example.reminder.Reminder.Receiver;
import com.example.reminder.Setting.SettingActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.graphics.Color.BLUE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAB = "message";
    public static final String NAME_DB = "DATABASE_FOLDER";
    public static final int DATABASE_VERSION = 1;

    public MyDatabase dbForder;
    public ArrayList<MyList> arrayList;
    Calendar calendar;


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout mTablayout;
    TabItem firstItem, secondItem, thirdItem;
    PagerAdapter adapter;

    public String music = "0";
    public String isMusic = "off";
    public int year;
    public int month;
    public int day;
    public int hour;
    public int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database
        arrayList = new ArrayList<>();

        dbForder = new MyDatabase(this, NAME_DB, null, DATABASE_VERSION);
//        dbForder.QueryData("DROP TABLE Mylist");
//        dbForder.QueryData("CREATE TABLE IF NOT EXISTS MyList(" +
//                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "name VARCHAR(255)," +
//                "date DATE DEFAULT '27-7-2020' NOT NULL," +
//                "time VARCHAR(255) DEFAULT '8:00'," +
//                "status VARCHAR(255) DEFAULT 'incomplete')");


        GetDataList();
        Log.i("TAG", "MAIN1 " + arrayList.size());

        calendar = Calendar.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.viewpager);
        mTablayout = findViewById(R.id.tablayout);
        firstItem = findViewById(R.id.firstItem);
        secondItem = findViewById(R.id.secondItem);
        thirdItem = findViewById(R.id.thirditem);

        drawerLayout = findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //Log.i("TAG","MAIN2 "+arrayList.size());

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mTablayout.getTabCount(), arrayList);
        pager.setAdapter(adapter);
        //Log.i("TAG","MAIN3 "+arrayList.size());

        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //Log.i("TAG","MAIN4 "+arrayList.size())

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));

        //change color
        try {
            Intent intent = this.getIntent();
            String s = intent.getStringExtra("color");
            Log.i("Color", s);
            if(s.equals("dark")){
                changeDark();
            }
        } catch (Exception e) {}

        try {
            Intent mintent = this.getIntent();
            String s = mintent.getStringExtra("music");
            isMusic = mintent.getStringExtra("on");
            music = s;
            Log.i("music", s);
        } catch (Exception e) {}

        
    }


    //lay du lieu
    public void GetDataList(){
        //dbForder.QueryData("INSERT INTO MyList VALUES(null,'di choi boi','20-2-2020','8:00','incomplete')");
        Cursor cursor = dbForder.GetData("SELECT * FROM MyList ");
        arrayList.clear();
        //Log.i("TAG","huhu");
        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            String date = cursor.getString(2);
            String time = cursor.getString(3);
            String status = cursor.getString(4);
            //Log.i("TAG","GetDataList " + name);
            arrayList.add(0,new MyList(id, name, date, time, status));
        }
    }


    // Navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.menuSetting){
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return false;
    }

    //Button Add
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_list,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuAdd){
            DialogAdd();
        }

        return super.onOptionsItemSelected(item);
    }

    // dialog add
    private void DialogAdd(){
        final First first = new First();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_list);

        final EditText editName = dialog.findViewById(R.id.eTAdd);
        Button btAdd = dialog.findViewById(R.id.btAdd);
        Button btCancel = dialog.findViewById(R.id.btCancel);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = editName.getText().toString();
                if(s.equals("Enter...")){
                    Toast.makeText(MainActivity.this, "Enter list, please!", Toast.LENGTH_SHORT).show();
                }
                else{

                    //first.addData(s);
                    dbForder.QueryData("INSERT INTO MyList VALUES(null,'"+s+"','27-7-2020','8:00','incomplete')");
                    Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataList();
                    pager.setAdapter(adapter);
                }
            }
        });
        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }


    // dialog edit
    public void DialogEdit(String name, final int id, String date, String time){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit);

        final EditText edEdit = (EditText) dialog.findViewById(R.id.etEdit);
        Button btEdit = (Button) dialog.findViewById(R.id.btEdit);
        Button btCancelEdit = (Button) dialog.findViewById(R.id.btCancelEdit);
        Button Reminder = (Button) dialog.findViewById(R.id.btReminder);
        final Button Time = (Button) dialog.findViewById(R.id.btTime);
        final TextView txtTime = (TextView) dialog.findViewById(R.id.txtTime);
        final TextView txtDate = (TextView) dialog.findViewById(R.id.txtReminder);


        edEdit.setText(name);
        Log.i("message", String.valueOf(date));
        if( String.valueOf(date).equals("null")){
            txtDate.setText("ddd-mmm-yyy");
            txtTime.setText("00:00");
        }
        else{
            txtDate.setText(String.valueOf(date));
            txtTime.setText(time);
        }

        btEdit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                String date = txtDate.getText().toString().trim();
                String time = txtTime.getText().toString().trim();
                String name = edEdit.getText().toString().trim();

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date d1 = new Date();
                Date d2 = calendar.getTime();


                Log.i("da",d1+"    "+d2);

                if(d2.compareTo(d1)<0)
                    Toast.makeText(MainActivity.this, "Date incorrect", Toast.LENGTH_SHORT).show();
                else {
                    dbForder.QueryData("UPDATE MyList SET name = '" + name + "',date ='" + date + "',time = '" + time + "' WHERE id='" + id + "'");
                    Toast.makeText(MainActivity.this, "Update Succefully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataList();
                    pager.setAdapter(adapter);
                    startAlarm(calendar,name);
                }
            }
        });

        btCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        Reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Date", Toast.LENGTH_SHORT).show();

                final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        txtDate.setText(d+"-"+(m+1)+"-"+y);

                        calendar.set(Calendar.YEAR,datePicker.getYear());
                        calendar.set(Calendar.MONTH,datePicker.getMonth());
                        calendar.set(Calendar.DAY_OF_MONTH,datePicker.getDayOfMonth());

                    }
                };
                Log.i("Time","Date: "+year+" "+month+" "+ day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,dateSetListener,year,month,day);
                datePickerDialog.show();
            }
        });


        hour= calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Time", Toast.LENGTH_SHORT).show();

                TimePickerDialog .OnTimeSetListener timeSetDialog = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int h, int m) {
                        txtTime.setText(h+" : "+m);

                        calendar.set(Calendar.HOUR_OF_DAY,timePicker.getCurrentHour());
                        calendar.set(Calendar.MINUTE,timePicker.getCurrentMinute());

                    }
                };
                Log.i("Time","Time: "+hour+" "+ minute);
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, timeSetDialog,hour,minute,true);
                timePickerDialog.show();

            }
        });


        dialog.show();
    }

    //xoa
    public void dialogDelete(final String name, final int id){
        final First first = new First();
        AlertDialog.Builder dialogDelete = new AlertDialog.Builder(this);
        dialogDelete.setMessage("Are you sure to delete '"+name+"' ?");

        dialogDelete.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbForder.QueryData("DELETE FROM MyList WHERE id = '"+id+"'");
                Toast.makeText(MainActivity.this, "Succefully, Delete "+name, Toast.LENGTH_SHORT).show();
                GetDataList();
                pager.setAdapter(adapter);
            }
        });

        dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });


        dialogDelete.show();
    }

    public void isComplete(int id){
        dbForder.QueryData("UPDATE Mylist SET status = 'complete' where id = '"+id+"' ");
        GetDataList();
        pager.setAdapter(adapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void startAlarm(Calendar c,String name){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        Intent mIntent = getIntent();
//        String i = mIntent.getExtras().getString("music");
////        Toast.makeText(this, i, Toast.LENGTH_SHORT).show();
////        Log.i("Music",i);
        Intent intent = new Intent(this, Receiver.class);
        intent.putExtra("time",name);
        intent.putExtra("music",music);
        intent.putExtra("on",isMusic);
        Log.i("music","Main "+music);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,intent,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(),pendingIntent);
    }

    public void cancleAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, Receiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
    }

    public void changeDark(){
        Resources res = getResources();
        int i = res.getColor(R.color.colorDark);
        pager.setBackgroundColor(i);
        navigationView.setBackgroundColor(i);
        mTablayout.setBackgroundColor(i);
        drawerLayout.setBackgroundColor(i);
    }
}