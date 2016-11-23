package bkoruznjak.from.hr.androidnarodniconcept;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by bkoruznjak on 07/11/2016.
 */

public class SecondCustomSurfaceView extends SurfaceView {

    private static final int TARGET_FPS = 80;
    final int INITIAL_BITMAP_SCALE = 300;
    public boolean isRunning = false;
    GraphicCircleFudge redFudge;
    GraphicCircleFudge blueFudge;
    GraphicCircleFudge pinkFudge;
    GraphicCircleFudge orangeFudge;
    GraphicCircleFudge grayFudge;
    GraphicCircleFudge greenFudge;
    private SecondCustomSurfaceView.RenderThread mRenderThread;
    private SurfaceHolder fudgeHolder;
    private int holderSize;
    private Paint mStrokePaint;
    private Path mInnerRoundedPath;


    public SecondCustomSurfaceView(Context context) {
        super(context);
        Log.d("bbb", "first constructor");
        init();
    }

    public SecondCustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d("bbb", "second constructor");
        init();
    }

    public SecondCustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("bbb", "third constructor");
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SecondCustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        Log.d("bbb", "fourth constructor");
        init();
    }

    private void init() {
        Log.d("bbb", "init");
        setZOrderOnTop(true);
        this.setDrawingCacheEnabled(true);
        fudgeHolder = getHolder();
        fudgeHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                holderSize = fudgeHolder.getSurfaceFrame().right;
                Log.d("bbb", "size:" + holderSize);
                redFudge = new GraphicCircleFudge(R.drawable.ic_red_particle, INITIAL_BITMAP_SCALE, holderSize);
                blueFudge = new GraphicCircleFudge(R.drawable.ic_blue_particle, INITIAL_BITMAP_SCALE, holderSize);
                orangeFudge = new GraphicCircleFudge(R.drawable.ic_orange_particle, INITIAL_BITMAP_SCALE, holderSize);
                pinkFudge = new GraphicCircleFudge(R.drawable.ic_pink_particle, INITIAL_BITMAP_SCALE, holderSize);
                grayFudge = new GraphicCircleFudge(R.drawable.ic_gray_perticle, INITIAL_BITMAP_SCALE, holderSize);
                greenFudge = new GraphicCircleFudge(R.drawable.ic_green_particle, INITIAL_BITMAP_SCALE, holderSize);
                mInnerRoundedPath = getRoundedPath(0f, 0f, holderSize, holderSize, 30f, 30f, false);
                mStrokePaint = new Paint();
                mStrokePaint.setColor(Color.WHITE);
                mStrokePaint.setStrokeWidth(20);
                mStrokePaint.setStyle(Paint.Style.STROKE);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder,
                                       int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                // TODO Auto-generated method stub

            }
        });
    }

    public void start() {
        Log.d("bbb", "starting");
        if (isRunning) {
            return;
        }
        if (mRenderThread == null || !mRenderThread.isAlive())
            Log.d("bbb", "new thread");
        mRenderThread = new SecondCustomSurfaceView.RenderThread(getHolder());
        isRunning = true;
        mRenderThread.start();
    }


    public void stop() {
        Log.d("bbb", "stopping");
        isRunning = false;
        if (mRenderThread != null) {
            Log.d("bbb", "interuppting thread");
            mRenderThread.interrupt();
        }
        mRenderThread = null;
    }

    /**
     * Method used to draw the inner rounded stroke rectangle to give our surfaceview a nice rounded feel
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     * @param rx
     * @param ry
     * @param conformToOriginalPost
     * @return
     */
    private Path getRoundedPath(float left, float top, float right, float bottom, float rx, float ry, boolean conformToOriginalPost) {
        Path path = new Path();
        if (rx < 0) rx = 0;
        if (ry < 0) ry = 0;
        float width = right - left;
        float height = bottom - top;
        if (rx > width / 2) rx = width / 2;
        if (ry > height / 2) ry = height / 2;
        float widthMinusCorners = (width - (2 * rx));
        float heightMinusCorners = (height - (2 * ry));

        path.moveTo(right, top + ry);
        path.rQuadTo(0, -ry, -rx, -ry);//top-right corner
        path.rLineTo(-widthMinusCorners, 0);
        path.rQuadTo(-rx, 0, -rx, ry); //top-left corner
        path.rLineTo(0, heightMinusCorners);

        if (conformToOriginalPost) {
            path.rLineTo(0, ry);
            path.rLineTo(width, 0);
            path.rLineTo(0, -ry);
        } else {
            path.rQuadTo(0, ry, rx, ry);//bottom-left corner
            path.rLineTo(widthMinusCorners, 0);
            path.rQuadTo(rx, 0, rx, -ry); //bottom-right corner
        }

        path.rLineTo(0, -heightMinusCorners);

        path.close();//Given close, last lineto can be removed.

        return path;
    }

    private class RenderThread extends Thread {
        private SurfaceHolder surfaceHolder;

        RenderThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            this.surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        }

        @Override
        public void run() {
            while (isRunning) {
                if (surfaceHolder != null && surfaceHolder.getSurface().isValid()) {
                    try {
                        Canvas canvas = surfaceHolder.lockCanvas();
                        if (canvas == null) {
                            continue;
                        }
                        draw(canvas);
                        surfaceHolder.unlockCanvasAndPost(canvas);
                        //FPS limit
                        Thread.sleep(TARGET_FPS);
                    } catch (Exception e) {
                        Log.e("bbb", "error:" + e);
                    }
                }
            }
        }

        private void draw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);

            blueFudge.handleSizeGrowth(0.1f);
            blueFudge.setMinSize(60);
            blueFudge.setMaxSize(80);
            canvas.drawBitmap(blueFudge.getFudgeBitmap(), blueFudge.transformFudge(1), null);

            pinkFudge.handleSizeGrowth(0.2f);
            pinkFudge.setMinSize(50);
            pinkFudge.setMaxSize(90);
            canvas.drawBitmap(pinkFudge.getFudgeBitmap(), pinkFudge.transformFudge(2), null);

            greenFudge.handleSizeGrowth(0.3f);
            greenFudge.setMinSize(80);
            greenFudge.setMaxSize(100);
            canvas.drawBitmap(greenFudge.getFudgeBitmap(), greenFudge.transformFudge(3), null);

            orangeFudge.handleSizeGrowth(0.4f);
            orangeFudge.setMinSize(80);
            orangeFudge.setMaxSize(100);
            canvas.drawBitmap(orangeFudge.getFudgeBitmap(), orangeFudge.transformFudge(4), null);

            grayFudge.handleSizeGrowth(0.5f);
            grayFudge.setMinSize(80);
            grayFudge.setMaxSize(100);
            canvas.drawBitmap(grayFudge.getFudgeBitmap(), grayFudge.transformFudge(5), null);

            redFudge.handleSizeGrowth(0.6f);
            redFudge.setMinSize(70);
            redFudge.setMaxSize(100);
            canvas.drawBitmap(redFudge.getFudgeBitmap(), redFudge.transformFudge(6), null);

            canvas.drawPath(mInnerRoundedPath, mStrokePaint);
        }
    }

    private class GraphicCircleFudge {
        private final int SURFACE_SIZE;
        private final int HALF_SURFACE_SIZE;
        private final int QUARTER_SURFACE_SIZE;
        private final int THREE_QUARTERS_SURFACE_SIZE;
        private Bitmap bmpIconOriginal;
        private Bitmap bmpScaledIcon;
        private int halfBitmapSize;
        private Matrix morphMatrix;
        private int minSizeScale = 1;
        private int maxSizeScale = 100;
        private float currentSize = 2f;
        private boolean isGrowing;

        public GraphicCircleFudge(int resurceId, final int INITIAL_BITMAP_SCALE, final int SURFACE_SIZE) {
            this.bmpIconOriginal = BitmapFactory.decodeResource(getResources(),
                    resurceId);
            this.bmpScaledIcon = Bitmap.createScaledBitmap(bmpIconOriginal, INITIAL_BITMAP_SCALE, INITIAL_BITMAP_SCALE, false);
            this.halfBitmapSize = bmpScaledIcon.getHeight() / 2;
            this.morphMatrix = new Matrix();
            this.SURFACE_SIZE = SURFACE_SIZE;
            this.HALF_SURFACE_SIZE = SURFACE_SIZE / 2;
            this.QUARTER_SURFACE_SIZE = SURFACE_SIZE / 4;
            this.THREE_QUARTERS_SURFACE_SIZE = (SURFACE_SIZE / 4) * 3;
        }

        public synchronized void setMaxSize(int maxSize) {
            //we do not want to mess up the growth that may be running
            if (currentSize > maxSize && isGrowing) {
                currentSize = maxSize;
            }
            this.maxSizeScale = maxSize;
        }

        public synchronized void setMinSize(int minSize) {
            //we do not want to mess up the growth that may be running
            if (currentSize < minSize && !isGrowing) {
                currentSize = minSize;
            }
            this.minSizeScale = minSize;
        }

        public boolean isGrowing() {
            return this.isGrowing;
        }

        public void setGrowing(boolean isGrowing) {
            this.isGrowing = isGrowing;
        }

        public Bitmap getFudgeBitmap() {
            return this.bmpScaledIcon;
        }

        public void handleSizeGrowth(float sizeDelta) {
            if (currentSize == maxSizeScale) {
                isGrowing = false;
            }

            if (currentSize == minSizeScale) {
                isGrowing = true;
            }

            if (isGrowing) {
                currentSize += sizeDelta;
            } else {
                currentSize -= sizeDelta;
            }
        }

        public Matrix transformFudge(int position) {
            morphMatrix.reset();
            morphMatrix.postScale(1f + (currentSize / 100), 1f + (currentSize / 100));
            switch (position) {
                case 1:
                    //upper left corner
                    morphMatrix.postTranslate(-halfBitmapSize - currentSize - 40, QUARTER_SURFACE_SIZE - (halfBitmapSize + currentSize + 40));
                    break;
                case 2:
                    //top corner
                    morphMatrix.postTranslate(HALF_SURFACE_SIZE - (halfBitmapSize + currentSize + 40), -halfBitmapSize - currentSize - 40);
                    break;
                case 3:
                    //upper right corner
                    morphMatrix.postTranslate(SURFACE_SIZE - (halfBitmapSize + currentSize - 40), QUARTER_SURFACE_SIZE - (halfBitmapSize + currentSize + 80));
                    break;
                case 4:
                    //lower right corner
                    morphMatrix.postTranslate(SURFACE_SIZE - (halfBitmapSize + currentSize), THREE_QUARTERS_SURFACE_SIZE - (halfBitmapSize + currentSize));
                    break;
                case 5:
                    //bottom corner
                    morphMatrix.postTranslate(HALF_SURFACE_SIZE - (halfBitmapSize + currentSize), SURFACE_SIZE - (halfBitmapSize + currentSize));
                    break;
                case 6:
                    //lower left corner
                    morphMatrix.postTranslate(-halfBitmapSize - currentSize - 80, THREE_QUARTERS_SURFACE_SIZE - (halfBitmapSize + currentSize));
                    break;
                default:
                    break;
            }
            return morphMatrix;
        }
    }
}
