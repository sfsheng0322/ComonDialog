package com.sunfusheng.dialog;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.sunfusheng.dialog.adapter.MainGroupAdapter;
import com.sunfusheng.dialog.datasource.DataSource;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MainGroupAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mainAdapter = new MainGroupAdapter(this, DataSource.mainItems);
        recyclerView.setAdapter(mainAdapter);

        mainAdapter.setOnItemClickListener((adapter, data, groupPosition, childPosition) -> {
            if (null != data && null != data.intentClass) {
                startActivity(new Intent(this, data.intentClass));
            }
        });
    }

}
