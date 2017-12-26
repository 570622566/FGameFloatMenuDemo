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
                .setBgColor(R.color.bgcolor)//背景颜色
                .addMenuItem(new MenuItem(R.mipmap.remind, "消息"))
                .addMenuItem(new MenuItem(R.mipmap.cart, "充值"))
                .addMenuItem(new MenuItem(R.mipmap.account, "我的"))
                .addMenuItem(new MenuItem(R.mipmap.home, "首页"))
                .setItemIconSize(18)
                .setMarginLogoLeft(3)
                .setMarginLogoRight(16)
                .setItemMarginRight(6)
                .setItemMarginLeft(6)
                .setItemMarginTop(0)
                .setItemMarginBottom(0)
                .setLogoRes(R.mipmap.ic_launcher_round)//logo资源
                .setDefPositionShow(FFloatMenu.SHOW_LEFT)//悬浮窗默认显示左边
                .setRotateLogo(true)//拖拽时悬浮窗是否旋转动画
                .setViewAlpha(0.7f)//半隐藏悬浮窗时透明度
                .setCenterInLogo(false)//拖拽点是否居中logo
                .setMillisInFuture(6)//半隐藏悬浮球倒计时
                .setHideLogoSize(20)//半隐藏logo靠边移动大小 dp
                .setOneShow(false)
                .build();
        fFloatMenu.setOnMenuClickListener(new FFloatMenu.OnMenuClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(MainActivity.this, "点击了第" + position + "项", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void dismiss() {
                Toast.makeText(MainActivity.this, "关闭菜单栏", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void open(int status) {
                Toast.makeText(MainActivity.this, "悬浮窗菜单在" + (status == 1 ? "左边" : "右边"), Toast.LENGTH_SHORT).show();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("----");
                startActivity(new Intent(MainActivity.this, SecActivity.class));

            }
        });
    }
}
