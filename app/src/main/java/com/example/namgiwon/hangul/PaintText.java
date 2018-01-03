package com.example.namgiwon.hangul;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Path;
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

public class PaintText extends AppCompatActivity implements View.OnTouchListener,View.OnDragListener {
    RelativeLayout parentLayout;
    String text;
    Path path  = new Path();
    private static final String IMAGEVIEW_TAG = "드래그 이미지";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painttext);

        parentLayout = (RelativeLayout) findViewById(R.id.painttext_parentlayout);
        text = getIntent().getStringExtra("text");


        for(int i = 0 ; i < 23; i++){
            ImageView iv =  new ImageView(this);
            iv.setClickable(true);
            iv.setTag(IMAGEVIEW_TAG);

            if(i > 10){
                iv.setId(i);
                iv.setOnTouchListener(this);
                iv.setOnDragListener(this);
                iv.setBackgroundResource(R.drawable.b);
                iv.setLayoutParams(new RelativeLayout.LayoutParams(100, 50));
                ViewGroup.MarginLayoutParams margin = new ViewGroup.MarginLayoutParams(
                        iv.getLayoutParams());
                margin.setMargins(10 * 50, (i-11)*50, 0, 0);
                iv.setLayoutParams(margin);
            }

            else {
                iv.setId(i);
                iv.setOnTouchListener(this);
                iv.setOnDragListener(this);
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

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();

            switch (motionEvent.getAction()){
                case MotionEvent.ACTION_DOWN:

                    Log.d("id down == ", String.valueOf(view.getId()));
                    Toast.makeText(getApplicationContext(),view.getId()+"",Toast.LENGTH_SHORT).show();
                    ClipData.Item item = new ClipData.Item(
                            (CharSequence) view.getTag());

                    String[] mimeTypes = { ClipDescription.MIMETYPE_TEXT_PLAIN };
                    ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);

                    view.startDrag(data, // data to be dragged
                            shadowBuilder, // drag shadow
                            view, // 드래그 드랍할  Vew
                            0 // 필요없은 플래그
                    );
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.moveTo(x, y);
                    Log.d("id move == ", String.valueOf(view.getId()));
                    Toast.makeText(getApplicationContext(),view.getId()+"",Toast.LENGTH_SHORT).show();
                    break;
            }
            return false;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        switch (dragEvent.getAction()) {

            // 이미지를 드래그 시작될때
            case DragEvent.ACTION_DRAG_STARTED:
                Log.d("DragClickListener", "ACTION_DRAG_STARTED");
                break;

            // 드래그한 이미지를 옮길려는 지역으로 들어왔을때
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.d("id move == ", String.valueOf(view.getId()));
                // 이미지가 들어왔다는 것을 알려주기 위해 배경이미지 변경
                Toast.makeText(getApplicationContext(),view.getId()+"",Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }


    /*private View.OnTouchListener tListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x, y);
                    Log.d("id down == ", String.valueOf(view.getId()));
                    Toast.makeText(getApplicationContext(),view.getId()+"",Toast.LENGTH_SHORT).show();
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.moveTo(x, y);
                    Log.d("id move == ", String.valueOf(view.getId()));
                    Toast.makeText(getApplicationContext(),view.getId()+"",Toast.LENGTH_SHORT).show();
                    break;
                

            }
            return false;
            //Toast.makeText(getApplicationContext(),"aa",Toast.LENGTH_LONG).show();

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
    };*/

}
