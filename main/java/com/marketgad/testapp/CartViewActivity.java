package com.marketgad.testapp;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class CartViewActivity {

    String Serialno,price;
    ImageView prodimage;

    public CartViewActivity(String serialno, String price) {
        Serialno = serialno;
        this.price = price;
    }

    public String getSerialno() {
        return Serialno;
    }

    public void setSerialno(String serialno) {
        Serialno = serialno;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }


}
