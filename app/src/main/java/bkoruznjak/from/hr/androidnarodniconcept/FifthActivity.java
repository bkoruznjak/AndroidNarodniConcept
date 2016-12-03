package bkoruznjak.from.hr.androidnarodniconcept;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.drawable.PaintDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import bkoruznjak.from.hr.androidnarodniconcept.databinding.ActivityFifthBinding;

public class FifthActivity extends AppCompatActivity {

    ActivityFifthBinding binding;
    int blueColor = Color.argb(255, 9, 126, 161);
    int purpleColor = Color.argb(255, 135, 30, 128);
    int greenColor = Color.argb(255, 104, 182, 86);
    int yellowColor = Color.argb(255, 251, 186, 51);
    int grayColor = Color.argb(255, 127, 118, 101);
    int redColor = Color.argb(255, 230, 5, 19);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_fifth);

        ShapeDrawable.ShaderFactory sf = new ShapeDrawable.ShaderFactory() {


            @Override
            public Shader resize(int width, int height) {
                LinearGradient lg = new LinearGradient(0, 0, binding.randomView.getWidth(), 0,
                        new int[]{
                                blueColor,
                                purpleColor,
                                greenColor,
                                yellowColor,
                                grayColor,
                                redColor}, //substitute the correct colors for these
                        new float[]{
                                0, 0.2f, 0.4f, 0.6f, 0.8f, 1.0f},
                        Shader.TileMode.REPEAT);
                return lg;
            }
        };
        PaintDrawable p = new PaintDrawable();
        p.setShape(new RectShape());
        p.setShaderFactory(sf);
        binding.randomView.setBackgroundDrawable(p);
    }
}
