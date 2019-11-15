package com.hrm.hrm.CustonStyle;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by shree on 3/1/2018.
 */

public class EditTextRalewaynormal extends EditText {

    public EditTextRalewaynormal(Context context) {
        super(context);

        applyCustomFont(context);
    }

    public EditTextRalewaynormal(Context context, AttributeSet attrs) {
        super(context, attrs);

        applyCustomFont(context);
    }

    public EditTextRalewaynormal(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        applyCustomFont(context);
    }

    private void applyCustomFont(Context context) {
        Typeface customFont = Typeface.createFromAsset(context.getAssets(),"font/OpenSans-Regular.ttf");
        setTypeface(customFont);
    }
}
