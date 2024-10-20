package com.besome.sketch.editor.view.palette;

import static mod.SketchwareUtil.dpToPx;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.besome.sketch.lib.ui.CustomScrollView;
import com.google.android.material.color.MaterialColors;
import com.sketchware.remod.R;

import a.a.a.wB;
import dev.aldi.sayuti.editor.view.palette.IconBadgeView;
import dev.aldi.sayuti.editor.view.palette.IconBottomNavigationView;
import dev.aldi.sayuti.editor.view.palette.IconCardView;
import dev.aldi.sayuti.editor.view.palette.IconCircleImageView;
import dev.aldi.sayuti.editor.view.palette.IconCodeView;
import dev.aldi.sayuti.editor.view.palette.IconCollapsingToolbar;
import dev.aldi.sayuti.editor.view.palette.IconGoogleSignInButton;
import dev.aldi.sayuti.editor.view.palette.IconLottieAnimation;
import dev.aldi.sayuti.editor.view.palette.IconMaterialButton;
import dev.aldi.sayuti.editor.view.palette.IconOTPView;
import dev.aldi.sayuti.editor.view.palette.IconPatternLockView;
import dev.aldi.sayuti.editor.view.palette.IconRadioGroup;
import dev.aldi.sayuti.editor.view.palette.IconRecyclerView;
import dev.aldi.sayuti.editor.view.palette.IconSwipeRefreshLayout;
import dev.aldi.sayuti.editor.view.palette.IconTabLayout;
import dev.aldi.sayuti.editor.view.palette.IconTextInputLayout;
import dev.aldi.sayuti.editor.view.palette.IconViewPager;
import dev.aldi.sayuti.editor.view.palette.IconWaveSideBar;
import dev.aldi.sayuti.editor.view.palette.IconYoutubePlayer;
import mod.agus.jcoderz.editor.view.palette.IconAnalogClock;
import mod.agus.jcoderz.editor.view.palette.IconAutoCompleteTextView;
import mod.agus.jcoderz.editor.view.palette.IconDatePicker;
import mod.agus.jcoderz.editor.view.palette.IconDigitalClock;
import mod.agus.jcoderz.editor.view.palette.IconGridView;
import mod.agus.jcoderz.editor.view.palette.IconMultiAutoCompleteTextView;
import mod.agus.jcoderz.editor.view.palette.IconRadioButton;
import mod.agus.jcoderz.editor.view.palette.IconRatingBar;
import mod.agus.jcoderz.editor.view.palette.IconSearchView;
import mod.agus.jcoderz.editor.view.palette.IconTimePicker;
import mod.agus.jcoderz.editor.view.palette.IconVideoView;
import mod.hey.studios.util.Helper;

public class PaletteWidget extends LinearLayout {

    private LinearLayout layoutContainer;
    private LinearLayout widgetsContainer;
    private TextView titleLayouts;
    private TextView titleWidgets;
    private CustomScrollView scrollView;

    public PaletteWidget(Context context) {
        super(context);
        initialize(context);
    }

    public PaletteWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public View a(PaletteWidget.a layoutType, String tag) {
        LinearLayout layout = switch (layoutType) {
            case a -> new IconLinearHorizontal(getContext());
            case b -> new IconLinearVertical(getContext());
            case c -> new IconScrollViewHorizontal(getContext());
            case d -> new IconScrollViewVertical(getContext());
        };

        if (tag != null && !tag.isEmpty()) {
            layout.setTag(tag);
        }

        layoutContainer.addView(layout);
        return layout;
    }

    public View a(PaletteWidget.b widgetType, String tag, String text, String resourceName, boolean hideDivider) {
        IconBase iconBase;
        switch (widgetType) {
            case a:
                iconBase = new IconButton(getContext());
                break;

            case c:
                iconBase = new IconEditText(getContext());
                break;

            case b:
                iconBase = new IconTextView(getContext());
                break;

            case d:
                iconBase = new IconImageView(getContext());
                ((IconImageView) iconBase).setResourceName(resourceName);
                break;

            case e:
                iconBase = new IconListView(getContext());
                break;

            case f:
                iconBase = new IconSpinner(getContext());
                break;

            case g:
                iconBase = new IconCheckBox(getContext());
                break;

            case h:
                iconBase = new IconWebView(getContext());
                break;

            case i:
                iconBase = new IconSwitch(getContext());
                break;

            case j:
                iconBase = new IconSeekBar(getContext());
                break;

            case k:
                iconBase = new IconCalendarView(getContext());
                break;

            case l:
                iconBase = new IconAdView(getContext());
                break;

            case m:
                iconBase = new IconProgressBar(getContext());
                break;

            case n:
                iconBase = new IconMapView(getContext());
                break;

            default:
                iconBase = null;
        }

        if (tag != null && !tag.isEmpty()) {
            iconBase.setTag(tag);
        }

        iconBase.setText(text);
        iconBase.setName(resourceName);
        iconBase.setHideDivider(hideDivider);
        widgetsContainer.addView(iconBase);
        return iconBase;
    }

