package com.qichang.hfydemo.base;


import com.fing.base.MApp;
import com.fing.config.BaseModule;
import com.fing.config.BaseModuleConfig;
import com.qichang.hfydemo.R;

public class App extends MApp {

    @Override
    protected void init() {
        BaseModule.initialize(BaseModuleConfig.newBuilder()
                .setLoadingViewStyle("LineSpinFadeLoaderIndicator")
                .setLoadingViewColor(R.color.colorAccent).build());
    }

}
