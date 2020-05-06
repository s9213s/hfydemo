package com.qichang.hfydemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fing.http.MCallBack;
import com.fing.http.Result;
import com.fing.uitl.MToast;
import com.fing.uitl.ParseJsonUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.qichang.hfydemo.adapter.ShopAdapter;
import com.qichang.hfydemo.adapter.itemclick.OnAllSelectClickListener;
import com.qichang.hfydemo.adapter.itemclick.OnViewItemClickListener_shop;
import com.qichang.hfydemo.adapter.itemclick.onItemMoneyClickListener_shop;
import com.qichang.hfydemo.base.BaseActivity;
import com.qichang.hfydemo.bean.GwcBean;
import com.qichang.hfydemo.bean.ShopCartBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hfy
 * @description
 * @time 2020/4/29
 */
public class GwcActivity extends BaseActivity {
    @BindView(R.id.tv_nub)
    TextView tvNub;
    @BindView(R.id.tv_bianji)
    TextView tvBianji;
    @BindView(R.id.ll_Top)
    RelativeLayout llTop;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    //    @BindView(R.id.tv_all_select)
//    TextView tvAllSelect;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_all_num)
    TextView tvAllNum;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_all_select)
    CheckBox tv_all_select;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    private ShopAdapter shopAdapter;

    double price;
    int num;
    private boolean isCheck;

    @Override
    public int bindLayout() {
        return R.layout.activity_shop;
    }

    @Override
    public void initView() {
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);
        recycler.setFocusable(false);
        shopAdapter = new ShopAdapter(null);
        recycler.setAdapter(shopAdapter);

        shopAdapter.setOnAllSelectClickListener(new OnAllSelectClickListener() {
            @Override
            public void OnSelectClick(List<GwcBean.CartlistBean> list) {
                for (int i = 0; i < list.size(); i++) {
                    Log.w("hfydemo", "点击商品选择========" + list.get(i).isSelect());
                    if (list.get(i).isSelect()) {//true,true,true
                        isCheck = true;
                    } else {
                        isCheck = false;
                        break;
                    }
                }
                tv_all_select.setChecked(isCheck);
            }
        });
        /**
         * 全选
         */
        shopAdapter.setOnItemClickListener_shop(new OnViewItemClickListener_shop() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                shopAdapter.getData().get(position).setShopSelect(isFlang);
                int length = shopAdapter.getData().get(position).getCartlist().size();
                for (int i = 0; i < length; i++) {
                    shopAdapter.getData().get(position).getCartlist().get(i).setSelect(isFlang);
                }
                shopAdapter.notifyDataSetChanged();

                for (int i = 0; i < shopAdapter.getData().size(); i++) {
                    Log.w("hfydemo", "店铺选择========" + shopAdapter.getData().get(i).isShopSelect());
                    if (shopAdapter.getData().get(i).isShopSelect()) {//true,true,true
                        isCheck = true;
                    } else {
                        isCheck = false;
                        break;
                    }
                }
                tv_all_select.setChecked(isCheck);

                showCommodityCalculation();
            }
        });
        /**
         * 计算价钱
         */
        shopAdapter.setOnItemMoneyClickListener_shop(new onItemMoneyClickListener_shop() {
            @Override
            public void onItemClick(View view, int position) {
                showCommodityCalculation();
            }
        });

    }

    @Override
    public void loadData() {
        OkGo.<Result<List<GwcBean>>>post("http://www.mocky.io/v2/5ea7d7aa2f0000d8a0c4f04e")
                .execute(new MCallBack<Result<List<GwcBean>>>(this) {
                    @Override
                    public void onSuccess(Response<Result<List<GwcBean>>> response) {
                        if (response.body().isSuccess()) {
                            shopAdapter.setNewData(response.body().data);
                        } else {
                            MToast.showToast(GwcActivity.this, response.body().msg);
                        }
                    }
                });
    }

    /***
     * 计算商品的数量和价格
     */
    private void showCommodityCalculation() {
        price = 0;
        num = 0;
        for (int i = 0; i < shopAdapter.getData().size(); i++) {
            for (int j = 0; j < shopAdapter.getData().get(i).getCartlist().size(); j++) {
                if (shopAdapter.getData().get(i).getCartlist().get(j).isSelect()) {
                    price += Double.valueOf((shopAdapter.getData().get(i).getCartlist().get(j).getCount() * shopAdapter.getData().get(i).getCartlist().get(j).getPrice()));
                    num++;
                } else {
                    tv_all_select.setChecked(false);
                }
            }
        }
        if (price == 0.0) {
            tvAllNum.setText("共0件商品");
            tvAllPrice.setText("总价：¥ 0.0");
            return;
        }
        try {
            String money = String.valueOf(price);
            tvAllNum.setText("共" + num + "件商品");
            if (money.substring(money.indexOf("."), money.length()).length() > 2) {
                tvAllPrice.setText("总价：¥ " + money.substring(0, (money.indexOf(".") + 3)));
                return;
            }
            tvAllPrice.setText("总价：¥ " + money.substring(0, (money.indexOf(".") + 2)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.tv_bianji, R.id.tv_submit, R.id.tv_all_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bianji:
                break;
            case R.id.tv_submit:
                break;
            case R.id.tv_all_select:
                if (tv_all_select.isChecked()) {
                    int length = shopAdapter.getData().size();
                    for (int i = 0; i < length; i++) {
                        shopAdapter.getData().get(i).setShopSelect(true);
                        int lengthn = shopAdapter.getData().get(i).getCartlist().size();
                        for (int j = 0; j < lengthn; j++) {
                            shopAdapter.getData().get(i).getCartlist().get(j).setSelect(true);
                        }
                    }

                } else {
                    int length = shopAdapter.getData().size();
                    for (int i = 0; i < length; i++) {
                        shopAdapter.getData().get(i).setShopSelect(false);
                        int lengthn = shopAdapter.getData().get(i).getCartlist().size();
                        for (int j = 0; j < lengthn; j++) {
                            shopAdapter.getData().get(i).getCartlist().get(j).setSelect(false);
                        }
                    }
                }
                shopAdapter.notifyDataSetChanged();
                showCommodityCalculation();
                break;
        }
    }
}
