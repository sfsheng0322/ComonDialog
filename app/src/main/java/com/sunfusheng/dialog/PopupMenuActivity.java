package com.sunfusheng.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sunfusheng.dialog.popupmenu.PopupMenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunfusheng on 2018/5/24.
 */
public class PopupMenuActivity extends AppCompatActivity {

    private TextView textView;
    private List<PopupMenuItem> items = new ArrayList<>();

    private List<PopupMenuItem> getItems() {
        List<PopupMenuItem> items = new ArrayList<>();
        items.add(new PopupMenuItem("转发", R.mipmap.ic_popup_menu_share));
        items.add(new PopupMenuItem("回复", R.mipmap.ic_popup_menu_reply));
        items.add(new PopupMenuItem("复制", R.mipmap.ic_popup_menu_copy));
        items.add(new PopupMenuItem("收藏", R.mipmap.ic_popup_menu_favorite));
        items.add(new PopupMenuItem("扬声器播放", R.mipmap.ic_popup_menu_speaker));
        items.add(new PopupMenuItem("听筒播放", R.mipmap.ic_popup_menu_earphone));
        items.add(new PopupMenuItem("删除", R.mipmap.ic_popup_menu_delete));
        items.add(new PopupMenuItem("静音播放", R.mipmap.ic_popup_menu_mute));
        items.add(new PopupMenuItem("撤回", R.mipmap.ic_popup_menu_undo));
        items.add(new PopupMenuItem("强制撤回", R.mipmap.ic_popup_menu_force_undo));
        items.add(new PopupMenuItem("多选", R.mipmap.ic_popup_menu_multi_select));
        return items;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_menu);

        initData();
        initView();
    }

    private void initData() {
        items = getItems();
    }

    private void initView() {
        setTitle(R.string.popup_menu);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(v -> {
            showPopupWindow(textView, items);
        });
    }

    private void showPopupWindow(View anchorView, List<PopupMenuItem> items) {
        Context context = anchorView.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_popup_menu, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        setItems(recyclerView, items);

        PopupWindow popupWindow = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(anchorView, Gravity.CENTER, -0, -200);
    }

    private void setItems(RecyclerView recyclerView, List<PopupMenuItem> items) {
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 4));
        recyclerView.setAdapter(new PopupMenuAdapter(recyclerView.getContext(), items));
    }

    static class PopupMenuAdapter extends RecyclerView.Adapter<PopupMenuAdapter.ViewHolder> {

        private LayoutInflater layoutInflater;
        private List<PopupMenuItem> items;

        public PopupMenuAdapter(Context context, List<PopupMenuItem> items) {
            this.layoutInflater = LayoutInflater.from(context);
            this.items = items;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(layoutInflater.inflate(R.layout.item_popup_menu, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            PopupMenuItem item = items.get(position);

            holder.vTitle.setText(item.title);
            holder.vIcon.setImageResource(item.icon);
        }

        @Override
        public int getItemCount() {
            return items == null ? 0 : items.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView vTitle;
            ImageView vIcon;

            public ViewHolder(View itemView) {
                super(itemView);
                vTitle = itemView.findViewById(R.id.title);
                vIcon = itemView.findViewById(R.id.icon);
            }
        }
    }

}
