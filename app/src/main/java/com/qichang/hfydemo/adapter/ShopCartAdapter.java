package com.qichang.hfydemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qichang.hfydemo.R;
import com.qichang.hfydemo.bean.ShopCartBean;
import com.qichang.hfydemo.util.MGlide;

import java.util.List;

/**
 * @author hfy
 * @description
 * @time 2020/4/29
 */
public class ShopCartAdapter extends RecyclerView.Adapter<ShopCartAdapter.MyViewHolder> {
    private Context mContext;
    private List<ShopCartBean.CartlistBean> data;
    private OnDeleteClickListener mOnDeleteClickListener;
    private OnEditClickListener mOnEditClickListener;
    private OnResfreshListener mOnResfreshListener;
    private OnVisibilitytClickListener mOnVisibilitytClickListener;

    public ShopCartAdapter(Context mContext, List<ShopCartBean.CartlistBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.item_shopcart, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int position) {
        MGlide.LoadWithRoundCorner(mContext, data.get(position).getDefaultPic(), R.mipmap.small_zwimg,
                myViewHolder.img, 8);
        Log.w("hfydemo", "--------" + position);
        //关于头部店铺
        if (position > 0) {
            //如果现在item的店铺id与上一个item的店铺id相同，那就是说明是同一个店铺，则店铺layouot只显示一个
            if (data.get(position).getShopId() == data.get(position - 1).getShopId()) {
                myViewHolder.ll_Top.setVisibility(View.GONE);
            } else {
                myViewHolder.ll_Top.setVisibility(View.VISIBLE);
            }
        } else {
            myViewHolder.ll_Top.setVisibility(View.VISIBLE);
        }

        myViewHolder.tv_style.setText(data.get(position).getColor() + ";" + data.get(position).getSize());
        myViewHolder.tv_shop_name.setText(data.get(position).getShopName());//店铺名称
        myViewHolder.tv_cloth_name.setText(data.get(position).getProductName());//商品名称
        myViewHolder.tv_price.setText(data.get(position).getPrice() + "");//价格
        myViewHolder.tv_cloth_num.setText(data.get(position).getCount() + "");//

        //实时监控全选按钮
        if (mOnResfreshListener != null) {
            boolean isSelect = false;
            for (int i = 0; i < data.size(); i++) {
                if (!data.get(i).isSelect()) {
                    isSelect = false;
                    break;
                } else {
                    isSelect = true;
                }
            }
            mOnResfreshListener.onResfresh(isSelect);
        }

        //减商品
        myViewHolder.img_cloth_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getCount() > 1) {
                    int count = data.get(position).getCount() - 1;
                    if (mOnEditClickListener != null) {
                        mOnEditClickListener.onEditClick(position, data.get(position).getId(), count);
                    }
                    data.get(position).setCount(count);
                    notifyDataSetChanged();
                }
            }
        });
        //加商品
        myViewHolder.img_cloth_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = data.get(position).getCount() + 1;
                if (mOnEditClickListener != null) {
                    mOnEditClickListener.onEditClick(position, data.get(position).getId(), count);
                }
                data.get(position).setCount(count);
                notifyDataSetChanged();
            }
        });
        if (data.get(position).isSelect()) {
            myViewHolder.img_cloth_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_selected));
        } else {
            myViewHolder.img_cloth_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_unselected));
        }

        if (data.get(position).isShopSelect()) {
            myViewHolder.img_shop_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_selected));
        } else {
            myViewHolder.img_shop_select.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shopcart_unselected));
        }

        //勾选商品
        myViewHolder.img_cloth_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setSelect(!data.get(position).isSelect());
                //通过循环找出不同商铺的第一个商品的位置
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getIsFirst() == 1) {
                        //遍历去找出同一家商铺的所有商品的勾选情况
                        for (int j = 0; j < data.size(); j++) {
                            //如果是同一家商铺的商品，并且其中一个商品是未选中，那么商铺的全选勾选取消
                            if (data.get(j).getShopId() == data.get(i).getShopId() && !data.get(j).isSelect()) {
                                data.get(i).setShopSelect(false);
                                break;
                            } else {
                                //如果是同一家商铺的商品，并且所有商品是选中，那么商铺的选中全选勾选
                                data.get(i).setShopSelect(true);
                            }
                        }
                    }
                }
                notifyDataSetChanged();
            }
        });

        //勾选店铺，就是勾选改店铺商品
        myViewHolder.img_shop_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getIsFirst() == 1) {
                    data.get(position).setShopSelect(!data.get(position).isShopSelect());
                    for (int i = 0; i < data.size(); i++) {
                        if (data.get(i).getShopId() == data.get(position).getShopId()) {
                            data.get(i).setSelect(data.get(position).isShopSelect());
                        }
                    }
                    notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        int count = (data == null ? 0 : data.size());
//        if (headerView != null) {
//            count++;
//        }
        return count;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_shop_select, img_cloth_select, img, img_cloth_minus, img_cloth_add;
        private TextView tv_shop_name, tv_style, tv_price, tv_cloth_num, tv_cloth_name;
        private LinearLayout ll_Top;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_shop_select = (ImageView) itemView.findViewById(R.id.img_shop_select);
            img_cloth_select = (ImageView) itemView.findViewById(R.id.img_cloth_select);
            img = (ImageView) itemView.findViewById(R.id.img);
            img_cloth_minus = (ImageView) itemView.findViewById(R.id.img_cloth_minus);
            img_cloth_add = (ImageView) itemView.findViewById(R.id.img_cloth_add);

            tv_shop_name = (TextView) itemView.findViewById(R.id.tv_shop_name);
            tv_style = (TextView) itemView.findViewById(R.id.tv_style);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_cloth_num = (TextView) itemView.findViewById(R.id.tv_cloth_num);
            tv_cloth_name = (TextView) itemView.findViewById(R.id.tv_cloth_name);
            ll_Top = (LinearLayout) itemView.findViewById(R.id.ll_Top);
        }
    }

    // 调用删除某个规格商品的接口
    public interface OnDeleteClickListener {
        void onDeleteClick(View view, int position, int cartid);
    }

    public void setOnDeleteClickListener(OnDeleteClickListener mOnDeleteClickListener) {
        this.mOnDeleteClickListener = mOnDeleteClickListener;
    }

    //修改数量接口
    public interface OnEditClickListener {
        void onEditClick(int position, int cartid, int count);
    }

    public void setOnEditClickListener(OnEditClickListener mOnEditClickListener) {
        this.mOnEditClickListener = mOnEditClickListener;
    }

    //实时监控全选按钮
    public interface OnResfreshListener {
        void onResfresh(boolean isSelect);
    }

    public void setResfreshListener(OnResfreshListener mOnResfreshListener) {
        this.mOnResfreshListener = mOnResfreshListener;
    }


    //删除按钮监听是否需要隐藏底部结算
    public interface OnVisibilitytClickListener {

        void onOnVisibilitytClick(boolean v);
    }

    public void setVisibilitytClickListener(OnVisibilitytClickListener mOnVisibilitytClickListener) {
        this.mOnVisibilitytClickListener = mOnVisibilitytClickListener;
    }
}
