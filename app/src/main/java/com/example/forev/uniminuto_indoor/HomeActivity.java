package com.example.forev.uniminuto_indoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by forev on 2017-07-25.
 */

public class HomeActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //하단버튼

        Button btn_home = (Button) findViewById(R.id.btn_home);
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),HomeActivity.class));
                HomeActivity.this.finish();
            }
        });
        Button btn_map = (Button) findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),MainActivity.class));
                HomeActivity.this.finish();
            }
        });
        /**
        btn_myPage = (Button)findViewById(R.id.btn_myPage);
        btn_myPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplication(),MypageActivity.class));
                CreditActivity.this.finish();
            }
        });
        btn_setting = (Button)findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),SettingActivity.class));
                CreditActivity.this.finish();
            }
        });
        *
         */
        //하단버튼 끝
    }
}
