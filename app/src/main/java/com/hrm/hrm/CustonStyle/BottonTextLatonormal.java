package com.hrm.hrm.CustonStyle;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by shree on 3/1/2018.
 */

public class BottonTextLatonormal extends Button
{
    public BottonTextLatonormal(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public BottonTextLatonormal(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public BottonTextLatonormal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),"font/OpenSans-Regular.ttf");
        setTypeface(customFont);
    }
}
