package bkoruznjak.from.hr.androidnarodniconcept;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by bkoruznjak on 05/11/2016.
 */

public class CustomSurfaceView extends SurfaceView {

    public static final float FRAME_RATIO = 1.25f;
    private SurfaceHolder surfaceHolder;
    /**
     * emo bitmaps start
     */
    final int INITIAL_BITMAP_SCALE = 300;
    private Bitmap bmpRedIconOriginal;
    private Bitmap bmpRedIcon;

    private Bitmap bmpBlueIconOriginal;
    private Bitmap bmpBlueIcon;

    private Bitmap bmpOrangeIconOriginal;
    private Bitmap bmpOrangeIcon;

    private Bitmap bmpPinkIconOriginal;
    private Bitmap bmpPinkIcon;

    private Bitmap bmpGreenIconOriginal;
    private Bitmap bmpGreenIcon;

    private Bitmap bmpRedIconOriginalTwo;
    private Bitmap bmpRedIconTwo;
    /**
     * emo bitmaps end
     */
    private Rect mFrameToDraw;
    private RectF whereToDraw;
    Point screenSize;
    int[] locationXY;

    int leftOffset;
    int topOffset;

    int startX;
    int startY;

    public CustomSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public CustomSurfaceView(Context context,
                             AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomSurfaceView(Context context,
                             AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        surfaceHolder = getHolder();
        bmpRedIconOriginal = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_red_particle);

        bmpRedIcon = Bitmap.createScaledBitmap(bmpRedIconOriginal, INITIAL_BITMAP_SCALE, INITIAL_BITMAP_SCALE, false);

        bmpRedIconOriginalTwo = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_red_particle);

        bmpRedIconTwo = Bitmap.createScaledBitmap(bmpRedIconOriginalTwo, INITIAL_BITMAP_SCALE, INITIAL_BITMAP_SCALE, false);

        bmpGreenIconOriginal = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_green_particle);

        bmpGreenIcon = Bitmap.createScaledBitmap(bmpGreenIconOriginal, INITIAL_BITMAP_SCALE, INITIAL_BITMAP_SCALE, false);

        bmpBlueIconOriginal = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_blue_particle);

        bmpBlueIcon = Bitmap.createScaledBitmap(bmpBlueIconOriginal, INITIAL_BITMAP_SCALE, INITIAL_BITMAP_SCALE, false);

        bmpOrangeIconOriginal = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_orange_particle);

        bmpOrangeIcon = Bitmap.createScaledBitmap(bmpOrangeIconOriginal, INITIAL_BITMAP_SCALE, INITIAL_BITMAP_SCALE, false);

        bmpPinkIconOriginal = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_pink_particle);

        bmpPinkIcon = Bitmap.createScaledBitmap(bmpPinkIconOriginal, INITIAL_BITMAP_SCALE, INITIAL_BITMAP_SCALE, false);


        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {
                // TODO Auto-generated method stub
                locationXY = new int[2];
                getLocationOnScreen(locationXY);
                Canvas canvas = holder.lockCanvas(null);
                drawSomething(canvas, holder);
                holder.unlockCanvasAndPost(canvas);
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }
        });
    }

    protected void drawSomething(Canvas canvas, SurfaceHolder holder) {
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bmpRedIcon,
                -(bmpRedIcon.getWidth() / 2), -(bmpRedIcon.getWidth() / 2), null);

        canvas.drawBitmap(bmpBlueIcon,
                holder.getSurfaceFrame().right - (bmpBlueIcon.getWidth() / 2), -(bmpBlueIcon.getWidth() / 2), null);

        canvas.drawBitmap(bmpOrangeIcon,
                holder.getSurfaceFrame().right - (bmpOrangeIcon.getWidth() / 2), holder.getSurfaceFrame().right - (bmpOrangeIcon.getWidth() / 2), null);

        canvas.drawBitmap(bmpPinkIcon,
                -(bmpPinkIcon.getWidth() / 2), holder.getSurfaceFrame().right - (bmpPinkIcon.getWidth() / 2), null);

//        canvas.drawBitmap(bmpGreenIcon,50,100,null);

        canvas.drawBitmap(bmpRedIconTwo,150,100,null);

        canvas.drawBitmap(bmpGreenIcon,50,100,null);
    }

    public void changeMood(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);

                } catch (InterruptedException intEx){

                }
            }
        });
        thread.start();
    }
}