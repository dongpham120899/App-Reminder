package com.example.reminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Toast;

import com.example.reminder.Database.Database;
import com.example.reminder.Database.MyList;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAB = "message";
    public static final String NAME_DB = "DATABASE_FOLDER";
    public static final int DATABASE_VERSION = 1;

    public Database dbForder;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    ViewPager pager;
    TabLayout mTablayout;
    private ArrayList<MyList> arrayList;
    TabItem firstItem, secondItem, thirdItem;

    PagerAdapter adapter;

    public int year;
    public int month;
    public int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pager = findViewById(R.id.viewpager);
        mTablayout = findViewById(R.id.tablayout);
        firstItem = findViewById(R.id.firstItem);
        secondItem = findViewById(R.id.secondItem);
        thirdItem = findViewById(R.id.thirditem);

        drawerLayout =findViewById(R.id.drawer);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,mTablayout.getTabCount());
        pager.setAdapter(adapter);

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

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));

        //Database
        arrayList = new ArrayList<>();
        dbForder = new Database(this, NAME_DB, null, DATABASE_VERSION);
        //Cursor cursor = dbForder.GetData("SELECT * FROM MyList");

        //tao database
        dbForder.QueryData("CREATE TABLE IF NOT EXISTS MyList(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(255)," +
                "date DATE DEFAULT '27-7-2020' NOT NULL," +
                "time VARCHAR(255)," +
                "status VARCHAR(255) )");
    }

    //lay du lieu
    public void GetDataList(){
        //dbForder.QueryData("INSERT INTO MyList VALUES(null,'di choi boi','20-2-2020',null,null)");
        Cursor cursor = dbForder.GetData("SELECT * FROM MyList ");
        arrayList.clear();
        while(cursor.moveToNext()){
            String name = cursor.getString(1);
            int id = cursor.getInt(0);
            String date = cursor.getString(2);
            String time = cursor.getString(3);
            String status = cursor.getString(4);

            arrayList.add(new MyList(id, name, date, time, status));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId() == R.id.menuItem){
            Toast.makeText(this, "Btn Click", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

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
                    //first.dbForder.QueryData("INSERT INTO MyList VALUES(null,'"+s+"','27-7-2020',null,null)");
                    Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    //first.GetDataList();
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
    public void DialogEdit(String name, final int id, String date){
        final First first = new First();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit);

        final EditText edEdit = (EditText) dialog.findViewById(R.id.etEdit);
        Button btEdit = (Button) dialog.findViewById(R.id.btEdit);
        Button btCancelEdit = (Button) dialog.findViewById(R.id.btCancelEdit);
        Button Reminder = (Button) dialog.findViewById(R.id.btReminder);
        final TextView editRe =  (TextView) dialog.findViewById(R.id.txtReminder);


        edEdit.setText(name);
        Log.i("message", String.valueOf(date));
        if( String.valueOf(date).equals("null")){
            editRe.setText("ddd-mmm-yyy");
        }
        else{
            editRe.setText(String.valueOf(date));
        }

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s2 = editRe.getText().toString().trim();
                Log.i("message",s2);
                String s = edEdit.getText().toString().trim();
                dbForder.QueryData("UPDATE MyList SET name = '"+s+"',date ='"+s2+"' WHERE id='"+id+"'");
                Toast.makeText(MainActivity.this, "Da cap nhat", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                GetDataList();
            }
        });

        btCancelEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        Reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Reminder", Toast.LENGTH_SHORT).show();

                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        editRe.setText(d+"-"+(m+1)+"-"+y);

                        year = y;
                        month = m;
                        day = d;
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,dateSetListener,year,month,day);
                datePickerDialog.show();
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
            }
        });

        dialogDelete.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });


        dialogDelete.show();
    }

}