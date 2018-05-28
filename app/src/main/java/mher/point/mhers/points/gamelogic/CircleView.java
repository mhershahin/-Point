package mher.point.mhers.points.gamelogic;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import mher.point.mhers.points.R;
import mher.point.mhers.points.dialog.DialogHelper;
import mher.point.mhers.points.dialog.IDialogLisener;
import mher.point.mhers.points.interfac.IGameCalBack;
import mher.point.mhers.points.model.Btn;
import mher.point.mhers.points.util.TypeFaceService;
import mher.point.mhers.points.util.SharedPref;
import mher.point.mhers.points.util.Sounds;

/**
 * Created by mhers on 09/12/2017.
 */

public class CircleView extends View {
    private List<Btn> dataList;
    private List<Btn> pozitivList;
    private List<String> colorList;
    private Context context;
    private int radius = 1, width, score = 0, timeScore = 1, height,j = 8;
    private Paint painttext;
    private boolean running = true;
    private IGameCalBack iGameCalBack;
    private IDialogLisener iDialogLisener;
    private Handler handler;
    private Runnable runnable;


    public CircleView(Context context, boolean isContinu, IGameCalBack iGameCalBack, IDialogLisener iDialogLisener) {
        super(context);
        this.context = context;
        this.iGameCalBack = iGameCalBack;
        this.iDialogLisener = iDialogLisener;

        pozitivList = new ArrayList<>();
        colorList = Arrays.asList(context.getApplicationContext().getResources().getStringArray(R.array.colors));
        dataList = new ArrayList<>();
        initialize();
        getDatalist(isContinu);
        iGameCalBack.setScore(score);
        butGoUp();

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
        j = 8;
    }


    private void getDatalist(boolean isContinu) {

//get continu List
        if (isContinu) {
            score = SharedPref.getInstance().getScore(context);
            for (Btn btn : SharedPref.getInstance().getbtnList(context)) {
                dataList.add(btn);
                String id[] = btn.getId().split("/");
                if (j < Integer.parseInt(id[0])) {
                    j = Integer.parseInt(id[0]);
                }

            }


        }

//creat new List
        if (!isContinu) {
            dataList.clear();
            score = 0;
            for (Btn btn : creatNewList()) {
                dataList.add(btn);
            }
        }

    }

    //new game new list for start
    private List<Btn> creatNewList() {
        List<Btn> newData = new ArrayList<>();

        int newY = 0;
        int newX = 0;
        for (int j = 1; j < 7; j++) {
            newY = height - j * (2 * radius);
            for (int i = 1; i <= 8; i++) {
                int point = new Random().nextInt(9) + 1;
                newX = (i - 1) * (2 * radius);
                newData.add(new Btn((8 - j) + "/" + i, newX, newY, point, 1));


            }
        }
        return newData;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (int i = 0; i < dataList.size(); i++) {
            Btn btn = dataList.get(i);
            Paint paintCircle = getPaint(btn.getPoint(), btn.getCategori());
            canvas.drawCircle(btn.getX() + radius, btn.getY() + radius, radius - 2, paintCircle);
            canvas.drawText(btn.getPoint() + "", btn.getX() + radius - radius / 4 + 2, btn.getY() + radius + radius / 4 - 2, painttext);

        }
    }

    //get paint view unique circles
    private Paint getPaint(int point, int category) {
        Paint paintcir = new Paint();
        Color color;
        paintcir.setColor(Color.parseColor(colorList.get((category != 3) ? point : 0)));
        if (category == 1) {
            paintcir.setStyle(Paint.Style.STROKE);
            paintcir.setStrokeWidth(5);
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

    //find clicking circle
    private void findCircle(int touchX, int touchY) {
        for (int i = 0; i < dataList.size(); i++) {
            Btn circle = dataList.get(i);
            if (circle.getX() < touchX && circle.getX() + 2 * radius > touchX && circle.getY() < touchY && circle.getY() + 2 * radius > touchY) {
                clikingCircle(i);
                break;
            }
        }
    }

    //check true or false
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
            for (Iterator<Btn> iter = dataList.listIterator(); iter.hasNext(); ) {
                Btn b = iter.next();
                if (b.getCategori() == 2) {
                    iter.remove();
                    pozitivList.add(b);
                }
            }
            positivGame();
        }
        if (pointSum > 10) {

            for (int i = 0; i < dataList.size(); i++) {
                Btn b = dataList.get(i);
                if (b.getCategori() == 2) {
                    b.setCategori(3);
                    dataList.set(i, b);
                }
            }
            negativGame();
        }
        invalidate();
    }


