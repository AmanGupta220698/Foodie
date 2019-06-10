package com.aman.androidfoodie.Common;

import com.aman.androidfoodie.Model.User;

/**
 * Created by Aman on 4/25/2019.
 */

public class Common {
    public static User currentUser;
    public static final String UPDATE="Update";
    public static final String DELETE="Delete";
    public static String convertCodeToStatus(String status){
        if(status.equals("0")){
            return "Placed";
        }
        else if(status.equals("1")){
            return "Order Approved";
        }
        else{
            return "Delivered";
        }
    }
}
