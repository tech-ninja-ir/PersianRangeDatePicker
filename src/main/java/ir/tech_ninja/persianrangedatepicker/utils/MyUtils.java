package ir.tech_ninja.persianrangedatepicker.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class MyUtils {
    private static final MyUtils ourInstance = new MyUtils();

    public static MyUtils getInstance() {
        return ourInstance;
    }

    private MyUtils() {
    }

    public void Toast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String getMiladyMonthName(int month) {
        switch (month) {
            case 1:
                return "ژانویه";
            case 2:
                return "فوریه";
            case 3:
                return "مارس";
            case 4:
                return "آوریل";
            case 5:
                return "می";
            case 6:
                return "ژوئن";
            case 7:
                return "جولای";
            case 8:
                return "آگوست";
            case 9:
                return "سپتامبر";
            case 10:
                return "اکتبر";
            case 11:
                return "نوامبر";
            case 12:
                return "دسامبر";
        }
        return "";
    }

}
