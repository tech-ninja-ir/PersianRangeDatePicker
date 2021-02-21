package ir.tech_ninja.persianrangedatepicker.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.util.SimpleArrayMap;

public class FontUtils {
    private static final String FONT_NAME = "Yekan_Bakh_Medium";
    private static final String FONT_LATIN_NUMBER = "Yekan_Bakh_Fa_En_Medium";

    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();
    private static final SimpleArrayMap<String, Typeface> cacheEn = new SimpleArrayMap<>();

    public static Typeface Default(Context context) {
        synchronized (cache) {
            if (!cache.containsKey(FONT_NAME)) {
                Typeface t = Typeface.createFromAsset(context.getAssets(), "fonts/" + FONT_NAME + ".ttf");
                cache.put(FONT_NAME, t);
                return t;
            }
            return cache.get(FONT_NAME);
        }
    }

    public static Typeface latinNumber(Context context) {
        synchronized (cacheEn) {
            if (!cacheEn.containsKey(FONT_LATIN_NUMBER)) {
                Typeface t = Typeface.createFromAsset(context.getAssets(), "fonts/" + FONT_LATIN_NUMBER + ".ttf");
                cacheEn.put(FONT_LATIN_NUMBER, t);
                return t;
            }
            return cacheEn.get(FONT_LATIN_NUMBER);
        }
    }

}
