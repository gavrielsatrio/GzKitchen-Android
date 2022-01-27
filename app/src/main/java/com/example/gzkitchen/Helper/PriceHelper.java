package com.example.gzkitchen.Helper;

import java.text.NumberFormat;

public class PriceHelper {
    public String convertToRupiah(int price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(0);

        return formatter.format(price).replace("$", "Rp");
    }
}
