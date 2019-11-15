package com.hrm.hrm.CustonStyle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shree on 3/1/2018.
 */

@SuppressLint("AppCompatCustomView")
public class TextViewRalewaynormal extends TextView {

    public TextViewRalewaynormal(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public TextViewRalewaynormal(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public TextViewRalewaynormal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),"font/OpenSans-Regular.ttf");
        setTypeface(customFont);
    }
}