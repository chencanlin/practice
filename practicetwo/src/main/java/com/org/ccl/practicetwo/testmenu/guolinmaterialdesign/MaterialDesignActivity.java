package com.org.ccl.practicetwo.testmenu.guolinmaterialdesign;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.org.ccl.practicetwo.R;
import com.org.ccl.practicetwo.testmenu.guolinmaterialdesign.adapter.HeroAdapter;
import com.org.ccl.practicetwo.testmenu.guolinmaterialdesign.bean.Hero;

/**
 * Created by ccl on 2017/11/1.
 */

public class MaterialDesignActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView mRecyclerView;

    private SparseArrayCompat<Hero> heroArray = new SparseArrayCompat<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private HeroAdapter mHeroAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guolin_material_design_main);
        init();
    }

    private void init() {
        initData();
        initView();
    }

    private void initData() {
        heroArray.put(0, new Hero(R.mipmap.bh, "赏金猎人"));
        heroArray.put(1, new Hero(R.mipmap.dk, "龙骑士"));
        heroArray.put(2, new Hero(R.mipmap.jugg, "剑圣"));
        heroArray.put(3, new Hero(R.mipmap.yaphet, "影魔"));
        heroArray.put(4, new Hero(R.mipmap.airenjujishou, "矮人狙击手"));
        heroArray.put(5, new Hero(R.mipmap.anyemowang, "暗夜魔王"));
        heroArray.put(6, new Hero(R.mipmap.chuanzhang, "船长"));
        heroArray.put(7, new Hero(R.mipmap.diyulingzhu, "地狱领主"));
        heroArray.put(8, new Hero(R.mipmap.fengbaozhiling, "风暴之灵"));
        heroArray.put(9, new Hero(R.mipmap.handishenniu, "撼地神牛"));
        heroArray.put(10, new Hero(R.mipmap.heianyouxia, "黑暗游侠"));
        heroArray.put(11, new Hero(R.mipmap.kaer, "卡尔"));
        heroArray.put(12, new Hero(R.mipmap.lanmao, "蓝猫"));
        heroArray.put(13, new Hero(R.mipmap.linghunshouwei, "灵魂守卫"));
        heroArray.put(14, new Hero(R.mipmap.liulangjianke, "流浪剑客"));
        heroArray.put(15, new Hero(R.mipmap.shefanvyao, "蛇发女妖"));
        heroArray.put(16, new Hero(R.mipmap.shouwang, "兽王"));
        heroArray.put(17, new Hero(R.mipmap.shuijingshinv, "水晶室女"));
        heroArray.put(18, new Hero(R.mipmap.xiongmaojiuxian, "熊猫酒仙"));
        heroArray.put(19, new Hero(R.mipmap.baihu, "白虎"));
    }

    private void initView() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.drawerlayout);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.mipmap.icon_menu);
        }

        mNavigationView = findViewById(R.id.navigationview);
        mNavigationView.setCheckedItem(R.id.nav_call);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    default:
                        mDrawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });

        mFloatingActionButton = findViewById(R.id.fab);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(mFloatingActionButton, "Data deleted", Snackbar.LENGTH_LONG)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MaterialDesignActivity.this, "Data restored", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });


        mRecyclerView = findViewById(R.id.rv);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(this, 2);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mHeroAdapter = new HeroAdapter(this, heroArray);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mHeroAdapter);
        mHeroAdapter.setOnItemClickListener(new HeroAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view) {
                Hero hero = heroArray.get((Integer) view.getTag());
                startHeroDetailActivity(hero);
            }
        });

        mSwipeRefreshLayout = findViewById(R.id.srl);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mHeroAdapter.notifyDataSetChanged();
                                    mSwipeRefreshLayout.setRefreshing(false);
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

    }

    private void startHeroDetailActivity(Hero hero) {
        Intent intent = new Intent(this, HeroDetailActivity.class);
        intent.putExtra(HeroDetailActivity.EXTRA_KEY_HERO_NAME, hero.getName());
        intent.putExtra(HeroDetailActivity.EXTRA_KEY_HERO_IMAGE_ID, hero.getImageID());
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.backup:
                Toast.makeText(this, "backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.more:
                Toast.makeText(this, "more", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
