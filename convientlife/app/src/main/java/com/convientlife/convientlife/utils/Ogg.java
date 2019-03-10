package com.convientlife.convientlife.utils;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.InputMethodManager;

/*
 * Created by Administrator on 2019/3/10 0010.
 */
public class Ogg {
    /**
     * 隐藏软键盘
     */

    public static void hideKeyBoard(Activity context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {

            imm.hideSoftInputFromWindow(context.getWindow().getDecorView().getWindowToken(), 0);

        }

    }

    /**
     * 显示软键盘
     */

    public static void showKeyBoard(Activity context) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null) {

            imm.showSoftInputFromInputMethod(context.getWindow().getDecorView().getWindowToken(), 0);

        }

    }
}
