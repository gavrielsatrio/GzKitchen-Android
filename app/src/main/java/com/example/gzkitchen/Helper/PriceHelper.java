package com.example.gzkitchen.Helper;

import java.text.NumberFormat;
import java.util.Locale;

public class PriceHelper {
    public String convertToRupiah(int price) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        formatter.setMaximumFractionDigits(0);

        return formatter.format(price).replace("$", "Rp");
    }
}
