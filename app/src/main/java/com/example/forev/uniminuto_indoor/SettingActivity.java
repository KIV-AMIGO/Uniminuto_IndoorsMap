package com.example.forev.uniminuto_indoor;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by forev on 2016-11-05.
 */

public class SettingActivity  extends AppCompatActivity {
    private Button btn_credit;
    private Button btn_home;
    private Button btn_costList;
    private Button btn_myPage;
    private Button btn_back;
    private Button btn_gpsSetting;
    private Button btn_vibraionOn;
    private Button btn_vibrationOff;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    //GPS
    private LocationManager locationManager;
    //Audio Mode
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //GPS Manager
        locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

        //AudioMode Manager
        audioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);

        //back
        btn_back=(Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),HomeActivity.class));
                SettingActivity.this.finish();
            }
        });
        //크레딧 버튼
        btn_credit = (Button)findViewById(R.id.btn_credit);
        btn_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),CreditActivity.class));
               SettingActivity.this.finish();
            }
        });

        //GPS Setting
        btn_gpsSetting = (Button)findViewById(R.id.btn_gpsSetting);
        btn_gpsSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GPS가 켜져있는지 체크
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    //GPS 설정화면으로 이동
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    startActivity(intent);
                } else {
                    //GPS 켜져있으면 메시지 띄움
                    Toast.makeText(SettingActivity.this, "이미 GPS 동작 중입니다.", Toast.LENGTH_SHORT).show();
                }
            } //end of onClick
        });

        //Vibration Setting
        btn_vibraionOn = (Button)findViewById(R.id.btn_vibrationOn);
        btn_vibraionOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            }
        });
        btn_vibrationOff = (Button)findViewById(R.id.btn_vibrationOff);
        btn_vibrationOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }
        });

        //하단버튼
        btn_home = (Button)findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),HomeActivity.class));
                SettingActivity.this.finish();
            }
        });
        btn_costList = (Button)findViewById(R.id.btn_map);
        btn_costList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),MapActivity.class));
                SettingActivity.this.finish();
            }
        });
        Button btn_credit = (Button)findViewById(R.id.btn_credit);
        btn_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplication(),CreditActivity.class));
                SettingActivity.this.finish();
            }
        });
        //하단버튼 끝
    }

    //back버튼 2번눌렀을때 종료됨
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime) //2초안에 한번더 눌렀을경우.
        {
            super.onBackPressed();
        }
        else //2초안에 한번만 눌렀을 경우
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "Sí apacha de nuevo se concluirá la aplicación.", Toast.LENGTH_SHORT).show(); //꺼진다는 텍스트를 띄워준다.
        }
    }
}
