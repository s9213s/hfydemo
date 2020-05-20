package com.qichang.hfydemo;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fing.http.MCallBack;
import com.fing.http.Result;
import com.fing.uitl.MToast;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.qichang.hfydemo.adapter.ShopAdapter;
import com.qichang.hfydemo.adapter.itemclick.OnAllSelectClickListener;
import com.qichang.hfydemo.adapter.itemclick.OnViewItemClickListener_shop;
import com.qichang.hfydemo.adapter.itemclick.onItemMoneyClickListener_shop;
import com.qichang.hfydemo.base.BaseActivity;
import com.qichang.hfydemo.bean.GwcBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author hfy
 * @description
 * @time 2020/4/29
 */
public class GwcActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.tv_nub)
    TextView tvNub;
    @BindView(R.id.tv_bianji)
    TextView tvBianji;
    @BindView(R.id.ll_Top)
    RelativeLayout llTop;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.pay_delete)
    Button payDelete;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_all_num)
    TextView tvAllNum;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_all_select)
    CheckBox tv_all_select;
    @BindView(R.id.ll_pay)
    RelativeLayout llPay;
    @BindView(R.id.pay_price)
    LinearLayout payPrice;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ShopAdapter shopAdapter;

    double price;//每次用都已经先进行初始化
    int num;//每次用都已经先进行初始化
    private boolean isCheck;
    int shopNumber;////每次用都已经先进行初始化
    private boolean flag = false;

    @Override
    public int bindLayout() {
        return R.layout.activity_shop;
    }

    @Override
    public void initView() {
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));
        mSwipeRefreshLayout.setOnRefreshListener(this);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);
        recycler.setFocusable(false);
        shopAdapter = new ShopAdapter(null);
        recycler.setAdapter(shopAdapter);

        /**
         * 商品勾选时监听底部的全选按钮
         */
        shopAdapter.setOnAllSelectClickListener(new OnAllSelectClickListener() {
            @Override
            public void OnSelectClick(List<GwcBean.CartlistBean> list) {
                //遍历所有店铺是否都勾选，如果全勾选则勾选底部
                for (int i = 0; i < shopAdapter.getData().size(); i++) {
                    if (shopAdapter.getData().get(i).isShopSelect()) {//true,true,true
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
         * 店铺CheckBox的勾选
         */
        shopAdapter.setOnItemClickListener_shop(new OnViewItemClickListener_shop() {
            @Override
            public void onItemClick(boolean isFlang, View view, int position) {
                shopAdapter.getData().get(position).setShopSelect(isFlang);
                //勾选店铺则店铺内所有商品为勾选，通过遍历改为商品全部勾选
                int length = shopAdapter.getData().get(position).getCartlist().size();
                for (int i = 0; i < length; i++) {
                    shopAdapter.getData().get(position).getCartlist().get(i).setSelect(isFlang);
                }
                shopAdapter.notifyDataSetChanged();
                //遍历所有店铺是否都勾选，如果全勾选则勾选底部
                for (int i = 0; i < shopAdapter.getData().size(); i++) {
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
            public void onItemClick(int position) {
                if (shopAdapter.getData().size() == 0) {
                    llPay.setVisibility(View.GONE);
                    tvNub.setText("购物车");
                } else {
                    llPay.setVisibility(View.VISIBLE);
                    showCommodityCalculation();
                }

            }
        });

    }

    @Override
    public void loadData() {
        initData();
    }

    private void initData() {
        //        OkGo.<Result<List<GwcBean>>>post("http://www.mocky.io/v2/5ea7d7aa2f0000d8a0c4f04e")//三个店铺的数据
//        OkGo.<Result<List<GwcBean>>>post("http://www.mocky.io/v2/5eb3790a32000059cc7b88f3")//二个店铺的数据
        OkGo.<Result<List<GwcBean>>>post("http://www.mocky.io/v2/5eb3a46f3200007c477b89dd")//数据单价都为1
                .execute(new MCallBack<Result<List<GwcBean>>>(this) {
                    @Override
                    public void onSuccess(Response<Result<List<GwcBean>>> response) {
                        if (mSwipeRefreshLayout != null && mSwipeRefreshLayout.isRefreshing()) {
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                        if (response.body().isSuccess()) {
                            if (response.body().data.size() == 0) {
                                llPay.setVisibility(View.GONE);
                                tvNub.setText("购物车");
                            } else {
                                llPay.setVisibility(View.VISIBLE);
                            }
                            shopAdapter.setNewData(response.body().data);
                            setTvNub();
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
        setTvNub();
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

    /**
     * 购物车的数量
     */
    private void setTvNub() {
        shopNumber = 0;
        for (int i = 0; i < shopAdapter.getData().size(); i++) {
            shopNumber += shopAdapter.getData().get(i).getCartlist().size();
        }
        tvNub.setText("购物车（" + shopNumber + "）");
    }

    /**
     * 删除选中item
     */
    public void removeChecked() {
        int iMax = shopAdapter.getData().size() - 1;
        //这里要倒序，因为要删除mDatas中的数据，mDatas的长度会变化
        for (int i = iMax; i >= 0; i--) {
            if (shopAdapter.getData().get(i).isShopSelect()) {
                shopAdapter.getData().remove(i);
                /*
                刷新方式不同，产生的动画不同
                  刷新方式1
                 */
//                shopAdapter.notifyItemRemoved(i);
//                shopAdapter.notifyItemRangeChanged(i, shopAdapter.getData().size());
                /*
                刷新方式二
                 */
                shopAdapter.notifyDataSetChanged();

            } else {
                int length = shopAdapter.getData().get(i).getCartlist().size() - 1;
                for (int j = length; j >= 0; j--) {
                    if (shopAdapter.getData().get(i).getCartlist().get(j).isSelect()) {
                        shopAdapter.getData().get(i).getCartlist().remove(j);
//                        shopAdapter.notifyItemRemoved(i);
//                        shopAdapter.notifyItemRangeChanged(i, shopAdapter.getData().size());
                        shopAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    }

    @OnClick({R.id.tv_bianji, R.id.tv_submit, R.id.tv_all_select, R.id.pay_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_delete:
                removeChecked();
                if (shopAdapter.getData().size() == 0) {
                    llPay.setVisibility(View.GONE);
                    tvNub.setText("购物车");
                } else {
                    llPay.setVisibility(View.VISIBLE);
                    showCommodityCalculation();
                }
                break;
            case R.id.tv_bianji:
                flag = !flag;
                if (flag) {
                    tvBianji.setText("完成");
                    payPrice.setVisibility(View.GONE);
                    tvSubmit.setVisibility(View.GONE);
                    payDelete.setVisibility(View.VISIBLE);
                } else {
                    tvBianji.setText("编辑");
                    payPrice.setVisibility(View.VISIBLE);
                    tvSubmit.setVisibility(View.VISIBLE);
                    payDelete.setVisibility(View.GONE);
                }
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

    @Override
    public void onRefresh() {
        initData();
        //刷新的话需要将数据清空
        isCheck = false;
        flag = false;
        tvAllNum.setText("共0件商品");
        tvAllPrice.setText("总价：¥ 0.0");
        tv_all_select.setChecked(false);
    }
}
