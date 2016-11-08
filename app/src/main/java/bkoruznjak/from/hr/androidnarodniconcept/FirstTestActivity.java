package bkoruznjak.from.hr.androidnarodniconcept;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bkoruznjak.from.hr.androidnarodniconcept.databinding.ActivityFirstTestBinding;

public class FirstTestActivity extends AppCompatActivity {

    ActivityFirstTestBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_test);
    }
}
