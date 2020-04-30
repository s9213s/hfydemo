package com.qichang.hfydemo.base;

import android.view.View;


import com.fing.base.MBaseFragment;
import com.qichang.hfydemo.util.SpUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends MBaseFragment {
    private Unbinder unbinder;
    public SpUtil spUtil;

    @Override
    public void init() {
        spUtil = new SpUtil(mContext);
    }

    @Override
    public void bindButterKnife(View view) {
        unbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void unBindButterKnife() {
        unbinder.unbind();
    }

}
