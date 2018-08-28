package com.itdeveapps.omar.translatordictionary.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

public class Utils {

    public static void forceHideKeyboard(Activity activity){
        InputMethodManager imm =(InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
