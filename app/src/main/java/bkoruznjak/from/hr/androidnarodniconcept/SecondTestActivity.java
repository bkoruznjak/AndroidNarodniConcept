package bkoruznjak.from.hr.androidnarodniconcept;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.View;

import bkoruznjak.from.hr.androidnarodniconcept.databinding.ActivitySecondTestBinding;

public class SecondTestActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    ActivitySecondTestBinding bindingTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingTwo = DataBindingUtil.setContentView(this, R.layout.activity_second_test);
        bindingTwo.btnRedrawBottomTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bindingTwo.surfaceViewTwo.isRunning) {
                    Log.d("bbb","surfaceview stopping");
                    bindingTwo.surfaceViewTwo.stop();
                } else {
                    Log.d("bbb","surfaceview starting");
                    bindingTwo.surfaceViewTwo.start();
                }

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        bindingTwo.surfaceViewTwo.getHolder().addCallback(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (bindingTwo.surfaceViewTwo.isRunning) {
            Log.d("bbb", "surfaceview stopping");
            bindingTwo.surfaceViewTwo.stop();
        }
        bindingTwo.surfaceViewTwo.getHolder().removeCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d("bbb", "surface created");
        if (bindingTwo.surfaceViewTwo.isRunning) {
            Log.d("bbb", "surfaceview stopping");
            bindingTwo.surfaceViewTwo.stop();
        } else {
            Log.d("bbb", "surfaceview starting");
            bindingTwo.surfaceViewTwo.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
