package com.example.forev.uniminuto_indoor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by forev on 2017-08-10.
 */

public class LoginActivity extends AppCompatActivity {
    private String ID = "Uniminuto"; //El nombre de usuario
    private String PASSWORD = "1234"; //La contraseña
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userId = ((EditText) (findViewById(R.id.txt_id))).getText().toString();
                String password = ((EditText) (findViewById(R.id.txt_pw))).getText().toString();
                if(userId.equals(ID) && password.equals(PASSWORD)){
                    startActivity(new Intent(getApplication(), HomeActivity.class));
                    LoginActivity.this.finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Error de Login.", Toast.LENGTH_SHORT).show();

                }
            }
        });
        //end


    }



}
