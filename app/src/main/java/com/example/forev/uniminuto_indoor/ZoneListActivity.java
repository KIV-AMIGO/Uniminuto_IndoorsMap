package com.example.forev.uniminuto_indoor;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.customlbs.library.IndoorsFactory;
import com.customlbs.library.callbacks.ZoneCallback;
import com.customlbs.library.model.Zone;
import com.customlbs.surface.library.IndoorsSurfaceFragment;

import java.util.ArrayList;
import java.util.List;

public class ZoneListActivity extends Activity {

    private ListView zoneListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zone_list);

        ArrayList<String> zoneList = (ArrayList<String>) getIntent().getSerializableExtra("zoneList");
        zoneListView = (ListView)findViewById(R.id.zoneListView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, zoneList);
        zoneListView.setAdapter(adapter);

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
