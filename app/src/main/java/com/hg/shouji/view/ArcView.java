package com.hg.shouji.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hg.shouji.R;
import com.hg.shouji.RegisterFaceActivity;


public class ArcView extends View {

    int p = 0;

    int type = 0;

    int color = Color.rgb(180, 180, 180);


    public ArcView(Context context) {
        super(context, null);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ArcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    RectF rect=new RectF();
    Paint paint = new Paint();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (type == 0) {
            color = Color.rgb(180, 180, 180);
        } else if (type == 1) {
            color = Color.rgb(35, 222, 25);
        }
        int W = getWidth() / 4 * 3;
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        if (p>100) {
            Bitmap bitmap = BitmapFactory.decodeResource(RegisterFaceActivity.registerFaceActivity.getResources(), R.drawable.right_big);

            canvas.drawBitmap(zoomImage(bitmap,W,W),(getWidth() - W) / 2, (int) (getHeight() - W) / 2,paint);
        }
        rect.set(-(getHeight()-getWidth())/2,0,getWidth()+(getHeight()-getWidth())/2,getHeight());
        paint.setStrokeWidth((getHeight() - W));
        paint.setColor(Color.rgb(250,250,250));
        canvas.drawArc(rect, 0, 360, false, paint);

        rect.set((int) (getWidth() - W) / 2, (int) (getHeight() - W) / 2, (int) (getWidth() - W) / 2 + W, (getHeight() - W) / 2 + W);
        paint.setStrokeWidth(10);
        paint.setColor(color);
        canvas.drawArc(rect, 0, 360, false, paint);

        paint.setColor(Color.rgb(25, 154, 222));
        canvas.drawArc(rect, 0, (int) (3.6 * p), false, paint);

    }

    public void setProcess(int p, int type) {
        this.type = type;
        this.p = p;
    }

    public Bitmap zoomImage(Bitmap bgimage, double newWidth,
                            double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }


}
