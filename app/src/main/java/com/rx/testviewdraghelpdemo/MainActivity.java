package com.rx.testviewdraghelpdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


public class MainActivity extends AppCompatActivity
{
    ViewGragHelpGroup viewGragHelpGroup;

    private String[] images = {"http://g.hiphotos.baidu.com/image/pic/item/5243fbf2b211931376d158d568380cd790238dc1.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/aec379310a55b3199f70cd0e4ea98226cffc173b.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/4b90f603738da977f53a9d57bd51f8198618e3b1.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/5243fbf2b211931369cbb5cb68380cd790238dc5.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/d043ad4bd11373f0cd65c8faa90f4bfbfbed0478.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/7acb0a46f21fbe0916db4d7066600c338644adc9.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewGragHelpGroup = findViewById(R.id.icon_group);
        Display display=getWindowManager().getDefaultDisplay();
        int width=display.getWidth();
        int heig=display.getHeight();
        for (int i = 0; i < 5; i++)
        {
            View view = this.getLayoutInflater().inflate(R.layout.card_item, null);
            ImageView imageView=view.findViewById(R.id.maskView);
            Glide.with(MainActivity.this).load(images[i]).into(imageView);
            ViewGroup.LayoutParams params=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.width=width;
            params.height=heig/2;
            view.setLayoutParams(params);
            viewGragHelpGroup.addView(view);
        }
    }
}
