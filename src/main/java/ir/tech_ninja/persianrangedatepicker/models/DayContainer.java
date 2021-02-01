package ir.tech_ninja.persianrangedatepicker.models;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ir.tech_ninja.persianrangedatepicker.customeView.CustomTextView;
import ir.tech_ninja.persianrangedatepicker.utils.PersianCalendar;

public class DayContainer {
    public RelativeLayout rootView;
    public CustomTextView tvDate;
    public TextView tvDateGeorgian, tvCurrentDay;
    public AppCompatImageView imgEvent;
    public View strip;

    public DayContainer(RelativeLayout rootView) {
        this.rootView = rootView;
        strip = rootView.getChildAt(0);
        tvDate = (CustomTextView) rootView.getChildAt(1);
        tvDateGeorgian = (TextView) rootView.getChildAt(4);
        imgEvent = (AppCompatImageView) rootView.getChildAt(2);
        tvCurrentDay = (TextView) rootView.getChildAt(3);
    }

    public static int GetContainerKey(PersianCalendar cal) {
        String str = cal.getPersianShortDate().replace("/", "");
        return Integer.valueOf(str);
    }

    public static PersianCalendar GetDateFromKey(String key) {
        PersianCalendar persianCalendar = new PersianCalendar();

        int _y = Integer.parseInt(key.substring(0, 4));
        int _m = Integer.parseInt(key.substring(4, 6));
        int _d = Integer.parseInt(key.substring(6));

        persianCalendar.setPersianDate(_y, _m - 1, _d);
        return persianCalendar;
    }
}
