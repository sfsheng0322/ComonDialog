package com.sunfusheng.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author sunfusheng on 2019/1/20.
 */
public class CommonPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_popup);

        setTitle(R.string.common_menu);

    }
}
