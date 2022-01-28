package com.example.gzkitchen.Helper;

public class OrderIDHelper {
    public String getDisplayOrderID(String orderID) {
        StringBuilder sb = new StringBuilder(orderID);
        sb.reverse();
        for(int j = sb.length(); j < 8; j++) {
            sb.append("0");
        }
        sb.append("-KG");
        sb.reverse();

        return sb.toString();
    }
}
