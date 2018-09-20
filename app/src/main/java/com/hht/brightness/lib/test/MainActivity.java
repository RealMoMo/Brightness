package com.hht.brightness.lib.test;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.hht.brightness.BrightnessPlatform;
import com.hht.brightness.i.CustomChangeListener;
import com.hht.brightness.impl.BaseBrightnessImpl;
import com.hht.brightness.impl.BrightnessFactory;
import com.hht.brightness.strategy.change.IBrightnessChange;
import com.hht.brightness.utils.DefaultLogger;


import java.util.Random;

/**
 * Test Brightness Lib Demo
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private SeekBar sb_standard_imm;
    private SeekBar sb_standard_gra;

    private Button btn1,btn2;

    private BaseBrightnessImpl standardImm;
    private BaseBrightnessImpl standardGra;

    private boolean hasWriteSettingPermission = false;

    private static final int REQUEST_CODE = 0x100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ActivityCompat兼容库 不需特意判断版本号再权限检测
        if(ActivityCompat.checkSelfPermission(getApplication(), Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED){
            hasWriteSettingPermission = true;
        }else{
            hasWriteSettingPermission = false;
        }
        DefaultLogger.debug(null,"hasWriteSettingPermission:"+hasWriteSettingPermission);

        initView();
        initBrightness();

    }



    private void initView() {

        sb_standard_imm = findViewById(R.id.sb_standard_imm);
        sb_standard_gra  = findViewById(R.id.sb_standard_gra);

        sb_standard_imm.setOnSeekBarChangeListener(this);
        sb_standard_gra.setOnSeekBarChangeListener(this);


        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    private void initBrightness() {
        standardImm = BrightnessFactory.createBrightnessImpl(getApplication(), BrightnessPlatform.PLATFORM_STANDARD, IBrightnessChange.Strategy.IMMEDIATELY);
        standardGra = BrightnessFactory.createBrightnessImpl(getApplication(), BrightnessPlatform.PLATFORM_STANDARD, IBrightnessChange.Strategy.GRADIENT);

        standardImm.setChangeStatusListener(new CustomChangeListener() {

            @Override
            public void updatingBrightnessValue(int currentBrightness) {
                sb_standard_imm.setProgress(currentBrightness);
            }

        });


        standardGra.setChangeStatusListener(new CustomChangeListener() {


            @Override
            public void updatingBrightnessValue(int currentBrightness) {
                sb_standard_gra.setProgress(currentBrightness);
            }


        });
    }


    @Override
    public void onClick(View v) {
        if(!hasWriteSettingPermission){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_SETTINGS},REQUEST_CODE);
        }
        switch (v.getId()){
            case R.id.btn1:{
                standardImm.setBrightness(new Random().nextInt(255));
            }break;
            case R.id.btn2:{
                standardGra.setBrightness(new Random().nextInt(255));
            }break;
            default:{
                //do nothing
            }break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar == sb_standard_imm){
            standardImm.forceChangeBrightness(progress);
        }else if(seekBar == sb_standard_gra){
            standardGra.forceChangeBrightness(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
               hasWriteSettingPermission = true;
            }else{
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_SETTINGS)) {
                    new AlertDialog.Builder(this)
                            .setMessage("Need writing setting permissions")
                            .setPositiveButton("OK", (dialog1, which) ->
                                    ActivityCompat.requestPermissions(MainActivity.this,
                                            new String[]{Manifest.permission.WRITE_SETTINGS},
                                            REQUEST_CODE))
                            .setNegativeButton("Cancel", null)
                            .create()
                            .show();
                }
            }
        }

    }
}
