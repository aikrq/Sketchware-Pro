package com.besome.sketch.lib.base;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

import com.sketchware.remod.R;

import a.a.a.wB;

public class BaseWidget extends LinearLayout {
    private ImageView img_widget;
    private TextView tv_widget;
    private View divider_widget;
    @DrawableRes
    private int widgetImgResId;
    private int widgetType;

    public BaseWidget(Context context) {
        super(context);
        View.inflate(context, R.layout.widget_layout, this);

        setDrawingCacheEnabled(true);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        a(context);
    }

    public void a(Context context) {
        img_widget = findViewById(R.id.img_widget);
        tv_widget = findViewById(R.id.tv_widget);
        divider_widget = findViewById(R.id.divider_widget);
    }

    public int getWidgetImageResId() {
        return widgetImgResId;
    }

    public String getWidgetName() {
        return tv_widget.getText().toString();
    }

    public int getWidgetType() {
        return widgetType;
    }

    public void setWidgetImage(@DrawableRes int image) {
        this.widgetImgResId = image;
        img_widget.setImageResource(image);
    }

    public void setWidgetName(String widgetName) {
        tv_widget.setText(widgetName);
    }

    public void setWidgetNameTextSize(float sizeSp) {
        tv_widget.setTextSize(sizeSp);
    }

    public void setWidgetType(a widgetType) {
        this.widgetType = widgetType.ordinal();
    }

    public void setHideDivider(boolean hideDivider) {
        divider_widget.setVisibility(hideDivider ? View.GONE : View.VISIBLE);
    }

    public enum a {
        a,
        b
    }
}
