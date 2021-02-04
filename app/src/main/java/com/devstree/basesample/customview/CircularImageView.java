package com.devstree.basesample.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.devstree.basesample.utilities.glide.GlideUtil;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.File;

public class CircularImageView extends RoundedImageView {
    public CircularImageView(Context context) {
        super(context);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setUrl(String url) {
        Glide.with(getContext()).load(url).centerCrop().apply(GlideUtil.getDefaultRequestOption()).into(this);
    }

    public void setUrl(File file) {
        Glide.with(getContext()).load(file).centerCrop().apply(GlideUtil.getDefaultRequestOption()).into(this);
    }

    public void setUrl(File file, int res_id) {
        Glide.with(getContext()).load(file).apply(GlideUtil.getDefaultRequestOption(res_id)).into(this);
    }

    public void setUrl(String url, int res_id) {
        Glide.with(getContext()).load(url).apply(GlideUtil.BuildRequestOptionRounded(res_id)).into(this);
    }

    public void loadBitmap(Bitmap bitmap) {
        clear();
        if (bitmap == null) return;
        Glide.with(getContext()).load(bitmap).into(this);
    }

    public void set(int res_id) {
        Glide.with(this).load(res_id).into(this);
    }

    public void clear() {
        Glide.with(getContext()).clear(this);
    }

}
