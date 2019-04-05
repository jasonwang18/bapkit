package com.supcon.mes.mbap.utils;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;


/**
 * Created by wangshizhan on 2017/10/20.
 * Email:wangshizhan@supcon.com
 */
public class KeyHelper {
    public interface OnActionListener {
        void onActionCompleted();
    }

    public static void doActionDone(EditText editText, final OnActionListener listener) {
        editText.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getAction() == KeyEvent.ACTION_DOWN)) {
                KeyboardUtil.hideKeyboard(view.getContext(), view);
                new Handler().postDelayed(() -> {
                    if (listener != null)
                        listener.onActionCompleted();
                }, 300);
            }
            return false;
        });
    }

    public static void doActionDone(EditText editText, final View view) {
        editText.setOnEditorActionListener((textView, actionId, event) -> {
            if((event != null && event.getKeyCode()==KeyEvent.KEYCODE_ENTER)){
                return false;
            }

            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT||(event != null && event.getAction() == KeyEvent.ACTION_DOWN)) {
                KeyboardUtil.hideKeyboard(textView.getContext(), textView);
                new Handler().postDelayed(() -> view.performClick(), 300);
                return true;
            }
            return false;
        });
    }

    public static void doActionEnter(EditText editText, final View view) {
        editText.setOnEditorActionListener((textView, actionId, event) -> {



            if (actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getAction() == KeyEvent.ACTION_DOWN)
                    ) {
                KeyboardUtil.hideKeyboard(textView.getContext(), textView);
                view.requestFocus();
                return true;
            }
            return false;
        });
    }

    public static void doActionNext(EditText editText, final View view) {
        editText.setOnEditorActionListener((textView, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getAction() == KeyEvent.ACTION_DOWN)
                    || (event != null && event.getKeyCode()==KeyEvent.KEYCODE_ENTER)) {
                KeyboardUtil.hideKeyboard(textView.getContext(), textView);
//                new Handler().postDelayed(() -> view.requestFocus(), 300);
                view.requestFocus();
                return true;
            }
            return false;
        });
    }

    public static void doActionNext(EditText editText, final View view, boolean hideKeyboard) {
        editText.setOnEditorActionListener((textView, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getAction() == KeyEvent.ACTION_DOWN)
                    || (event != null && event.getKeyCode()==KeyEvent.KEYCODE_ENTER)) {
                if(hideKeyboard)
                    KeyboardUtil.hideKeyboard(textView.getContext(), textView);
//                new Handler().postDelayed(() -> view.requestFocus(), 300);
                view.requestFocus();
                return true;
            }
            return false;
        });
    }

    public static void doActionNext(EditText editText, boolean hideKeyboard, final OnActionListener listener) {
        editText.setOnEditorActionListener((textView, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE
                    || (event != null && event.getAction() == KeyEvent.ACTION_DOWN)
                    || (event != null && event.getKeyCode()==KeyEvent.KEYCODE_ENTER)) {
                if(hideKeyboard)
                    KeyboardUtil.hideKeyboard(textView.getContext(), textView);
                if (listener != null)
                    listener.onActionCompleted();
                return true;
            }
            return false;
        });
    }


    public static void doActionSend(EditText editText, final View view) {
        editText.setOnEditorActionListener((textView, actionId, event) -> {
            if (event != null && actionId == KeyEvent.ACTION_DOWN) {
                // if (KeyUtil.isEnterKey(actionId, event)) {
                view.performClick();
                //   }
            }
            return false;
        });
    }

    public static void doActionSend(EditText editText, final OnActionListener listener) {
        editText.setOnEditorActionListener((textView, actionId, event) -> {
            if (event != null && actionId == KeyEvent.ACTION_DOWN) {
                // if (KeyUtil.isEnterKey(actionId, event)) {
                if (listener != null)
                    listener.onActionCompleted();
                //   }
            }
            return false;
        });
        }
}
