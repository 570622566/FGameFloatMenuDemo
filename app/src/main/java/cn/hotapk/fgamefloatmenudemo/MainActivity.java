package cn.hotapk.fgamefloatmenudemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.hotapk.fgamefloatmenu.FFloatMenu;
import cn.hotapk.fgamefloatmenu.FFloatMenuBuilder;
import cn.hotapk.fgamefloatmenu.FFloatMenuView;
import cn.hotapk.fgamefloatmenu.bean.MenuItem;

public class MainActivity extends AppCompatActivity {


    private FFloatMenu fFloatMenu;
    private TextView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        fFloatMenu = new FFloatMenuBuilder(this)
                .setBgColor(R.color.colorPrimary)
                .addMenuItem(new MenuItem(R.drawable.ic_launcher, "我的"))
                .addMenuItem(new MenuItem(R.drawable.icon, "首页"))
                .setLogoRes(R.drawable.icon)
                .setOnItemClickListener(new FFloatMenuView.OnItemClickListener() {
                    @Override
                    public void itemClick(int position) {
                        Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("----");
                startActivity(new Intent(MainActivity.this, SecActivity.class));

            }
        });
    }
}
