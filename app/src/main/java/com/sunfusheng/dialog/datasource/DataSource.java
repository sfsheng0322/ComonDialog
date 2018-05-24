package com.sunfusheng.dialog.datasource;

import com.sunfusheng.dialog.PopupMenuActivity;
import com.sunfusheng.dialog.R;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class DataSource {

    public enum MainItemConfig {
        NULL(0, null),
        COMMON_DIALOG(R.string.common_dialog, null),
        BOTTOM_SHEET(R.string.bottom_sheet, null),
        POPUP_MENU(R.string.popup_menu, PopupMenuActivity.class);

        public int titleId;
        public Class<?> intentClass;

        MainItemConfig(int resId, Class<?> intentClass) {
            this.titleId = resId;
            this.intentClass = intentClass;
        }
    }

    public static MainItemConfig[][] mainItems = {
            {MainItemConfig.NULL, MainItemConfig.COMMON_DIALOG, MainItemConfig.BOTTOM_SHEET, MainItemConfig.POPUP_MENU}
    };
}
