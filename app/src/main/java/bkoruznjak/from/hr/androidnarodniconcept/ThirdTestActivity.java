package bkoruznjak.from.hr.androidnarodniconcept;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import bkoruznjak.from.hr.androidnarodniconcept.databinding.ActivityThirdTestActivityBinding;

public class ThirdTestActivity extends AppCompatActivity {

    private ActivityThirdTestActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_third_test_activity);
        binding.btnCenterPie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("bbb", "clicked");
            }
        });
    }
}
