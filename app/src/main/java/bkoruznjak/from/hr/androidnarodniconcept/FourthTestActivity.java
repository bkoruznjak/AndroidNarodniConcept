package bkoruznjak.from.hr.androidnarodniconcept;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;

import bkoruznjak.from.hr.androidnarodniconcept.databinding.ActivityFourthTestBinding;

public class FourthTestActivity extends AppCompatActivity implements SurfaceHolder.Callback, FudgeClickListener {

    private final int LOGO_IMAGES[] = {R.drawable.img_blue_logo, R.drawable.img_purple_logo, R.drawable.img_green_logo, R.drawable.img_orange_logo, R.drawable.img_gray_logo, R.drawable.img_red_logo};
    ActivityFourthTestBinding binding;
    TransitionDrawable mTransitionDrawable;
    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fourth_test);
        mTransitionDrawable = new TransitionDrawable(new Drawable[]{getResources().getDrawable(LOGO_IMAGES[0]),
                getResources().getDrawable(LOGO_IMAGES[0])});
        binding.imgLogo.setImageDrawable(mTransitionDrawable);
        mHandler = new Handler();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("bbb", "on resume");
        binding.surfaceViewCustom.getHolder().addCallback(this);
        binding.btnCustomRectangle.registerFudgeClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (binding.surfaceViewCustom.isRunning) {
            Log.d("bbb", "surfaceview stopping");
            binding.surfaceViewCustom.stop();
        }
        binding.surfaceViewCustom.getHolder().removeCallback(this);
        binding.btnCustomRectangle.unregisterFudgeClickListener();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d("bbb", "layout is drawn");
        if (binding.surfaceViewCustom.isRunning) {
            Log.d("bbb", "surfaceview stopping");
            binding.surfaceViewCustom.stop();
        } else {
            Log.d("bbb", "surfaceview starting");
            binding.surfaceViewCustom.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onFudgePressed(int fudgeId) {
        if (binding.surfaceViewCustom.isRunning) {
            binding.surfaceViewCustom.growFudge(fudgeId);

            switchOutLogo(fudgeId);


        }

    }

    public void switchOutLogo(int logoId) {
        Drawable oldDrawable = mTransitionDrawable.getDrawable(1);
        mTransitionDrawable = new TransitionDrawable(new Drawable[]{
                oldDrawable,
                getResources().getDrawable(LOGO_IMAGES[logoId - 1])
        });
        binding.imgLogo.setImageDrawable(mTransitionDrawable);
        mTransitionDrawable.startTransition(2000);


    }

}
