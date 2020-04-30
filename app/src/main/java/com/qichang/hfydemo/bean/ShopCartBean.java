package com.qichang.hfydemo.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/10/14.
 */

public class ShopCartBean {
    /**
     * shopId : 1
     * shopName : H&M官方商城
     * cartlist : [{"id":1001001,"shopId":1,"shopName":"H&M官方商城","defaultPic":"111.jpg","productId":1001,"productName":"衬衫","color":"白色","size":"S","price":79.88,"count":2},{"id":1001002,"shopId":1,"shopName":"H&M官方商城","defaultPic":"111.jpg","productId":1001,"productName":"衬衫","color":"黑色","size":"M","price":79.88,"count":1}]
     */

    private int shopId;
    private String shopName;
    private List<CartlistBean> cartlist;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public List<CartlistBean> getCartlist() {
        return cartlist;
    }

    public void setCartlist(List<CartlistBean> cartlist) {
        this.cartlist = cartlist;
    }

    public static class CartlistBean {
        /**
         * id : 1001001
         * shopId : 1
         * shopName : H&M官方商城
         * defaultPic : 111.jpg
         * productId : 1001
         * productName : 衬衫
         * color : 白色
         * size : S
         * price : 79.88
         * count : 2
         */

        private int id;
        private int shopId;
        private String shopName;
        private String defaultPic;
        private int productId;
        private String productName;
        private String color;
        private String size;
        private float price;
        private int count;

        //新增部分 ----开始
        private boolean isSelect;//是否勾选
        private int isFirst = 2;//记住第一个
        private boolean isShopSelect;//店铺的勾选
        private boolean ActionBarEditor;// 全局对该组的编辑状态

        public boolean isSelect() {
            return isSelect;
        }

        public void setSelect(boolean select) {
            isSelect = select;
        }

        public int getIsFirst() {
            return isFirst;
        }

        public void setIsFirst(int isFirst) {
            this.isFirst = isFirst;
        }

        public boolean isShopSelect() {
            return isShopSelect;
        }

        public void setShopSelect(boolean shopSelect) {
            isShopSelect = shopSelect;
        }

        public boolean isActionBarEditor() {
            return ActionBarEditor;
        }

        public void setActionBarEditor(boolean actionBarEditor) {
            ActionBarEditor = actionBarEditor;
        }
        //新增部分 ----结束

        //原部分
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getShopId() {
            return shopId;
        }

        public void setShopId(int shopId) {
            this.shopId = shopId;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getDefaultPic() {
            return defaultPic;
        }

        public void setDefaultPic(String defaultPic) {
            this.defaultPic = defaultPic;
        }

        public int getProductId() {
            return productId;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
