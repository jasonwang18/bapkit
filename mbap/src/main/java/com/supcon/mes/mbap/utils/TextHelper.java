package com.supcon.mes.mbap.utils;

import android.graphics.Typeface;
import android.text.Html;
import android.text.style.ImageSpan;
import android.widget.TextView;

import static com.supcon.mes.mbap.MBapConfig.REQUIRED_MARK;

/**
 * Created by wangshizhan on 2018/2/5.
 * Email:wangshizhan@supcon.com
 */

public class TextHelper {

    public static void setTextStyle(TextView textView, int style){

        textView.setTypeface(Typeface.defaultFromStyle(style));

    }

    public static void setRequired(boolean isRequired, TextView textView){

        if(isRequired){
            TextHelper.addRequiredText(" "+REQUIRED_MARK, textView);
        }
        else{
            TextHelper.removeRequiredText(REQUIRED_MARK, textView);
        }

    }


    public static void addRequiredText(String text, TextView textView){

        String originalText = textView.getText().toString();

        if(originalText.contains(REQUIRED_MARK)){
            return;
        }

        String required ="<font color='#c82529'><small>"+text+"</small></font>";
        StringBuilder stringBuilder = new StringBuilder(originalText);
//        stringBuilder.append("(");
        stringBuilder.append(required);
//        stringBuilder.append(")");
        textView.setText(Html.fromHtml(stringBuilder.toString()));
    }

    public static void removeRequiredText(String text, TextView textView){

        String originalText = textView.getText().toString();
        originalText = originalText.replace(text, "");
        textView.setText(originalText);
    }
}
