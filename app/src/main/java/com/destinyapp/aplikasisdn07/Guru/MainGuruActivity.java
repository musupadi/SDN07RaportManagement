package com.destinyapp.aplikasisdn07.Guru;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.destinyapp.aplikasisdn07.API.ApiRequest;
import com.destinyapp.aplikasisdn07.API.RetroServer;
import com.destinyapp.aplikasisdn07.About.AboutFragment;
import com.destinyapp.aplikasisdn07.About.AboutSDN07;
import com.destinyapp.aplikasisdn07.Guru.Fragment.AbsenSiswa;
import com.destinyapp.aplikasisdn07.Guru.Fragment.AbsenSiswaGuru;
import com.destinyapp.aplikasisdn07.Guru.Fragment.AbsensiSiswaGuru;
import com.destinyapp.aplikasisdn07.Guru.Fragment.AllClassFragmentGuru;
import com.destinyapp.aplikasisdn07.Guru.Fragment.AllSiswaInKelasGuru;
import com.destinyapp.aplikasisdn07.Guru.Fragment.DashboardGuru;
import com.destinyapp.aplikasisdn07.Guru.Fragment.GuruMengajarFragment;
import com.destinyapp.aplikasisdn07.Guru.Fragment.NilaiFragmentGuru;
import com.destinyapp.aplikasisdn07.Guru.Fragment.PemberianNilaiFragment;
import com.destinyapp.aplikasisdn07.Guru.Fragment.RaportFinalSiswaGuru;
import com.destinyapp.aplikasisdn07.Guru.Fragment.RaportSiswaGuru;
import com.destinyapp.aplikasisdn07.MainActivity;
import com.destinyapp.aplikasisdn07.Models.ResponseModel;
import com.destinyapp.aplikasisdn07.R;
import com.destinyapp.aplikasisdn07.Session.DB_Helper;
import com.destinyapp.aplikasisdn07.Siswa.Fragment.JadwalBelajarSiswaFragment;
import com.destinyapp.aplikasisdn07.Siswa.Fragment.RaportSiswaFragment;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainGuruActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DB_Helper dbHelper;
    String User="";
    TextView navUsername,navName;
    CircleImageView navHeaderPP;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_guru);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        dbHelper = new DB_Helper(this);
        Cursor cursor = dbHelper.checkSession();

        while (cursor.moveToNext()){
            User=cursor.getString(0);
        }


        View headerView = navigationView.getHeaderView(0);
        navUsername = (TextView) headerView.findViewById(R.id.tvUsernameGurusHeader);
        navName = (TextView)headerView.findViewById(R.id.tvNamaGuruHeader);
        navHeaderPP = (CircleImageView) headerView.findViewById(R.id.navHeaderPPGuru);

        getDataGuru();
        navUsername.setText("Username : "+User);
        Fragment fragment = null;
        Intent data = getIntent();
        String namaSiswa = data.getStringExtra("nama_siswa");
        String idKelas = data.getStringExtra("id_kelas");
        String idMapel = data.getStringExtra("id_mapel");
        String NIS = data.getStringExtra("nis");
        String JADWAL = data.getStringExtra("JADWAL");
        String ABSEN = data.getStringExtra("ABSEN");
        String NILAI = data.getStringExtra("NILAI");
        String RAPORT = data.getStringExtra("RAPORT");
        final String absensi = data.getStringExtra("Absensi");
        final String kelas = data.getStringExtra("Kelas");
        final String penilaian = data.getStringExtra("Penilaian");
        final String check = data.getStringExtra("Check");
        if (absensi != null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_ID",idKelas);
            bundle.putString("KEY_ID_MAPEL",idMapel);
            fragment = new AbsensiSiswaGuru();
            fragment.setArguments(bundle);
        }else if(kelas != null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_ID",idKelas);
            fragment = new AllSiswaInKelasGuru();
            fragment.setArguments(bundle);
        }else if(penilaian != null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_NAMA",namaSiswa);
            bundle.putString("KEY_ID_KELAS",idKelas);
            bundle.putString("KEY_NIS",NIS);
            fragment = new PemberianNilaiFragment();
            fragment.setArguments(bundle);
        }else if(check != null){
            Bundle bundle = new Bundle();
            bundle.putString("KEY_NIS",NIS);
            fragment = new RaportFinalSiswaGuru();
            fragment.setArguments(bundle);
        }else if(JADWAL != null){
            fragment=new JadwalBelajarSiswaFragment();
        }else if(ABSEN != null){
            fragment=new AbsenSiswa();
        }else if(NILAI !=null){
            fragment=new NilaiFragmentGuru();
        }else if(RAPORT !=null){
            fragment=new RaportSiswaGuru();
        }else{
            fragment=new DashboardGuru();
        }
        ChangeFragment(fragment);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            long t = System.currentTimeMillis();
            if (t - backPressedTime > 2000) {    // 2 secs
                backPressedTime = t;
                Toast.makeText(this, "Tekan Lagi Untuk Logout",
                        Toast.LENGTH_SHORT).show();

            } else {
                logout();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_guru, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = null;
        int id = item.getItemId();

        if (id == R.id.nav_mengajarGuru) {
            fragment = new GuruMengajarFragment();
        } else if (id == R.id.nav_absenGuru) {
            fragment = new AbsenSiswaGuru();
        } else if (id == R.id.nav_penilaianGuru) {
            fragment = new NilaiFragmentGuru();
        }else if (id == R.id.nav_raportGuru) {
            fragment = new RaportSiswaGuru();
        }else if (id == R.id.nav_data_diriGuru) {

        } else if (id == R.id.nav_keluarGuru) {
            logout();
        }else if (id == R.id.nav_about_sekolah) {
            Intent intent = new Intent(this, AboutSDN07.class);
            startActivity(intent);
        }else if (id == R.id.nav_about) {
            fragment=new AboutFragment();
        }
        ChangeFragment(fragment);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void ChangeFragment(Fragment fragment){
        if(fragment != null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.screen_areaMainGuru,fragment);
            ft.commit();
        }
    }

    private void getDataGuru(){
        String Username=User;
        ApiRequest api = RetroServer.getClient().create(ApiRequest.class);

        final Call<ResponseModel> getGuru = api.dataGuru(Username);
        getGuru.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                String nama = response.body().getNama();
                String profile = response.body().getPictureguru();
                getImageFromURL(profile);
                navName.setText("Nama :"+nama);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(MainGuruActivity.this,"Data Error",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void logout(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda Yakin ingin Logout ?")
                .setCancelable(false)
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(MainGuruActivity.this, MainActivity.class);
                        dbHelper = new DB_Helper(MainGuruActivity.this);
                        dbHelper.userLogout(User,MainGuruActivity.this);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                //Set your icon here
                .setTitle("Perhatian !!!")
                .setIcon(R.drawable.ic_close_black_24dp);
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void getImageFromURL(String url){
        String BASE_URL = getString(R.string.base_url);
        String URL = BASE_URL+"ProfilePicture/Guru/"+url;
        Glide.with(MainGuruActivity.this)
                .load(URL)
                .into(navHeaderPP);
    }
}
