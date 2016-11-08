package bkoruznjak.from.hr.androidnarodniconcept;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
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

    private SecondCustomSurfaceView.RenderThread mRenderThread;
    private SurfaceHolder fudgeHolder;
    private static final int TARGET_FPS = 60;
    private int holderSize;
    public boolean isRunning = false;
    GraphicCircleFudge redFudge;
    GraphicCircleFudge blueFudge;
    GraphicCircleFudge pinkFudge;
    GraphicCircleFudge orangeFudge;

    final int INITIAL_BITMAP_SCALE = 300;


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
        fudgeHolder = getHolder();
        fudgeHolder.addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                holderSize = fudgeHolder.getSurfaceFrame().right;
                Log.d("bbb", "size:" + holderSize);
                redFudge = new GraphicCircleFudge(R.drawable.ic_red_particle, INITIAL_BITMAP_SCALE, holderSize);
                redFudge.setMaxSize(200);
                blueFudge = new GraphicCircleFudge(R.drawable.ic_blue_particle, INITIAL_BITMAP_SCALE, holderSize);
                orangeFudge = new GraphicCircleFudge(R.drawable.ic_orange_particle, INITIAL_BITMAP_SCALE, holderSize);
                pinkFudge = new GraphicCircleFudge(R.drawable.ic_pink_particle, INITIAL_BITMAP_SCALE, holderSize);
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
            redFudge.handleSizeGrowth(1);
            canvas.drawBitmap(redFudge.getFudgeBitmap(), redFudge.transformFudge(1), null);

            blueFudge.handleSizeGrowth(1);
            canvas.drawBitmap(blueFudge.getFudgeBitmap(), blueFudge.transformFudge(2), null);

            orangeFudge.handleSizeGrowth(1);
            canvas.drawBitmap(orangeFudge.getFudgeBitmap(), orangeFudge.transformFudge(3), null);

            pinkFudge.handleSizeGrowth(1);
            canvas.drawBitmap(pinkFudge.getFudgeBitmap(), pinkFudge.transformFudge(4), null);
        }
    }

    private class GraphicCircleFudge {
        private final int SURFACE_SIZE;
        private Bitmap bmpIconOriginal;
        private Bitmap bmpScaledIcon;
        private Matrix morphMatrix;
        private int minSizeScale = 1;
        private int maxSizeScale = 100;
        private int currentSize = 2;
        private boolean isGrowing;

        public GraphicCircleFudge(int resurceId, final int INITIAL_BITMAP_SCALE, final int SURFACE_SIZE) {
            this.bmpIconOriginal = BitmapFactory.decodeResource(getResources(),
                    resurceId);
            this.bmpScaledIcon = Bitmap.createScaledBitmap(bmpIconOriginal, INITIAL_BITMAP_SCALE, INITIAL_BITMAP_SCALE, false);
            this.morphMatrix = new Matrix();
            this.SURFACE_SIZE = SURFACE_SIZE;
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

        public Bitmap getFudgeBitmap() {
            return this.bmpScaledIcon;
        }

        public void setGrowing(boolean isGrowing) {
            this.isGrowing = isGrowing;
        }

        public void handleSizeGrowth(int sizeDelta) {
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
            morphMatrix.postScale(1f + ((float) currentSize / 100), 1f + ((float) currentSize / 100));
            switch (position) {
                case 1:
                    //upper left corner
                    morphMatrix.postTranslate(-bmpScaledIcon.getHeight() / 2f - currentSize, -bmpScaledIcon.getHeight() / 2f - currentSize);
                    break;
                case 2:
                    //upper right corner
                    morphMatrix.postTranslate(SURFACE_SIZE - (bmpScaledIcon.getHeight() / 2f + currentSize), -bmpScaledIcon.getHeight() / 2f - currentSize);
                    break;
                case 3:
                    //lower right corner
                    morphMatrix.postTranslate(SURFACE_SIZE - (bmpScaledIcon.getHeight() / 2f + currentSize), SURFACE_SIZE - (bmpScaledIcon.getHeight() / 2f + currentSize));
                    break;
                case 4:
                    //lower left corner
                    morphMatrix.postTranslate(-bmpScaledIcon.getHeight() / 2f - currentSize, SURFACE_SIZE - (bmpScaledIcon.getHeight() / 2f + currentSize));
                    break;
                default:
                    break;
            }
            return morphMatrix;
        }
    }
}
