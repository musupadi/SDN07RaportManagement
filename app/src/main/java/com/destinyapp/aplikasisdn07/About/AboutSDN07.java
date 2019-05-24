package com.destinyapp.aplikasisdn07.About;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.destinyapp.aplikasisdn07.Admin.MainAdminActivity;
import com.destinyapp.aplikasisdn07.MainActivity;
import com.destinyapp.aplikasisdn07.R;

public class AboutSDN07 extends AppCompatActivity {
    Button kembali;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_sdn07);
        kembali=(Button)findViewById(R.id.btnKembali);

        kembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Back();
            }
        });
    }
    private void Back(){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}
