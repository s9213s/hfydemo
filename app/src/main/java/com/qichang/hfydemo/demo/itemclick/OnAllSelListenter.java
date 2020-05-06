package com.qichang.hfydemo.demo.itemclick;

import com.qichang.hfydemo.demo.entity.CartInfo;

import java.util.List;

/**
 * @author hfy
 * @description
 * @time 2020/4/30
 */
public interface OnAllSelListenter {
    void onItemClick(List<CartInfo.DataBean.ItemsBean> list);
}
