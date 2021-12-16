package com.example.gzkitchen;

public class StatusController {
    public String getStatusByID(int statusID) {
        String status = "";

        if(statusID == 1) {
            status = "Order Placed";
        } else if(statusID == 2) {
            status = "On Cook";
        } else if(statusID == 3) {
            status = "Finish Cooked";
        } else if(statusID == 4) {
            status = "Delivered";
        }

        return status;
    }
}
