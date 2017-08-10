package com.example.forev.uniminuto_indoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by forev on 2017-07-25.
 */

public class HomeActivity  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //하단버튼
        ImageView img_map = (ImageView)findViewById(R.id.img_map);
        img_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),MapActivity.class));
                HomeActivity.this.finish();
            }
        });

        Button btn_map = (Button) findViewById(R.id.btn_map);
        btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),MapActivity.class));
                HomeActivity.this.finish();
            }
        });
        Button btn_setting = (Button) findViewById(R.id.btn_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),SettingActivity.class));
                HomeActivity.this.finish();
            }
        });

        Button btn_credit = (Button) findViewById(R.id.btn_credit);
        btn_credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplication(),CreditActivity.class));
                HomeActivity.this.finish();
            }
        });
        //하단버튼 끝
    }
}
