package com.example.namgiwon.hangul;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by namgiwon on 2017. 12. 29..
 */

public class PaintText extends AppCompatActivity {
    RelativeLayout parentLayout;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painttext);

        parentLayout = (RelativeLayout) findViewById(R.id.painttext_parentlayout);
        text = getIntent().getStringExtra("text");


        for(int i = 0 ; i < 23; i++){
            ImageView iv =  new ImageView(this);

            if(i > 10){
                iv.setId(i);
                iv.setOnTouchListener(tListener);
                iv.setBackgroundResource(R.drawable.b);
                iv.setLayoutParams(new RelativeLayout.LayoutParams(100, 50));
                ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(
                        iv.getLayoutParams());
                margin.setMargins(10 * 50, (i-11)*50, 0, 0);
                iv.setLayoutParams(margin);
            }

            else {
                iv.setId(i);
                iv.setOnDragListener(dListener);
                iv.setBackgroundResource(R.drawable.a);
                iv.setLayoutParams(new RelativeLayout.LayoutParams(50, 100));
                // margin 설정을 담을 param을 하나 만들어서 ImageView에 적용합니다.
                ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(
                        iv.getLayoutParams());
                margin.setMargins(i * 50, 0, 0, 0);
                iv.setLayoutParams(margin);
            }
            parentLayout.addView(iv);
        }


    }


    private View.OnTouchListener tListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    Toast.makeText(getApplicationContext(),String.valueOf(view.getId()),Toast.LENGTH_LONG).show();
                case MotionEvent.ACTION_MOVE:
                    Log.d("id == ", String.valueOf(view.getId()));
                    Toast.makeText(getApplicationContext(),String.valueOf(view.getId()),Toast.LENGTH_LONG).show();
                    break;
                

            }
            //Toast.makeText(getApplicationContext(),"aa",Toast.LENGTH_LONG).show();

            return true;
        }
    };

    private View.OnDragListener dListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            switch (dragEvent.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    Toast.makeText(getApplicationContext(),"tt",Toast.LENGTH_LONG).show();
                    break;
                case DragEvent.ACTION_DRAG_ENTERED :
                    Log.d("id == ", String.valueOf(view.getId()));
                    Toast.makeText(getApplicationContext(),String.valueOf(view.getId()),Toast.LENGTH_LONG).show();
                    break;
            }
            return true;
        }
    };

}
