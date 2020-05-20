package com.qichang.hfydemo.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qichang.hfydemo.R;
import com.qichang.hfydemo.adapter.itemclick.OnClickAddCloseListenter_cloth;
import com.qichang.hfydemo.adapter.itemclick.OnClickListenterModel_cloth;
import com.qichang.hfydemo.adapter.itemclick.OnClothChildDelete;
import com.qichang.hfydemo.bean.GwcBean;
import com.qichang.hfydemo.demo.itemclick.OnClickAddCloseListenter;
import com.qichang.hfydemo.demo.itemclick.OnClickListenterModel;

import java.util.List;

/**
 * @author hfy
 * @description
 * @time 2020/4/29
 */
public class ClothAdapter extends BaseQuickAdapter<GwcBean.CartlistBean, BaseViewHolder> {


    public ClothAdapter(@Nullable List<GwcBean.CartlistBean> data) {
        super(R.layout.item_cloth, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GwcBean.CartlistBean item) {
        helper.setText(R.id.tv_cloth_name, item.getProductName());
        helper.setText(R.id.tv_style, item.getColor() + ";" + item.getSize());
        helper.setText(R.id.tv_cloth_num, item.getCount() + "");
        helper.setText(R.id.tv_price, item.getPrice() + "");
        final Button delete = helper.getView(R.id.Delete);

        ImageView img_cloth_minus, img_cloth_add;
        img_cloth_minus = helper.getView(R.id.img_cloth_minus);
        img_cloth_add = helper.getView(R.id.img_cloth_add);
        final CheckBox img_cloth_select = helper.getView(R.id.img_cloth_select);

        img_cloth_select.setChecked(item.isSelect());

        //商品的选择按钮
        img_cloth_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListenterModel_cloth.onItemClick(img_cloth_select.isChecked(), v, helper.getAdapterPosition());
            }
        });


        img_cloth_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddCloseListenter_cloth.onItemClick(v, 1, helper.getAdapterPosition(), item.getCount());
            }
        });
        img_cloth_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAddCloseListenter_cloth.onItemClick(v, 2, helper.getAdapterPosition(), item.getCount());
            }
        });

        //删除按钮
        helper.addOnClickListener(R.id.Delete);

    }

    //  商品的选择按钮,CheckBox2接口的方法
    private OnClickListenterModel_cloth onClickListenterModel_cloth = null;

    public void setOnClickListenterModel_cloth(OnClickListenterModel_cloth listener) {
        this.onClickListenterModel_cloth = listener;
    }

    // 数量接口的方法
    private OnClickAddCloseListenter_cloth onClickAddCloseListenter_cloth = null;

    public void setOnClickAddCloseListenter_cloth(OnClickAddCloseListenter_cloth listener) {
        this.onClickAddCloseListenter_cloth = listener;
    }
}
