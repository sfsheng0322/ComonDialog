package com.sunfusheng.dialog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private String[][] items = {
            {"第一组", "1", "2", "3"}, // 0~3
            {"第二组", "1", "2"}, // 4~6
            {"第三组", "1", "2", "3", "4", "5"}, // 7~12
            {"第四组", "1", "2", "3", "4"}, // 13~17
            {"第五组", "1"} // 18~19
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<List<String>> lists = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            List<String> list = new ArrayList<>();
            list.addAll(Arrays.asList(items[i]));
            lists.add(list);
        }

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CommonGroupAdapter adapter = new CommonGroupAdapter(this, lists);
        recyclerView.setAdapter(adapter);
        adapter.setItems(lists);
    }

}
