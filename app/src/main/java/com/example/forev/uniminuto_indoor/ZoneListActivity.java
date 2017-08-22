package com.example.forev.uniminuto_indoor;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;

public class ZoneListActivity extends Activity {

    private ListView zoneListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zone_list);

        final ArrayList<String> zoneList = (ArrayList<String>) getIntent().getSerializableExtra("zoneList");
        zoneListView = (ListView)findViewById(R.id.zoneListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, zoneList);
        zoneListView.setAdapter(adapter);

        // listView에서 목적지 클릭 시, MapActivity로 전환
        zoneListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ZoneListActivity.this, zoneList.get(position), Toast.LENGTH_LONG).show();
                Intent it = new Intent(getApplication(), MapActivity.class);
                it.putExtra("destination", zoneList.get(position));
                startActivity(it);
                ZoneListActivity.this.finish();
            }
        });

        //뒤로가기 버튼
        Button btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(),MapActivity.class));
                ZoneListActivity.this.finish();
            }
        });
    }
}
