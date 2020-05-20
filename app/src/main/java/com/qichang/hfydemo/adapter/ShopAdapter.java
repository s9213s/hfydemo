package com.qichang.hfydemo.adapter;


import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.fing.uitl.ParseJsonUtil;
import com.qichang.hfydemo.R;
import com.qichang.hfydemo.adapter.itemclick.OnAllSelectClickListener;
import com.qichang.hfydemo.adapter.itemclick.OnClickAddCloseListenter_cloth;
import com.qichang.hfydemo.adapter.itemclick.OnClickListenterModel_cloth;
import com.qichang.hfydemo.adapter.itemclick.OnViewItemClickListener_shop;
import com.qichang.hfydemo.adapter.itemclick.onItemMoneyClickListener_shop;
import com.qichang.hfydemo.bean.GwcBean;
import java.util.List;


/**
 * @author hfy
 * @description
 * @time 2020/4/29
 */
public class ShopAdapter extends BaseQuickAdapter<GwcBean, BaseViewHolder> {
    public boolean isCheck = false;

    public ShopAdapter(@Nullable List<GwcBean> data) {
        super(R.layout.item_shop, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GwcBean item) {
        RecyclerView recyclerView = helper.getView(R.id.recycler);
        final CheckBox img_shop_select = helper.getView(R.id.img_shop_select);
        helper.setText(R.id.tv_shop_name, item.getShopName());
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        List<GwcBean.CartlistBean> mList = item.getCartlist();
        final ClothAdapter clothAdapter = new ClothAdapter(mList);
        recyclerView.setFocusableInTouchMode(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.requestFocus();
        recyclerView.setFocusable(false);
        recyclerView.setAdapter(clothAdapter);

        img_shop_select.setChecked(item.isShopSelect());
        img_shop_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(img_shop_select.isChecked(), v, helper.getAdapterPosition());
            }
        });
        //商品的选择
        clothAdapter.setOnClickListenterModel_cloth(new OnClickListenterModel_cloth() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                clothAdapter.getData().get(position).setSelect(isFlang);
                int length = clothAdapter.getData().size();
                //遍历商品列表，如果商品全是选中状态则店铺状态为选中
                    for (int i = 0; i < length; i++) {
                        if (clothAdapter.getData().get(i).isSelect()) {//true,true,true
                            isCheck = true;
                        } else {
                            isCheck = false;
                            break;
                        }
                    }
                    item.setShopSelect(isCheck);
                    onAllSelectClickListener.OnSelectClick(clothAdapter.getData());
                    onItemMoneyClickListener.onItemClick(helper.getAdapterPosition());
                    notifyDataSetChanged();
            }
        });

        clothAdapter.setOnClickAddCloseListenter_cloth(new OnClickAddCloseListenter_cloth() {
            @Override
            public void onItemClick(View view, int index, int position, int num) {
                if (index == 1) {
                    if (num > 1) {
                        item.getCartlist().get(position).setCount(num - 1);
                        notifyDataSetChanged();
                    }
                } else {
                    item.getCartlist().get(position).setCount(num + 1);
                    notifyDataSetChanged();
                }
                onItemMoneyClickListener.onItemClick(helper.getAdapterPosition());
            }
        });

        //商品的侧滑删除
        clothAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                adapter.remove(position);
                if (adapter.getData().size() == 0) {
                    remove(helper.getAdapterPosition());
                }
                notifyDataSetChanged();
                onItemMoneyClickListener.onItemClick(12580);
            }
        });

    }

    // 店铺CheckBox全选的方法
    private OnViewItemClickListener_shop mOnItemClickListener = null;

    public void setOnItemClickListener_shop(OnViewItemClickListener_shop listener) {
        this.mOnItemClickListener = listener;
    }

    // 计算价钱
    private onItemMoneyClickListener_shop onItemMoneyClickListener = null;

    public void setOnItemMoneyClickListener_shop(onItemMoneyClickListener_shop listener) {
        this.onItemMoneyClickListener = listener;
    }

    //底部选择按钮的监听
    private OnAllSelectClickListener onAllSelectClickListener = null;

    public void setOnAllSelectClickListener(OnAllSelectClickListener onAllSelectClickListener) {
        this.onAllSelectClickListener = onAllSelectClickListener;
    }
}
