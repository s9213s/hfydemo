package com.qichang.hfydemo.adapter;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fing.uitl.ParseJsonUtil;
import com.qichang.hfydemo.R;
import com.qichang.hfydemo.bean.GwcBean;
import com.qichang.hfydemo.bean.ShopCartBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hfy
 * @description
 * @time 2020/4/29
 */
public class ShopAdapter extends BaseQuickAdapter<GwcBean, BaseViewHolder> {
    boolean mSelect;

    private OnResfreshListener mOnResfreshListener;

    public ShopAdapter(@Nullable List<GwcBean> data) {
        super(R.layout.item_shop, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GwcBean item) {
        RecyclerView recyclerView = helper.getView(R.id.recycler);
        final ImageView img_shop_select = helper.getView(R.id.img_shop_select);
        helper.setText(R.id.tv_shop_name, item.getShopName());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        List<GwcBean.CartlistBean> mList = item.getCartlist();
        final ClothAdapter clothAdapter = new ClothAdapter(mList);
        recyclerView.setFocusableInTouchMode(false);
        recyclerView.requestFocus();
        recyclerView.setFocusable(false);
        recyclerView.setAdapter(clothAdapter);
        if (item.isShopSelect()) {
            img_shop_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_selected));
        } else {
            img_shop_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_unselected));
        }

        if (mOnResfreshListener != null) {
            boolean isSelect = false;
            if (!item.isShopSelect()) {
                isSelect = false;
            } else {
                isSelect = true;
            }
            mOnResfreshListener.onResfresh(isSelect,helper.getAdapterPosition());
        }
        clothAdapter.setResfreshListener_cloth(new ClothAdapter.OnResfreshListener_cloth() {
            @Override
            public void onResfresh(boolean isSelect) {
                List<String> isSelectStr = new ArrayList<>();
                for (int i = 0; i < clothAdapter.getData().size(); i++) {
                    if (!clothAdapter.getData().get(i).isSelect()) {
                        isSelectStr.add("没选");
                    } else {
                        isSelectStr.add("选");
                    }
                }
                Log.w("hfydemo", "isSelectStr:" + ParseJsonUtil.toJson(isSelectStr));
                if (isSelectStr.contains("没选")) {
                    item.setShopSelect(false);
                    img_shop_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_unselected));
                } else {
                    item.setShopSelect(true);
                    img_shop_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_selected));
                }
            }
        });
        img_shop_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("hfydemo", "----" + item.isShopSelect());
                item.setShopSelect(!item.isShopSelect());
                Log.w("hfydemo", "=====" + item.isShopSelect());

                for (int i = 0; i < clothAdapter.getData().size(); i++) {
                    if (item.isShopSelect()) {
                        clothAdapter.getData().get(i).setSelect(true);
                    } else {
                        clothAdapter.getData().get(i).setSelect(false);
                    }

                }
                notifyDataSetChanged();
            }
        });

    }

    //实时监控全选按钮
    public interface OnResfreshListener {
        void onResfresh(boolean b,int po);
    }

    public void setResfreshListener(OnResfreshListener mOnResfreshListener) {
        this.mOnResfreshListener = mOnResfreshListener;
    }

}