    public void removeWidgetLayouts() {
        layoutContainer.removeAllViews();
    }

    private void initialize(Context context) {
        wB.a(context, this, R.layout.palette_widget);
        layoutContainer = findViewById(R.id.layout);
        widgetsContainer = findViewById(R.id.widget);
        titleLayouts = findViewById(R.id.tv_layout);
        titleWidgets = findViewById(R.id.tv_widget);
        titleLayouts.setText(Helper.getResString(R.string.view_panel_title_layouts));
        titleWidgets.setText(Helper.getResString(R.string.view_panel_title_widgets));
        scrollView = findViewById(R.id.scv);
    }

    public void removeWidgets() {
        widgetsContainer.removeAllViews();
    }

    public void extraTitle(String title, int targetType) {
        LinearLayout target = targetType == 0 ? layoutContainer : widgetsContainer;

        TextView titleView = new TextView(getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(6));
        titleView.setLayoutParams(layoutParams);
        titleView.setText(title);
        titleView.setTextSize(12);
        titleView.setTextColor(MaterialColors.getColor(titleView, com.google.android.material.R.attr.colorPrimary));
        target.addView(titleView);
    }

    public View extraWidget(String tag, String title, String name, boolean hideDivider) {
        IconBase iconBase;
        Context context = getContext();
        iconBase = switch (title) {
            case "DatePicker" -> new IconDatePicker(context);
            case "RatingBar" -> new IconRatingBar(context);
            case "SearchView" -> new IconSearchView(context);
            case "DigitalClock" -> new IconDigitalClock(context);
            case "RadioButton" -> new IconRadioButton(context);
            case "GridView" -> new IconGridView(context);
            case "AutoCompleteTextView" -> new IconAutoCompleteTextView(context);
            case "MultiAutoCompleteTextView" -> new IconMultiAutoCompleteTextView(context);
            case "VideoView" -> new IconVideoView(context);
            case "TimePicker" -> new IconTimePicker(context);
            case "AnalogClock" -> new IconAnalogClock(context);
            case "ViewPager" -> new IconViewPager(context);
            case "BadgeView" -> new IconBadgeView(context);
            case "PatternLockView" -> new IconPatternLockView(context);
            case "WaveSideBar" -> new IconWaveSideBar(context);
            case "SignInButton" -> new IconGoogleSignInButton(context);
            case "MaterialButton" -> new IconMaterialButton(context);
            case "CircleImageView" -> new IconCircleImageView(context);
            case "LottieAnimation" -> new IconLottieAnimation(context);
            case "YoutubePlayer" -> new IconYoutubePlayer(context);
            case "OTPView" -> new IconOTPView(context);
            case "CodeView" -> new IconCodeView(context);
            case "RecyclerView" -> new IconRecyclerView(context);
            default -> null;
        };
        if (tag != null && !tag.isEmpty()) {
            iconBase.setTag(tag);
        }

        iconBase.setText(title);
        iconBase.setName(name);
        iconBase.setHideDivider(hideDivider);
        widgetsContainer.addView(iconBase);
        return iconBase;
    }

    public View extraWidgetLayout(String tag, String name, boolean hideDivider) {
        IconBase iconBase;
        Context context = getContext();
        iconBase = switch (name) {
            case "TabLayout" -> new IconTabLayout(context);
            case "BottomNavigationView" -> new IconBottomNavigationView(context);
            case "CollapsingToolbarLayout" -> new IconCollapsingToolbar(context);
            case "SwipeRefreshLayout" -> new IconSwipeRefreshLayout(context);
            case "RadioGroup" -> new IconRadioGroup(context);
            case "CardView" -> new IconCardView(context);
            case "TextInputLayout" -> new IconTextInputLayout(context);
            default -> null;
        };
        if (tag != null && !tag.isEmpty()) {
            iconBase.setTag(tag);
        }

        iconBase.setHideDivider(hideDivider);
        layoutContainer.addView(iconBase);
        return iconBase;
    }

    public void setLayoutVisible(int visibility) {
        layoutContainer.setVisibility(visibility);
        titleLayouts.setVisibility(visibility);
    }

    public void setScrollEnabled(boolean scrollEnabled) {
        if (scrollEnabled) {
            scrollView.b();
        } else {
            scrollView.a();
        }
    }

    public void setWidgetVisible(int visibility) {
        widgetsContainer.setVisibility(visibility);
        titleWidgets.setVisibility(visibility);
    }

    public enum a {
        a, //eLinearHorizontal
        b, //eLinearVertical
        c, //eScrollHorizontal
        d //eScrollVertical
    }

    public enum b {
        a, //eButton
        b, //eTextView
        c, //eEditText
        d, //eImageView
        e, //eListView
        f, //eSpinner
        g, //eCheckBox
        h, //eWebView
        i, //eSwitch
        j, //eSeekBar
        k, //eCalenderView
        l, //eAddView
        m, //eProgressBar
        n, //eMapView
        o //eRadioButton
    }
}
