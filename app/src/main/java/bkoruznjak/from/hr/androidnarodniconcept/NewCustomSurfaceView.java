package bkoruznjak.from.hr.androidnarodniconcept;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by bkoruznjak on 06/11/2016.
 */

public class NewCustomSurfaceView extends SurfaceView {
    private static final int TARGET_FPS = 25;
    public static final float FRAME_RATIO = 1.25f;
    private boolean isRunning = false;

    private int spriteSheetWidth;
    private int currentFrame = 0;
    private int frameCount;
    private float targetFrameDrawTime;
    private Paint paint;

    private Bitmap mSpriteSheet;
    private Rect mFrameToDraw;
    private RectF whereToDraw;
    private RenderThread mRenderThread;

    public void setSpriteSheet(int animationSpriteSheetId, int keyboardWidth, int keyboardHeight) {
        if(isRunning) {
            return;
        }
        attemptRecycle(mSpriteSheet);
        stop();
        mSpriteSheet = BitmapFactory.decodeResource(getResources(), animationSpriteSheetId, null);

        int frameHeight = mSpriteSheet.getHeight();
        int frameWidth = (int) (frameHeight * FRAME_RATIO);

        frameCount = (int) (mSpriteSheet.getWidth() / (mSpriteSheet.getHeight() * FRAME_RATIO));
        spriteSheetWidth = (int) (mSpriteSheet.getHeight() * FRAME_RATIO);

        float scaleWidthFactor = keyboardWidth / (float) frameWidth;
        frameHeight = (int) (frameHeight * scaleWidthFactor);
        frameWidth = (int) (frameWidth * scaleWidthFactor);

        float scaleHeightFactor = keyboardHeight / (float) frameHeight;
        frameHeight = (int) (frameHeight * scaleHeightFactor);
        frameWidth = (int) (frameWidth * scaleHeightFactor);

        // Align at bottom
        int leftOffset = (int) ((keyboardWidth - frameWidth) / 2f);
        int topOffset = (int) ((keyboardHeight - frameHeight) / 2f);

        whereToDraw = new RectF(leftOffset, topOffset, frameWidth + leftOffset, frameHeight + topOffset);

        mFrameToDraw = new Rect(0, 0, spriteSheetWidth, mSpriteSheet.getHeight());
        currentFrame = 0;
        // Scale the bitmap
        mSpriteSheet = mSpriteSheet;
    }

    private void attemptRecycle(Bitmap bitmap) {
        if (bitmap != null) {
            bitmap.recycle();
        }
    }

    private void init() {
        setZOrderOnTop(true);

        // TODO remove this when development is finished
        // This paint is only for fps info drawing
        paint = new Paint();
        paint.setTextSize(50);
        paint.setColor(Color.argb(255, 249, 129, 0));

        targetFrameDrawTime = 1000f / TARGET_FPS;
    }

    public NewCustomSurfaceView(Context context) {
        super(context);
        init();
    }

    public NewCustomSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NewCustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NewCustomSurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void getCurrentFrame() {
        mFrameToDraw.left = currentFrame * spriteSheetWidth;
        mFrameToDraw.right = mFrameToDraw.left + spriteSheetWidth;
        currentFrame++;
    }

    public void start() {
        if(isRunning) {
            return;
        }
        currentFrame = 0;
        if (mRenderThread == null || !mRenderThread.isAlive())
            mRenderThread = new RenderThread(getHolder());
        isRunning = true;
        mRenderThread.start();
    }


    public void stop() {
        isRunning = false;
        if (mRenderThread != null) {
            mRenderThread.interrupt();
        }
        mRenderThread = null;
        currentFrame = 0;
        attemptRecycle(mSpriteSheet);
    }


    private class RenderThread extends Thread {
        private SurfaceHolder surfaceHolder;
        private long startTimeCurrentFrame;
        private long delta;
        private long endTimeCurrentFrame;
        private long fps;
        private long sleepTimeInMillis;

        RenderThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
            this.surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        }

        @Override
        public void run() {
            while (isRunning) {
                if (surfaceHolder != null && surfaceHolder.getSurface().isValid()) {
                    try {
                        startTimeCurrentFrame = System.nanoTime() / 1000000;
                        Canvas canvas = surfaceHolder.lockCanvas();
                        if (canvas == null) {
                            continue;
                        }

                        draw(canvas);

                        if (currentFrame > frameCount - 1) {
                            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                        }

                        surfaceHolder.unlockCanvasAndPost(canvas);
                        //FPS limit
                        endTimeCurrentFrame = System.nanoTime() / 1000000;
                        delta = endTimeCurrentFrame - startTimeCurrentFrame;
                        sleepTimeInMillis = (long) (targetFrameDrawTime - delta);
                        if (sleepTimeInMillis > 0) {
                            Thread.sleep(sleepTimeInMillis);
                        }

                        // Current FPS after calculated sleep time
                        fps = 1000 / (System.nanoTime() / 1000000 - startTimeCurrentFrame);
                        if (currentFrame > frameCount - 1) {
                            currentFrame = 0;
                            NewCustomSurfaceView.this.stop();
                        }
                    } catch (Exception e) {
                        Log.e("bbb","error:"+e);
                    }
                }
            }
        }

        private void draw(Canvas canvas) {
            if (mSpriteSheet.isRecycled()) return;
            getCurrentFrame();
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
            canvas.drawBitmap(mSpriteSheet, mFrameToDraw, whereToDraw, null);
            drawFps(canvas);
        }

        private void drawFps(Canvas canvas) {
            canvas.drawText("FPS:" + fps, 50, 400, paint);
        }
    }
}
