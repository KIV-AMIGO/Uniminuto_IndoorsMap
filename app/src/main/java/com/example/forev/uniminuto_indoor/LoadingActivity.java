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
            public void onPermissionGranted() {

                intent = new Intent(getApplicationContext(),LoginActivity.class);
                Handler handler = new Handler(){
                    public void handleMessage(Message msg){
                        startActivity(intent);
                        LoadingActivity.this.finish();
                    }
                };

                ProgressBar progress = (ProgressBar) findViewById(R.id.loadingBar) ;
                //progress.setProgress(value) ;

                handler.sendEmptyMessageDelayed(0,3000);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(LoadingActivity.this, "No se puede realizar porque la permisión ha sido rechazada.\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                LoadingActivity.this.finishAffinity(); //종료
            }


        };
        new TedPermission(this)
                .setPermissionListener(permissionlistener)
                .setRationaleMessage("Para utilizar la aplicación se necesita la permisión de acercaminto de ubicación")
                .setDeniedMessage("No se puede utilizar la aplicación si la niega.\nPero [Ajuste] > [Permisión] aquí se puede encender la permisión")
                .setPermissions(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();


    }
}