package com.qichang.hfydemo.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qichang.hfydemo.R;
import com.qichang.hfydemo.bean.GwcBean;

import java.util.List;

/**
 * @author hfy
 * @description
 * @time 2020/4/29
 */
public class ClothAdapter extends BaseQuickAdapter<GwcBean.CartlistBean, BaseViewHolder> {
    private OnResfreshListener_cloth mOnResfreshListener;

    public ClothAdapter(@Nullable List<GwcBean.CartlistBean> data) {
        super(R.layout.item_cloth, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GwcBean.CartlistBean item) {
        helper.setText(R.id.tv_cloth_name, item.getProductName());
        helper.setText(R.id.tv_style, item.getColor() + ";" + item.getSize());
        helper.setText(R.id.tv_cloth_num, item.getCount() + "");
        helper.setText(R.id.tv_price, item.getPrice() + "");
        ImageView img_cloth_select=helper.getView(R.id.img_cloth_select);

        if (item.isSelect()) {
            img_cloth_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_selected));
        } else {
            img_cloth_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_unselected));
        }

        if (mOnResfreshListener != null) {
            boolean isSelect = false;
            if (!item.isSelect()) {
                isSelect = false;
            } else {
                isSelect = true;
            }
            mOnResfreshListener.onResfresh(isSelect);
        }

        //商品的选择按钮
        helper.getView(R.id.img_cloth_select).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.w("hfydemo","----"+item.isSelect());
                item.setSelect(!item.isSelect());
                Log.w("hfydemo","====="+item.isSelect());
                notifyDataSetChanged();
            }
        });
    }

    //实时监控全选按钮
    public interface OnResfreshListener_cloth {
        void onResfresh(boolean isSelect);
    }

    public void setResfreshListener_cloth(OnResfreshListener_cloth mOnResfreshListener) {
        this.mOnResfreshListener = mOnResfreshListener;
    }

}