    private void positivGame() {

        if (SharedPref.getInstance().getSound(context)) {
            Sounds.getInstance().playPositivSound(context);
        }
        score = score + 10;
        iGameCalBack.setScore(score);
        if (score > SharedPref.getInstance().getBest(context)) {
            SharedPref.getInstance().saveBest(context, score);
        }

        int n = 0;
        for (Btn b : dataList) {
            if (b.getCategori() == 3)
                n++;
        }

        //open some buttons
        if (score % 70 == 0) {
            int btncount1 = new Random().nextInt(3) + 1;
            if (n < 4) {
                btncount1 = 1;
            }
            int j = 0;
            for (int i = 0; i < dataList.size(); i++) {
                Btn btn = dataList.get(i);
                if (btn.getCategori() == 3) {
                    btn.setCategori(1);
                    dataList.set(i, btn);
                    j++;
                    if (j == btncount1)
                        break;
                }
            }
        }
//circals go down
        for (Btn pButton : pozitivList) {
            String id[] = pButton.getId().split("/");
            int iP = Integer.parseInt(id[1]);
            int jP = Integer.parseInt(id[0]);
            for (int i = 0; i < dataList.size(); i++) {
                Btn b = dataList.get(i);
                String idB[] = b.getId().split("/");
                int iB = Integer.parseInt(idB[1]);
                int jB = Integer.parseInt(idB[0]);

                if (iB == iP && jB < jP) {
                    int y = b.getY();
                    b.setY(y + 2 * radius);
                    dataList.set(i, b);

                }
            }
        }

        pozitivList.clear();


    }

    private void negativGame() {
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (SharedPref.getInstance().getSound(context))
            vibe.vibrate(100);
    }

    private void gamemetods() {
        int min = Integer.MAX_VALUE;
        for (Btn b : dataList) {
            if (b.getY() < min)
                min = b.getY();
        }
        if (min < 0) {
            running = false;
//gemeOver
            DialogHelper.getInstance().showDialog(context, iDialogLisener,score);
            iGameCalBack.gameOver();
        }

        if (score >= 0 && score < 150)
            timeScore = 600;
        if (score > 150 && score < 305)
            timeScore = 530;
        if (score > 305 && score < 500)
            timeScore = 300;
        if (score > 500 && score < 900)
            timeScore = 400;
        if (score > 900 && score < 1200)
            timeScore = 300;
        if (score > 1200 && score < 2000)
            timeScore = 260;
        if (score > 2000 && score < 2500)
            timeScore = 200;
        if (score < 3000 && score > 2500)
            timeScore = 150;
        if (score > 3000)
            timeScore = 100;
        if (score > 4000)
            timeScore = 50;
        int newX = 0;
        int max = 0;
        for (Btn b : dataList) {
            if (b.getY() > max)
                max = b.getY();
            Log.e("MAX", max + "");
        }

        //creat new line
        if (dataList.size() == 0)
            max = height - 2 * radius - 1;

        if (max + (2 * radius) < height)
            for (int i = 1; i <= 8; i++) {
                int point = new Random().nextInt(9) + 1;
                newX = (i - 1) * (2 * radius);
                Btn btn = new Btn(j + "/" + i, newX, max + 2 * radius, point, 1);
                dataList.add(btn);
            }

        j++;
        //heighten Y
        for (int i = 0; i < dataList.size(); i++) {
            Btn btn = dataList.get(i);
            int y = btn.getY();
            y = y - 8;
            btn.setY(y);
            dataList.set(i, btn);
        }
        invalidate();
        butGoUp();
    }

    private void butGoUp() {

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                if (running) {
                    gamemetods();
                }
            }
        };
        handler.postDelayed(runnable, timeScore);
    }

    //on back pres save current List
    public void saveData() {
        handler.removeCallbacks(runnable);
        SharedPref.getInstance().saveScore(context, score);
        if (running) {
            SharedPref.getInstance().saveBtnList(context, dataList);
        } else {
            SharedPref.getInstance().saveBtnList(context, null);
        }
        running=false;
    }


}
