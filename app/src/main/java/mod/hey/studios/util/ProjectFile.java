package mod.hey.studios.util;

import static pro.sketchware.utility.ThemeUtils.isDarkThemeEnabled;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.core.content.res.ColorStateListInflaterCompat;

import pro.sketchware.SketchApplication;

import a.a.a.lC;
import a.a.a.yB;

import java.lang.reflect.Method;

public class ProjectFile {
    public static final String COLOR_ACCENT = "color_accent";
    public static final String COLOR_PRIMARY = "color_primary";
    public static final String COLOR_PRIMARY_DARK = "color_primary_dark";
    public static final String COLOR_CONTROL_HIGHLIGHT = "color_control_highlight";
    public static final String COLOR_CONTROL_NORMAL = "color_control_normal";

    // Android Framework
    public static final String COLOR_BACKGROUND = "color_background";

    public static int getColor(String sc_id, String color) {
        return yB.a(lC.b(sc_id), color, getDefaultColor(color));
        /*
        Old in-progress by Mike?
        HashMap<String, Object> hashMap = lC.b(sc_id);

        return 0;//yB.a(hashMap, color, getDefaultColor(color));
        */
    }

	/*
	 // comment by Jbk0
	 // the smali files say something completely different lol

	 package a.a.a;
	 import java.util.Map;

	 public class yB {
	 public static int a(Map<String, Object> paramMap, String paramString, int paramInt) {

	 return 0;

	 }
	 }
	*/

    public static int getDefaultColor(String color) {
        final Context ctx = SketchApplication.getContext();
        final boolean isDark = isDarkThemeEnabled(ctx);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return switch (color) {
                case COLOR_CONTROL_HIGHLIGHT -> ctx.getColor(isDark ? android.R.color.system_accent1_900 : android.R.color.system_accent1_100);
                case COLOR_BACKGROUND ->
                    getLStaredColor(ctx, android.R.color.system_neutral2_600,
                            0xfffafafa, isDark ? 6.0f : 98.0f);
                default -> ctx.getColor(android.R.color.system_accent1_600);
            };
        } else {
            return switch (color) {
                case COLOR_CONTROL_HIGHLIGHT -> 0x202196f3;
                case COLOR_BACKGROUND -> isDark ? 0xff121212 : 0xfffafafa;
                default -> 0xff2196f3;
            };
        }
    }

    private static int getLStaredColor(Context ctx, int colorId, int defaultColor, float lStar) {
        try {
            Method method = ColorStateListInflaterCompat.class.getDeclaredMethod(
                    "modulateColorAlpha", int.class, float.class, float.class);
            method.setAccessible(true);
            return (int) method.invoke(ColorStateListInflaterCompat.class, ctx.getColor(colorId), 1.0f, lStar);
        } catch (Exception e) {
            e.printStackTrace();
            return defaultColor;
        }
    }
}
