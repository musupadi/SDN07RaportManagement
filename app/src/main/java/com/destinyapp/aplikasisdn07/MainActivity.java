package com.destinyapp.aplikasisdn07;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.destinyapp.aplikasisdn07.Fragment.LoginGuruFragment;
import com.destinyapp.aplikasisdn07.Fragment.LoginSiswaFragment;
import com.destinyapp.aplikasisdn07.Guru.MainGuruActivity;
import com.destinyapp.aplikasisdn07.Session.DB_Helper;
import com.destinyapp.aplikasisdn07.Siswa.MainSiswaActivity;

public class MainActivity extends AppCompatActivity {

    private DB_Helper dbHelper;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new LoginGuruFragment();
                    break;
                case R.id.navigation_dashboard:
                    selectedFragment = new LoginSiswaFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Login_Count,
                    selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.Fragment_Login_Count,
                new LoginGuruFragment()).commit();

        dbHelper = new DB_Helper(this);
        Cursor cursor = dbHelper.checkSession();
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                String user = cursor.getString(0);
                String person = cursor.getString(1);
                if(person.equals("Guru")){
                    Intent intent = new Intent(MainActivity.this, MainGuruActivity.class);
                    startActivity(intent);
                }else if(person.equals("Siswa")){
                    Intent intent = new Intent(MainActivity.this, MainSiswaActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(this,"Terjadi Kesalahan pada Session"+user,Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
