package bkoruznjak.from.hr.androidnarodniconcept;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import bkoruznjak.from.hr.androidnarodniconcept.databinding.ActivityFourthTestBinding;

public class FourthTestActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    ActivityFourthTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fourth_test);
        binding.btnRedrawBottomThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bbb", "button clicked");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("bbb", "on resume");
        binding.surfaceViewCustom.getHolder().addCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (binding.surfaceViewCustom.isRunning) {
            Log.d("bbb", "surfaceview stopping");
            binding.surfaceViewCustom.stop();
        }
        binding.surfaceViewCustom.getHolder().removeCallback(this);
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
}
