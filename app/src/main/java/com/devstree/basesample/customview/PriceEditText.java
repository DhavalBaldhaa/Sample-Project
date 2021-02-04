package com.devstree.basesample.customview;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;

public class PriceEditText extends EditText implements TextWatcher {

    public PriceEditText(Context context) {
        super(context);
        addTextChangedListener(this);
    }

    public PriceEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTextChangedListener(this);

    }

    public PriceEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        removeTextChangedListener(this);
        super.setText(text, type);
        addTextChangedListener(this);
    }

    @Override
    public void afterTextChanged(Editable s) {
//        double value = TextUtil.parsedouble(s.toString());
//        String newValue = String.format(Locale.ENGLISH, "%.3f", value);
//        setText(newValue);

    }
}
