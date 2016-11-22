package bkoruznjak.from.hr.androidnarodniconcept;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;

/**
 * Created by bkoruznjak on 14/11/2016.
 */

public class CustomRectangle extends View {

    SurfaceHolder mHolder;
    Paint mStrokePaint;
    Bitmap canvasBitmap;
    int blueColor = Color.argb(255, 9, 126, 161);
    int purpleColor = Color.argb(255, 135, 30, 128);
    int greenColor = Color.argb(255, 104, 182, 86);
    int yellowColor = Color.argb(255, 251, 186, 51);
    int grayColor = Color.argb(255, 127, 118, 101);
    int redColor = Color.argb(255, 230, 5, 19);

    public CustomRectangle(Context context) {
        super(context);
        init();
    }

    public CustomRectangle(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRectangle(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mStrokePaint = new Paint();
        mStrokePaint.setColor(Color.WHITE);
        mStrokePaint.setStrokeWidth(15);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        this.setDrawingCacheEnabled(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        int numberOfZones = 6;
        int frameWidth = canvas.getWidth();
        int frameHeigth = canvas.getHeight();

        for (int zoneId = 1; zoneId <= numberOfZones; zoneId++) {
            Path zonePath = generatePathForZoneInRectangle(frameWidth, frameHeigth, zoneId);
            Paint fillPaint = new Paint();
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setAntiAlias(true);
            switch (zoneId) {
                case 1:
                    fillPaint.setColor(blueColor);
                    break;
                case 2:
                    fillPaint.setColor(purpleColor);
                    break;
                case 3:
                    fillPaint.setColor(greenColor);
                    break;
                case 4:
                    fillPaint.setColor(yellowColor);
                    break;
                case 5:
                    fillPaint.setColor(grayColor);
                    break;
                case 6:
                    fillPaint.setColor(redColor);
                    break;
                default:
                    break;
            }
            canvas.drawPath(zonePath, fillPaint);
            canvas.drawPath(zonePath, mStrokePaint);

        }

        canvasBitmap = getDrawingCache(true);
    }

    private Path generatePathForZoneInRectangle(int rectangleWidth, int rectangleHeight, int zone) {
        boolean zoneHasFourPoints = true;
        Point firstPoint = new Point();
        Point secondPoint = new Point();
        Point thirdPoint = new Point();
        Point fourthPoint = new Point();
        switch (zone) {
            case 1:
                firstPoint.set(0, 0);
                secondPoint.set(0, rectangleHeight / 2);
                thirdPoint.set(rectangleWidth / 2, rectangleHeight / 2);
                fourthPoint.set(rectangleWidth / 6, 0);
                break;
            case 2:
                zoneHasFourPoints = false;
                firstPoint.set(rectangleWidth / 6, 0);
                secondPoint.set(rectangleWidth / 2, rectangleHeight / 2);
                thirdPoint.set((rectangleWidth / 6) * 5, 0);
                break;
            case 3:
                firstPoint.set((rectangleWidth / 6) * 5, 0);
                secondPoint.set(rectangleWidth / 2, rectangleHeight / 2);
                thirdPoint.set(rectangleWidth, rectangleHeight / 2);
                fourthPoint.set(rectangleWidth, 0);
                break;
            case 4:
                firstPoint.set(rectangleWidth, rectangleHeight / 2);
                secondPoint.set(rectangleWidth, rectangleHeight);
                thirdPoint.set((rectangleWidth / 6) * 5, rectangleHeight);
                fourthPoint.set(rectangleWidth / 2, rectangleHeight / 2);
                break;
            case 5:
                zoneHasFourPoints = false;
                firstPoint.set(rectangleWidth / 2, rectangleHeight / 2);
                secondPoint.set(rectangleWidth / 6, rectangleHeight);
                thirdPoint.set((rectangleWidth / 6) * 5, rectangleHeight);
                break;
            case 6:
                firstPoint.set(rectangleWidth / 2, rectangleHeight / 2);
                secondPoint.set(rectangleWidth / 6, rectangleHeight);
                thirdPoint.set(0, rectangleHeight);
                fourthPoint.set(0, rectangleHeight / 2);
                break;
            default:
                break;

        }

        Path zonePath = new Path();
        zonePath.setFillType(Path.FillType.EVEN_ODD);
        zonePath.moveTo(firstPoint.x, firstPoint.y);
        zonePath.lineTo(secondPoint.x, secondPoint.y);
        zonePath.lineTo(thirdPoint.x, thirdPoint.y);
        if (zoneHasFourPoints) {
            zonePath.lineTo(fourthPoint.x, fourthPoint.y);
        }
        zonePath.close();
        return zonePath;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int color = canvasBitmap.getPixel((int) event.getX(), (int) event.getY());
        Log.d("bbb", "color :" + color);

        if (color == blueColor) {
            Log.d("bbb", "blue pressed");
        }

        if (color == purpleColor) {
            Log.d("bbb", "purple pressed");
        }

        if (color == greenColor) {
            Log.d("bbb", "green pressed");
        }

        if (color == yellowColor) {
            Log.d("bbb", "yellow pressed");
        }

        if (color == grayColor) {
            Log.d("bbb", "gray pressed");
        }

        if (color == redColor) {
            Log.d("bbb", "red pressed");
        }

//        return super.onTouchEvent(event);
        return false;
    }
}