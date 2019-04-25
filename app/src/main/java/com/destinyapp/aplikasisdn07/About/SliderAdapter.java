package com.destinyapp.aplikasisdn07.About;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.destinyapp.aplikasisdn07.R;

public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context){
        this.context = context;
    }
    public int[] id ={
            0,
            1,
            2,
            3,
            4
    };

    public int[] slide_image ={
            R.drawable.fotome,
            R.drawable.juna,
            R.drawable.liviana,
            R.drawable.richi,
            R.drawable.zuki
    };

    public String[] slide_nama ={
            "Muhammad Supriyadi",
            "Mochammad Ardjun Adinugraha",
            "Liviana Febriyanti",
            "Richi Apriyanto",
            "Hayyilah Al Marzuki"
    };

    public String[] slide_title = {
            "Full Stack Developer",
            "Front End Programmer",
            "Database Manager",
            "Back End Programmer",
            "Back End Programmer"
    };

    public int[] slide_deskripsi = {
            R.string.about_supriyadi,
            R.string.about_supriyadi,
            R.string.about_supriyadi,
            R.string.about_supriyadi,
            R.string.about_supriyadi
    };

    public int[] suara_saya = {
            R.raw.therisingshieldhero,
            R.raw.therisingshieldhero,
            R.raw.therisingshieldhero,
            R.raw.therisingshieldhero,
            R.raw.therisingshieldhero
    };

    @Override
    public int getCount() {
        return slide_nama.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        layoutInflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.about_slide_layout,container,false);

        ImageView slideImageView = (ImageView)view.findViewById(R.id.imgProfileMe);
        TextView Nama = (TextView)view.findViewById(R.id.Nama);
        TextView Tittle = (TextView)view.findViewById(R.id.Tittle);
        final TextView Deskripsi = (TextView)view.findViewById(R.id.DeskripsiSaya);

        slideImageView.setImageResource(slide_image[position]);
        Nama.setText(slide_nama[position]);
        Tittle.setText(slide_title[position]);
        Deskripsi.setText(slide_deskripsi[position]);
        final MediaPlayer SuaraMe = MediaPlayer.create(context,suara_saya[position]);
        Deskripsi.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SuaraMe.start();
                return true;
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout)object);
    }
}
