package com.qichang.hfydemo;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.qichang.hfydemo.adapter.ShopAdapter;
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
    TextView tv_all_select;
    @BindView(R.id.ll_pay)
    LinearLayout llPay;
    private ShopAdapter shopAdapter;
    private boolean mSelect;

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

        //实时监控全选按钮
        shopAdapter.setResfreshListener(new ShopAdapter.OnResfreshListener() {
            @Override
            public void onResfresh(boolean isSelect,int po) {
//                List<String> isSelectStr = new ArrayList<>();
//                for (int i = 0; i < shopAdapter.getData().size(); i++) {
//                    if (!shopAdapter.getData().get(i).isShopSelect()) {
//                        isSelectStr.add("没选");
//                    } else {
//                        isSelectStr.add("选");
//                    }
//                }
//                Log.w("hfydemo", "isSelectStr:" + ParseJsonUtil.toJson(isSelectStr));
//                if (isSelectStr.contains("没选")) {
//                    shopAdapter.getData().get(isSelect).setShopSelect(false);
//                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
//                    tv_all_select.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
//                    mSelect = false;
//                } else {
//                    shopAdapter.getData().get(isSelect).setShopSelect(true);
//                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
//                    tv_all_select.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
//                    mSelect = true;
//                }

                mSelect = isSelect;
                if(isSelect){
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
                    tv_all_select.setCompoundDrawablesWithIntrinsicBounds(left,null,null,null);
                }else {
                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
                    tv_all_select.setCompoundDrawablesWithIntrinsicBounds(left,null,null,null);
                }

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


    @OnClick({R.id.tv_bianji, R.id.tv_submit, R.id.tv_all_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bianji:
                break;
            case R.id.tv_submit:
                break;
            case R.id.tv_all_select:
//                if (mSelect) {
//                    mSelect = false;
//                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
//                    tv_all_select.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
//                    for (int i = 0; i < shopAdapter.getData().size(); i++) {
//                            shopAdapter.getData().get(i).setShopSelect(false);
//                    }
//                } else {
//                    mSelect = true;
//                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
//                    tv_all_select.setCompoundDrawablesWithIntrinsicBounds(left, null, null, null);
//                    for (int i = 0; i < shopAdapter.getData().size(); i++) {
//                        shopAdapter.getData().get(i).setShopSelect(true);
//                    }
//                }
//                shopAdapter.notifyDataSetChanged();




//                mSelect = !mSelect;
//                if(mSelect){
//                    Drawable left = getResources().getDrawable(R.drawable.shopcart_selected);
//                    tv_all_select.setCompoundDrawablesWithIntrinsicBounds(left,null,null,null);
//                    for(int i = 0;i < shopAdapter.getData().size();i++){
////                        shopAdapter.getData().get(i).setSelect(true);
//                        shopAdapter.getData().get(i).setShopSelect(true);
//                    }
//                }else{
//                    Drawable left = getResources().getDrawable(R.drawable.shopcart_unselected);
//                    tv_all_select.setCompoundDrawablesWithIntrinsicBounds(left,null,null,null);
//                    for(int i = 0;i <  shopAdapter.getData().size();i++){
////                        shopAdapter.getData().get(i).setSelect(false);
//                        shopAdapter.getData().get(i).setShopSelect(false);
//                    }
//                }
                Log.w("hfydemo","之前："+ParseJsonUtil.toJson(shopAdapter.getData()));
                for(int i = 0;i <  shopAdapter.getData().size();i++){
//                        shopAdapter.getData().get(i).setSelect(false);
                        shopAdapter.getData().get(i).setShopSelect(true);
                    }
                shopAdapter.notifyDataSetChanged();
                Log.w("hfydemo","之后："+ParseJsonUtil.toJson(shopAdapter.getData()));

                break;
        }
    }
}
