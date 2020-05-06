package com.qichang.hfydemo.adapter.itemclick;

import android.view.View;

/**
 * @author hfy
 * @description 添加和减少 接口回调
 * @time 2020/5/6
 */
public interface OnClickAddCloseListenter_cloth {
    /**
     * @param view
     * @param index    1是减少 2增加
     * @param position
     * @param num
     */
    void onItemClick(View view, int index, int position, int num);
}
