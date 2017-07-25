package com.example.forev.uniminuto_indoor;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by forev on 2017-07-25.
 */


public class LoadingActivity  extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() { //권한이 모두 허용된 후 실행.
                Toast.makeText(LoadingActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
                //3초동안 로딩화면이 뜨게하기.
                intent = new Intent(getApplicationContext(),MainActivity.class);
                Handler handler = new Handler(){
                    public void handleMessage(Message msg){
                        startActivity(intent); //로딩시스템
                        LoadingActivity.this.finish();
                    }
                }; //핸들러가 수행되었을때 일어나는일.

                ProgressBar progress = (ProgressBar) findViewById(R.id.loadingBar) ;
                //progress.setProgress(value) ;

                handler.sendEmptyMessageDelayed(0,3000); //3초후 발생하게하기
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) { //거부된 권한 목록을 제공.
                Toast.makeText(LoadingActivity.this, "권한이 거부되어 실행할 수 없습니다.\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                LoadingActivity.this.finishAffinity(); //종료
            }


        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("구글 로그인을 하기 위해서는 위치 접근 권한이 필요해요")
                .setDeniedMessage("왜 거부하셨어요...\n하지만 [설정] > [권한] 에서 권한을 허용할 수 있어요.")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();


    }
}