package com.example.namgiwon.hangul;
//kiwon
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.ssomai.android.scalablelayout.ScalableLayout;

import java.util.LinkedList;

import static android.view.DragEvent.ACTION_DRAG_STARTED;
import static android.view.Gravity.CENTER;
import static android.view.Gravity.isVertical;


/**
 * Created by namgiwon on 2017. 12. 29..
 */

public class PaintText extends AppCompatActivity implements View.OnTouchListener,View.OnDragListener {
    RelativeLayout parentLayout;
    String text;
    View now;
    private DrawLine drawLine = null;
    private static final String IMAGEVIEW_TAG = "드래그 이미지";
    int x1;
    int x2;
    int y1;
    int y2;
    int id = 1;
    int blackBlockSize = 100;
    int clearBlockSize = 300;
    int a = 0;
    Gson gson = new Gson();
    JsonArray jsonarr = new JsonArray();
    JsonArray jsonarr1 = new JsonArray();
    JsonArray jsonarr2 = new JsonArray();
    JsonObject jsonobj = new JsonObject();
    JsonObject jsonobj1 = new JsonObject();
    JsonObject jsonobj2 = new JsonObject();
    Button reset;
    Button back;
    LinkedList<Path> stack;
    LinearLayout ll;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painttext);

        stack = new LinkedList<Path>();
        reset = (Button) findViewById(R.id.painttext_reset);
        reset.setOnClickListener(bListener);
        back = (Button) findViewById(R.id.painttext_back);
        back.setOnClickListener(bListener);
        parentLayout = (RelativeLayout) findViewById(R.id.painttext_parentlayout);
        text = getIntent().getStringExtra("text");
        parentLayout.setOnTouchListener(this);
        parentLayout.setOnDragListener(this);



        jsonobj.addProperty("x1","0");
        jsonobj.addProperty("y1","0");
        jsonobj.addProperty("x2","200");
        jsonobj.addProperty("y2","0");
        jsonarr.add(jsonobj);
        jsonobj = new JsonObject();
        jsonobj.addProperty("x1","200");
        jsonobj.addProperty("y1","100");
        jsonobj.addProperty("x2","200");
        jsonobj.addProperty("y2","600");
        jsonarr.add(jsonobj);
        jsonobj = new JsonObject();

        jsonobj1.addProperty("x1","0");
        jsonobj1.addProperty("y1","0");
        jsonobj1.addProperty("x2","0");
        jsonobj1.addProperty("y2","400");
        jsonarr1.add(jsonobj1);
        jsonobj1 = new JsonObject();

        jsonobj1.addProperty("x1","0");
        jsonobj1.addProperty("y1","200");
        jsonobj1.addProperty("x2","100");
        jsonobj1.addProperty("y2","200");
        jsonarr1.add(jsonobj1);
        jsonobj1 = new JsonObject();


        jsonobj2.addProperty("x1","100");
        jsonobj2.addProperty("y1","50");
        jsonobj2.addProperty("x2","100");
        jsonobj2.addProperty("y2","150");
        jsonarr2.add(jsonobj2);
        jsonobj2 = new JsonObject();

        jsonobj2.addProperty("x1","200");
        jsonobj2.addProperty("y1","150");
        jsonobj2.addProperty("x2","600");
        jsonobj2.addProperty("y2","150");
        jsonarr2.add(jsonobj2);
        jsonobj2 = new JsonObject();

        parentLayout.setGravity(CENTER);
        ll = new LinearLayout(this);
        ll.setId(0);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 500);
        ll.setWeightSum(3);
        ll.setLayoutParams(lp);
        ll.setBackgroundColor(Color.BLUE);
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout one = new LinearLayout(this);
        one.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,400 );
        lp1.weight=2;
        one.setLayoutParams(lp1);
        one.setBackgroundColor(Color.BLUE);

        ScalableLayout sl = new ScalableLayout(this,400,500);
        ScalableLayout sl1 = new ScalableLayout(this,300,500);
        ScalableLayout sl2 = new ScalableLayout(this,600,250);

        View cho = PaintWord(jsonarr,sl);
        View jung = PaintWord(jsonarr1,sl1);
        View jong = PaintWord(jsonarr2,sl2);
        one.addView(cho);
        one.addView(jung);
        ll.addView(one);
        ll.addView(jong);

        parentLayout.addView(ll);


    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        //hasFocus : 앱이 화면에 보여졌을때 true로 설정되어 호출됨.
        //만약 그리기 뷰 전역변수에 값이 없을경우 전역변수를 초기화 시킴.
        if(hasFocus && drawLine == null)
        {
            //그리기 뷰가 보여질(나타날) 레이아웃 찾기..
            if(parentLayout != null) //그리기 뷰가 보여질 레이아웃이 있으면...
            {
                //그리기 뷰 레이아웃의 넓이와 높이를 찾아서 Rect 변수 생성.
                Rect rect = new Rect(0, 0,
                        parentLayout.getMeasuredWidth(), parentLayout.getMeasuredHeight());

                //그리기 뷰 초기화..
                drawLine = new DrawLine(  this, rect);

                //그리기 뷰를 그리기 뷰 레이아웃에 넣기 -- 이렇게 하면 그리기 뷰가 화면에 보여지게 됨.
                parentLayout.addView(drawLine);
            }
            if(drawLine != null) drawLine.setLineColor(Color.BLACK);
        }
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        Rect l = new Rect();
        parentLayout.getGlobalVisibleRect(l);

        if ((int) view.getId() < 100) {         //10 이란 숫자는 그려진 최대 id값
            x = x + location[0];                // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 왼쪽마진값을 더한다
            y = y +location[1] -l.top;          // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 위쪽마진 값을 더한다
        }
            switch (motionEvent.getAction()& MotionEvent.ACTION_MASK){
                case MotionEvent.ACTION_DOWN: {
                    drawLine.oldX = x;
                    drawLine.oldY = y;
                    drawLine.path.reset();
                    drawLine.path.moveTo(x, y);

                    ClipData.Item item = new ClipData.Item(
                            (CharSequence) view.getTag());
                    String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                    ClipData data = new ClipData("a", mimeTypes, item);
                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                            null);
                    view.startDrag(data, // data to be dragged
                            shadowBuilder, // drag shadow
                            view, // 드래그 드랍할  Vew
                            0 // 필요없은 플래그
                    );
                    return false;
                }
                case MotionEvent.ACTION_MOVE: {

                    return true;
                }
                case MotionEvent.ACTION_UP :{
                    return true;
                }
            }
            return true;
    }

    @Override
    public boolean onDrag(View view, DragEvent dragEvent) {
        float x = dragEvent.getX();
        float y = dragEvent.getY();
        int[] location = new int[2];
        view.getLocationOnScreen(location);

        Rect l = new Rect();
        parentLayout.getGlobalVisibleRect(l);

        if ((int) view.getId() < 100) {         //10 이란 숫자는 그려진 최대 id값
            x = x + location[0];                // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 왼쪽마진값을 더한다
            y += location[1] - l.top;           // 해당 아이디의 절대 좌표를 계산 하기 위하여 좌표에 뷰의 위쪽마진 값을 더한다
        }

        switch (dragEvent.getAction()) {
            // 이미지를 드래그 시작될때

                case DragEvent.ACTION_DRAG_STARTED:
                    return true;
                // 드래그한 이미지를 옮길려는 지역으로 들어왔을때

                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("id move ===", String.valueOf(view.getId()));
                    return true;

            case DragEvent.ACTION_DRAG_LOCATION:
                    //포인트가 이동될때 마다 두 좌표(이전에눌렀던 좌표와 현재 이동한 좌료)간의 간격을 구한다.
                    float dx = Math.abs(x - drawLine.oldX);
                    float dy = Math.abs(y - drawLine.oldY);
                    //두 좌표간의 간격이 4px이상이면 (가로든, 세로든) 그리기 bitmap에 선을 그린다.
                    if (dx >= 4 || dy >= 4) {
                        //path에 좌표의 이동 상황을 넣는다. 이전 좌표에서 신규 좌표로..
                        //lineTo를 쓸수 있지만.. 좀더 부드럽게 보이기 위해서 quadTo를 사용함.
                        drawLine.path.quadTo(drawLine.oldX, drawLine.oldY, x, y);
                        //포인터의 마지막 위치값을 기억한다.
                        drawLine.oldX = x;
                        drawLine.oldY = y;
                        //그리기 bitmap에 path를 따라서 선을 그린다.
                        drawLine.canvas.drawPath(drawLine.path, drawLine.paint);
                    }
                    //화면을 갱신시킴... 이 함수가 호출 되면 onDraw 함수가 실행됨.
                    drawLine.invalidate();
                return true;
            case DragEvent.ACTION_DROP:
                stack.push(drawLine.path);
                drawLine.path = new Path();
                return true;
        }

        return true;
    }

    Button.OnClickListener bListener = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.painttext_reset:
                    drawLine.canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    stack = new LinkedList<Path>(); //스택 초기화
                    drawLine.invalidate();
                    break;
                case R.id.painttext_back:    // 뒤로가기 버튼 즉, 가장 최신의 획을 지우는 버튼
                    drawLine.canvas.drawColor(0, PorterDuff.Mode.CLEAR);
                    if(stack.size() > 0 ) stack.pop();
                    if(stack.size() > 0){
                        for(int i = 0 ; i < stack.size(); i++){
                            drawLine.canvas.drawPath(stack.get(i),drawLine.paint);
                        }
                    }
                    drawLine.invalidate();
                    break;
            }
        }
    };

    public String PaintDirect(PointVO point){
        if(point.getX1() < point.getX2() && point.getY1() == point.getY2())
            return "horizontal";
        else if(point.getX1() == point.getX2() && point.getY1() < point.getY2())
            return "vertical";
        else return "round";
    }

        public ScalableLayout PaintWord(JsonArray jsonarr , ScalableLayout sl){
        for (int i = 0; i < jsonarr.size(); i++) {
            JsonObject jsonobj = jsonarr.get(i).getAsJsonObject();
            PointVO point = gson.fromJson(jsonobj,PointVO.class);
            String direct = PaintDirect(point);  // 그려야할 방향이 가로인지 세로인지 리턴값 = vertical or horizontal or round
            for(int j = 0;;j++){
                Log.d("gg","generate view");
                ImageView iv1 = new ImageView(this);
                ImageView iv = new ImageView(this);
                int ivTOP=0;
                int ivLEFT=0;
                iv1.setId(id);
                iv1.setClickable(true);
                id++;
                iv1.setOnTouchListener(this);
                iv1.setOnDragListener(this);
                iv1.setBackgroundResource(R.drawable.a1); // 이미지뷰 이미지지정 : 투명블럭(글자의 정답체크를 위한 투명 이미지)
                iv.setBackgroundResource(R.drawable.b); //이미지뷰 이미지지정 :  글자블럭
                
                //글자블럭 param 설정
                if(direct.equals("horizontal") ) {
                     ivLEFT =point.getX1()+j*blackBlockSize;
                     ivTOP = point.getY1();
                }else if(direct.equals("vertical")){
                    ivLEFT =point.getX1();
                    ivTOP = point.getY1()+j*blackBlockSize;
                }
                sl.addView(iv,ivLEFT,ivTOP,blackBlockSize,blackBlockSize);
                sl.addView(iv1,ivLEFT-(clearBlockSize-blackBlockSize)/2,ivTOP-(clearBlockSize-blackBlockSize)/2,clearBlockSize,clearBlockSize);
                if(direct.equals("horizontal") ) {
                    if(j == (point.getX2()-point.getX1())/blackBlockSize) break;
                }else if(direct.equals("vertical")){
                    if(j == ((point.getY2()-point.getY1())/blackBlockSize)) break;
                }
            }
        }
        return  sl;
    }



}
