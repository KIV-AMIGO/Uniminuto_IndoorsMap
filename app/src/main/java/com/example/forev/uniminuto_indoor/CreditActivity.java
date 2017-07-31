package com.example.forev.uniminuto_indoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by forev on 2017-07-28.
 */

public class CreditActivity extends AppCompatActivity {
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    Button btn_back;
    Button btn_Map;
    Button btn_home;
    Button btn_myPage;
    Button btn_setting;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit);

        btn_back=(Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),SettingActivity.class));
                CreditActivity.this.finish();
            }
        });


        //하단버튼
        btn_home = (Button)findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),HomeActivity.class));
                CreditActivity.this.finish();
            }
        });
        btn_Map = (Button)findViewById(R.id.btn_map);
        btn_Map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),MapActivity.class));
                CreditActivity.this.finish();
            }
        });
        //btn_myPage = (Button)findViewById(R.id.btn_myPage);
        //btn_myPage.setOnClickListener(new View.OnClickListener() {
         //   @Override
        //    public void onClick(View v) {

          //      startActivity(new Intent(getApplication(),MypageActivity.class));
         //       CreditActivity.this.finish();
        //    }
        //});
        btn_setting = (Button)findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),SettingActivity.class));
                CreditActivity.this.finish();
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
            Toast.makeText(getApplicationContext(), "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show(); //꺼진다는 텍스트를 띄워준다.
        }
    }
}
