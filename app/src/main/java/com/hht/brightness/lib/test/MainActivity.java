package com.hht.brightness.lib.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.hht.brightness.BrightnessPlatform;
import com.hht.brightness.impl.BaseBrightnessImpl;
import com.hht.brightness.impl.BrightnessFactory;
import com.hht.brightness.strategy.change.IBrightnessChange;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SeekBar sb_standard_imm;
    private SeekBar sb_standard_gra;

    private Button btn1,btn2;

    private BaseBrightnessImpl standardImm;
    private BaseBrightnessImpl standardGra;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBrightness();
        initView();

    }

    private void initBrightness() {
        standardImm = BrightnessFactory.createBrightnessImpl(getApplication(), BrightnessPlatform.PLATFORM_STANDARD, IBrightnessChange.Strategy.IMMEDIATELY);
        standardGra = BrightnessFactory.createBrightnessImpl(getApplication(), BrightnessPlatform.PLATFORM_STANDARD, IBrightnessChange.Strategy.GRADIENT);
    }

    private void initView() {

        sb_standard_imm = findViewById(R.id.sb_standard_imm);
        sb_standard_gra  = findViewById(R.id.sb_standard_gra);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:{
                standardImm.setBrightness(new Random().nextInt(100));
            }break;
            case R.id.btn2:{
                standardGra.setBrightness(new Random().nextInt(100));
            }break;
        }
    }
}
