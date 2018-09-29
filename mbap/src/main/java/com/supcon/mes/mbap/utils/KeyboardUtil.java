package com.supcon.mes.mbap.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by wangshizhan on 2017/10/20.
 * Email:wangshizhan@supcon.com
 */

public class KeyboardUtil {

    public static void toggleKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInputFromWindow(editText.getWindowToken(), 0, 0);//显示
    }

    public static void openKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInputFromInputMethod(editText.getWindowToken(), 0);//显示
    }

    public static void hideKeyboard(Context context, View editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);//显示
    }

    public static boolean showSearchInputMethod(View view) {
        final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.showSoftInput(view, 0);
        }
        return false;
    }


    public static boolean hideSearchInputMethod(View view, HideKeyboardCallback hideKeyboardCallback) {
        final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            if(hideKeyboardCallback != null) {
                hideKeyboardCallback.executingHideKeyboard();
            }
            return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }

    public static void editTextRequestFocus(View view) {
        view.requestFocus();
        showSearchInputMethod(view);
    }

    public static void editTextClearFocus(View view ) {
        view.clearFocus();
        hideSearchInputMethod(view);
    }

    public static boolean hideSearchInputMethod(View view) {
        final InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }

    public interface HideKeyboardCallback{
        void executingHideKeyboard();
    }
}
