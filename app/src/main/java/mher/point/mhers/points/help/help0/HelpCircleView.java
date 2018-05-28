package mher.point.mhers.points.help.help0;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import mher.point.mhers.points.R;
import mher.point.mhers.points.model.Btn;
import mher.point.mhers.points.util.TypeFaceService;

/**
 * Created by mhers on 20/05/2018.
 */

public class HelpCircleView extends View {
    private Paint painttext;
    private int radius = 1, width, height ;
    private Context context;
    private List<Btn> dataList;
    private List<String> colorList;

    private IUnderstand iUnderstand;

    private final static String CIRCLE1="0/7";
    private final static String CIRCLE2="1/5";
    private final static String CIRCLE3="2/7";


    private Handler handler;
    private Runnable runnable;
    private boolean blink = true ,turnBlink =true;



    public HelpCircleView(Context context,IUnderstand iUnderstand) {
        super(context);
        this.context = context;
        this.iUnderstand =iUnderstand;

        dataList = new ArrayList<>();
        colorList = Arrays.asList(context.getApplicationContext().getResources().getStringArray(R.array.colors));

        initialize();
        circleBlink();


    }

    private void initialize() {


//paint text
        painttext = new Paint();
        painttext.setColor(Color.WHITE);
        painttext.setTypeface(TypeFaceService.getInstance().getBellotaRegular(context));
        painttext.setTextSize(50);


        //metric
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        radius = width / 16;

        for(Btn btn:creatNewList()){
            dataList.add(btn);
        }

    }
    private List<Btn> creatNewList() {
        List<Btn> newData = new ArrayList<>();
        int newY = 0;
        int newX = 0;
        for (int j = 1; j < 10; j++) {
            newY = height - j * (2 * radius);
            for (int i = 1; i <= 8; i++) {
                int point = new Random().nextInt(9) + 1;
                newX = (i - 1) * (2 * radius);
                switch ((8 - j) + "/" + i){
                    case  CIRCLE1:
                        point =5;
                        break;
                    case  CIRCLE2:
                        point =3;
                        break;
                    case  CIRCLE3:
                        point =2;
                        break;
                }
                newData.add(new Btn((8 - j) + "/" + i, newX, newY, point, 1));


            }
        }
        return newData;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < dataList.size(); i++) {
            Btn btn = dataList.get(i);
            Paint paintCircle = getPaint(btn.getId(),btn.getPoint(), btn.getCategori());
            canvas.drawCircle(btn.getX() + radius, btn.getY() + radius, radius - 2, paintCircle);
            canvas.drawText(btn.getPoint() + "", btn.getX() + radius - radius / 4 + 2, btn.getY() + radius + radius / 4 - 2, painttext);

        }
    }

    private Paint getPaint(String id,int point, int category) {
        Paint paintcir = new Paint();
        Color color;
        paintcir.setColor(Color.parseColor(colorList.get((category != 3) ? point : 0)));


        if (category == 1) {
            paintcir.setStyle(Paint.Style.STROKE);
            paintcir.setStrokeWidth(5);
            if(id.equals(CIRCLE1) || id.equals(CIRCLE2) || id.equals(CIRCLE3)){
                if(blink){
                    paintcir.setStyle(Paint.Style.FILL);
                }
            }
        } else {
            paintcir.setStyle(Paint.Style.FILL);
        }
        return paintcir;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean handled = false;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                int touchX = (int) event.getX(0);
                int touchY = (int) event.getY(0);
                findCircle(touchX, touchY);
                handled = true;
                break;

        }
        return handled;
    }
    private void findCircle(int touchX, int touchY) {
        for (int i = 0; i < dataList.size(); i++) {
            Btn circle = dataList.get(i);
            if (circle.getX() < touchX && circle.getX() + 2 * radius > touchX && circle.getY() < touchY && circle.getY() + 2 * radius > touchY) {
                 if(circle.getId().equals(CIRCLE1) || circle.getId().equals(CIRCLE2) || circle.getId().equals(CIRCLE3)){
                     clikingCircle(i);
                 }
                break;
            }
        }
    }

    private void clikingCircle(int index) {
        if (dataList.get(index).getCategori() != 3) {
            dataList.get(index).setCategori(dataList.get(index).getCategori() == 1 ? 2 : 1);

        }

        int pointSum = 0;
        for (Btn button : dataList) {
            if (button.getCategori() == 2) {
                pointSum = pointSum + button.getPoint();
            }
        }


        if (pointSum == 10) {
goNextPaje();
        }
        invalidate();
    }


    private void circleBlink() {
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if(turnBlink){
                    blink = blink?false:true;
                    invalidate();
                    circleBlink();
                }
            }
        };
        handler.postDelayed(runnable, 500);
    }
public void stopBlink(){
        turnBlink=false;
}
private   void goNextPaje(){
    turnBlink=false;
     iUnderstand.understand();
}
}
