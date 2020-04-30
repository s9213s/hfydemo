package com.qichang.hfydemo;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fing.http.MCallBack;
import com.fing.http.Result;
import com.fing.uitl.MToast;
import com.fing.uitl.ParseJsonUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.qichang.hfydemo.adapter.ShopCartAdapter;
import com.qichang.hfydemo.bean.ShopCartBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopActivity extends AppCompatActivity {
    @BindView(R.id.tv_nub)
    TextView tvNub;
    @BindView(R.id.ll_Top)
    RelativeLayout llTop;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.tv_all_select)
    TextView tvAllSelect;
    @BindView(R.id.tv_all_price)
    TextView tvAllPrice;
    @BindView(R.id.tv_all_num)
    TextView tvAllNum;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;

    private ShopCartAdapter mShopCartAdapter;
    private List<ShopCartBean.CartlistBean> mAllOrderList = new ArrayList<>();
    private ArrayList<ShopCartBean.CartlistBean> mGoPayList = new ArrayList<>();
    private int mCount, mPosition;
    private float mTotalPrice1;
    private boolean mSelect;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        ButterKnife.bind(this);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        mShopCartAdapter = new ShopCartAdapter(this, mAllOrderList);
        recycler.setAdapter(mShopCartAdapter);
        initHttp();
//        mShopCartAdapter.setVisibilitytClickListener(new ShopCartAdapter.OnVisibilitytClickListener() {
//            @Override
//            public void onOnVisibilitytClick(boolean v) {
//                if (v) {
//                    llPay.setVisibility(View.GONE);
//                } else {
//                    llPay.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//        //删除商品接口
//        mShopCartAdapter.setOnDeleteClickListener(new ShopCartAdapter.OnDeleteClickListener() {
//            @Override
//            public void onDeleteClick(View view, int position, int cartid) {
//                mShopCartAdapter.notifyDataSetChanged();
//            }
//        });
        //修改数量接口
        mShopCartAdapter.setOnEditClickListener(new ShopCartAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position, int cartid, int count) {
                mCount = count;
                mPosition = position;
            }
        });
        //实时监控全选按钮
        mShopCartAdapter.setResfreshListener(new ShopCartAdapter.OnResfreshListener() {
            @Override
            public void onResfresh(boolean isSelect) {
                mSelect = isSelect;
                if (isSelect) {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    tvAllSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                } else {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    tvAllSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                }
                float mTotalPrice = 0;
                int mTotalNum = 0;
                mTotalPrice1 = 0;
                mGoPayList.clear();
                for (int i = 0; i < mAllOrderList.size(); i++)
                    if (mAllOrderList.get(i).isSelect()) {
                        mTotalPrice += mAllOrderList.get(i).getPrice() * mAllOrderList.get(i).getCount();
                        mTotalNum += 1;
                        mGoPayList.add(mAllOrderList.get(i));
                    }
                mTotalPrice1 = mTotalPrice;
                tvAllPrice.setText("总价：" + mTotalPrice);
                tvAllNum.setText("共" + mTotalNum + "件商品");
            }
        });
        //全选
        tvAllSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSelect = !mSelect;
                if (mSelect) {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    tvAllSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (int i = 0; i < mAllOrderList.size(); i++) {
                        mAllOrderList.get(i).setSelect(true);
                        mAllOrderList.get(i).setShopSelect(true);
                    }
                } else {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    tvAllSelect.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
                    for (int i = 0; i < mAllOrderList.size(); i++) {
                        mAllOrderList.get(i).setSelect(false);
                        mAllOrderList.get(i).setShopSelect(false);
                    }
                }
                mShopCartAdapter.notifyDataSetChanged();

            }
        });
    }

    private void initHttp() {
        OkGo.<Result<List<ShopCartBean>>>post("http://www.mocky.io/v2/5ea7d7aa2f0000d8a0c4f04e")
//        OkGo.<Result<ShopCartBean>>post("http://www.mocky.io/v2/5ea929232d000097883a433c")
                .execute(new MCallBack<Result<List<ShopCartBean>>>(this) {
                    @Override
                    public void onSuccess(Response<Result<List<ShopCartBean>>> response) {
                        if (response.body().isSuccess()) {
                            List<ShopCartBean.CartlistBean> mlist = new ArrayList<>();
                            for (int i = 0; i < response.body().data.size(); i++) {
                                mAllOrderList.addAll(response.body().data.get(i).getCartlist());
                            }
                            Log.w("hfydemo", "-*****" + ParseJsonUtil.toJson(mAllOrderList));
                            isSelectFirst(mAllOrderList);
                            mShopCartAdapter.notifyDataSetChanged();
                        } else {
                            MToast.showToast(ShopActivity.this, response.body().msg);
                        }
                    }
                });

    }

    @OnClick({R.id.tv_nub, R.id.ll_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_nub:
                break;
            case R.id.ll_pay:
                startActivity(new Intent(ShopActivity.this, GwcActivity.class));
                break;
        }
    }

    public static void isSelectFirst(List<ShopCartBean.CartlistBean> list) {
        if (list.size() > 0) {
            list.get(0).setIsFirst(1);
            for (int i = 1; i < list.size(); i++) {
                if (list.get(i).getShopId() == list.get(i - 1).getShopId()) {
                    list.get(i).setIsFirst(2);
                } else {
                    list.get(i).setIsFirst(1);
                }
            }
        }
    }
}
