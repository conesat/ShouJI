package com.hg.shouji.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.hg.shouji.RegisterActionActivity;

import java.util.ArrayList;
import java.util.List;

public class ActionView extends View {

    private int finishTemp = 0;
    private boolean startCount = false;
    private boolean finish = false;

    private Handler handler;

    private Path path;//曲线
    private Paint paint;//画笔
    private Paint paint2;//画笔

    List<List<Point>> lPoints=new ArrayList<>();//全部点线
    List<Point> points;//点集合
    List<Point> iPoints=new ArrayList<>();//特征点集合

    /**
     * 记录上一次的坐标位置
     */
    private float toX;
    private float toY;

    public ActionView(Context context) {
        this(context, null);
    }

    public ActionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint2=new Paint();
        paint2.setColor(Color.RED);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);//描边,空心,如果是FILL画出来就会是实心的了
        path = new Path();//创建曲线
        handler = new Handler();
        new Thread(new MyRun()).start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStrokeWidth(canvas.getWidth() / 20);
       // canvas.drawPath(path, paint);//用画笔画这条曲线
        for (int i=0;i<lPoints.size();i++){
            for (int j=0;j<lPoints.get(i).size()-1;j++){
                canvas.drawCircle(lPoints.get(i).get(j).x,lPoints.get(i).get(j).y,canvas.getWidth() / 40,paint2);
                canvas.drawLine(lPoints.get(i).get(j).x,lPoints.get(i).get(j).y,lPoints.get(i).get(j+1).x,lPoints.get(i).get(j+1).y,paint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (!finish) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                   // path.reset();//重置,每次按下就会清屏了
                    points=new ArrayList<>();
                    lPoints.add(points);
                    finishTemp = 0;
                    startCount = false;
                    toX = event.getX();
                    toY = event.getY();
                    points.add(new Point((int)toX,(int)toY));
                    path.moveTo(toX, toY);//曲线的起点位置
                    break;
                case MotionEvent.ACTION_MOVE:
                    toX = event.getX();
                    toY = event.getY();
                    path.lineTo(toX, toY);//曲线连接到该点
                    points.add(new Point((int)toX,(int)toY));
                    break;
                case MotionEvent.ACTION_UP:
                    startCount = true;

                    break;
            }
            invalidate();//重绘
        }
        return true;

    }

    class MyRun implements Runnable {
        @Override
        public void run() {
            while (!finish) {
                try {
                    if (startCount & !finish) {
                        Thread.sleep(400);
                        finishTemp++;
                        if (finishTemp >= 4) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    RegisterActionActivity.registerActionActivity.finishAction();
                                }
                            });
                            startCount = false;
                            finish = true;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void reSet() {
        lPoints.clear();
        iPoints.clear();
        path.reset();
        finish = false;
        new Thread(new MyRun()).start();
    }
}
