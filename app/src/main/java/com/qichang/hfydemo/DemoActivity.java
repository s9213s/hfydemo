package com.qichang.hfydemo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.qichang.hfydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hfy
 * @description
 * @time 2020/5/6
 */
public class DemoActivity extends BaseActivity {
    @BindView(R.id.recycler2recycler)
    TextView recycler2recycler;
    @BindView(R.id.recycler2listview)
    TextView recycler2listview;
    @BindView(R.id.recycler1)
    TextView recycler1;

    @Override
    public int bindLayout() {
        return R.layout.activity_demo;
    }

    @Override
    public void initView() {

    }

    @Override
    public void loadData() {

    }


    @OnClick({R.id.recycler2recycler, R.id.recycler2listview, R.id.recycler1})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.recycler2recycler:
                startActivity(new Intent(DemoActivity.this, GwcActivity.class));
                break;
            case R.id.recycler2listview:
                startActivity(new Intent(DemoActivity.this, MainActivity.class));
                break;
            case R.id.recycler1:
                startActivity(new Intent(DemoActivity.this, ShopActivity.class));
                break;
        }
    }
}
