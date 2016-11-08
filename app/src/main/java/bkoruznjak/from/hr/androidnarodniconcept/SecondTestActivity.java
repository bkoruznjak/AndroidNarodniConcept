package bkoruznjak.from.hr.androidnarodniconcept;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import bkoruznjak.from.hr.androidnarodniconcept.databinding.ActivitySecondTestBinding;

public class SecondTestActivity extends AppCompatActivity {

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
}
