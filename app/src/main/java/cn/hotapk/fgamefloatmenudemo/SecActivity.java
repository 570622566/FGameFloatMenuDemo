package cn.hotapk.fgamefloatmenudemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import cn.hotapk.fgamefloatmenu.FFloatMenu;
import cn.hotapk.fgamefloatmenu.FFloatMenuBuilder;
import cn.hotapk.fgamefloatmenu.FFloatMenuView;
import cn.hotapk.fgamefloatmenu.bean.MenuItem;

public class SecActivity extends AppCompatActivity {
    FFloatMenu fFloatMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
        fFloatMenu = new FFloatMenuBuilder(this)
                .setBgColor(R.color.colorPrimary)
                .addMenuItem(new MenuItem(R.drawable.ic_launcher, "我的"))
                .addMenuItem(new MenuItem(R.drawable.icon, "首页"))
                .setLogoRes(R.drawable.icon)
                .build();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void hideFloat(View view) {
        fFloatMenu.hideFloat();
    }

    public void showFloat(View view) {
        fFloatMenu.showFloat();
    }
}
