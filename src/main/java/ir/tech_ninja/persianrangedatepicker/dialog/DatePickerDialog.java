package ir.tech_ninja.persianrangedatepicker.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sardari.ali.persianrangedatepicker.R;

import java.util.Date;

import ir.tech_ninja.persianrangedatepicker.customeView.DateRangeCalendarView;
import ir.tech_ninja.persianrangedatepicker.utils.FontUtils;
import ir.tech_ninja.persianrangedatepicker.utils.MyUtils;
import ir.tech_ninja.persianrangedatepicker.utils.PersianCalendar;

public class DatePickerDialog extends Dialog {
    //region Fields
    private final Context mContext;
    private DateRangeCalendarView calendar;
    private Button btn_Accept;
    private TextView tvDate1, tvDate2;
    private RelativeLayout rlReturn;
    private PersianCalendar persianDate, persianStartDate, persianEndDate;
    private Date date, startDate, endDate;
    private Typeface typeface;
    //endregion
    //region theme
    private int acceptButtonColor, headerBackgroundColor, weekColor, rangeStripColor, selectedDateCircleColor, selectedDateColor, defaultDateColor, disableDateColor, rangeDateColor, holidayColor, todayColor;
    private float textSizeTitle, textSizeWeek, textSizeDate;
    //region SelectionMode -> Default = Range | Enum -> {Single(1),Range(2),None(3)}
    private DateRangeCalendarView.SelectionMode selectionMode = DateRangeCalendarView.SelectionMode.Range;
    private DatePickerDialog.OnSingleDateSelectedListener onSingleDateSelectedListener;
    private DatePickerDialog.OnRangeDateSelectedListener onRangeDateSelectedListener;
    private boolean disableDaysAgo = true;
    private PersianCalendar currentDate = new PersianCalendar();
    private PersianCalendar minDate;
    private PersianCalendar maxDate;
    private boolean enableTimePicker = false;
    private boolean showGregorianDate = false;

    public DatePickerDialog(Context context) {
        super(context);
        mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (getWindow() != null)
            getWindow().setGravity(Gravity.CENTER);

        this.typeface = FontUtils.Default(mContext);

        initView();

        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = this.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        PersianCalendar today = new PersianCalendar();
        setCurrentDate(today);
    }

    private void initView() {
        //region init View & Font
        setContentView(R.layout.dialog_date_picker);

        btn_Accept = findViewById(R.id.btn_Accept);
        tvDate1 = findViewById(R.id.tv_date);
        tvDate2 = findViewById(R.id.tv_date_return);
        rlReturn = findViewById(R.id.rl_return);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            acceptButtonColor = mContext.getColor(R.color.white);
        }
//        calendar = findViewById(R.id.calendar);
        //endregion
    }

    public void showDialog() {
        calendar = new DateRangeCalendarView(mContext);
        calendar.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onDateSelected(PersianCalendar _date) {
                persianDate = _date;
                tvDate1.setText(_date.getPersianWeekDayName() + " " + _date.getPersianDay() + " " + _date.getPersianMonthName());
            }

            @Override
            public void onDateRangeSelected(PersianCalendar _startDate, PersianCalendar _endDate) {
                persianStartDate = _startDate;
                persianEndDate = _endDate;
                tvDate1.setText(_startDate.getPersianWeekDayName() + " " + _startDate.getPersianDay() + " " + _startDate.getPersianMonthName());
                tvDate2.setText(_endDate.getPersianWeekDayName() + " " + _endDate.getPersianDay() + " " + _endDate.getPersianMonthName());
            }

            @Override
            public void onCancel() {

            }
        });

        btn_Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectionMode == DateRangeCalendarView.SelectionMode.Single) {
                    //region SelectionMode.Single
                    if (persianDate != null) {
                        if (onSingleDateSelectedListener != null) {
                            onSingleDateSelectedListener.onSingleDateSelected(persianDate, date, calendar.isShowCalendarMilady);
                        }

                        dismiss();
                    } else {
                        MyUtils.getInstance().Toast(mContext, "لطفا یک تاریخ انتخاب کنید");
                    }
                    //endregion
                } else if (selectionMode == DateRangeCalendarView.SelectionMode.Range) {
                    //region SelectionMode.Range
                    if (persianStartDate != null) {
                        if (persianEndDate != null) {
                            if (onRangeDateSelectedListener != null) {
                                onRangeDateSelectedListener.onRangeDateSelected(persianStartDate, persianEndDate, startDate, endDate, calendar.isShowCalendarMilady);
                            }

                            dismiss();
                        } else {
                            MyUtils.getInstance().Toast(mContext, "لطفا بازه زمانی را مشخص کنید");
                        }
                    } else {
                        MyUtils.getInstance().Toast(mContext, "لطفا بازه زمانی را مشخص کنید");
                    }
                    //endregion
                }
            }
        });

        //config
        calendar.setSelectionMode(selectionMode.getValue());
        calendar.setDisableDaysAgo(disableDaysAgo);
        calendar.setTypeface(typeface);
        calendar.setCurrentDate(currentDate);
        calendar.setMaxDate(maxDate);
        calendar.setMinDate(minDate);
        calendar.setShowGregorianDate(showGregorianDate);
        calendar.setShouldEnabledTime(enableTimePicker);

        //theme
        calendar.setHeaderBackgroundColor(headerBackgroundColor);
        calendar.setSelectedDateCircleColor(selectedDateCircleColor);
        calendar.setWeekColor(weekColor);
        calendar.setRangeStripColor(rangeStripColor);
        calendar.setSelectedDateColor(selectedDateColor);
        calendar.setDefaultDateColor(defaultDateColor);
        calendar.setDisableDateColor(disableDateColor);
        calendar.setRangeDateColor(rangeDateColor);
        calendar.setHolidayColor(holidayColor);
        calendar.setTodayColor(todayColor);
        calendar.setTextSizeTitle(textSizeTitle);
        calendar.setTextSizeWeek(textSizeWeek);
        calendar.setTextSizeDate(textSizeDate);

        //init
        calendar.setAttributes();
        calendar.build();

        ViewGroup insertPoint = findViewById(R.id.content);

        if (insertPoint.getChildCount() > 0) {
            insertPoint.removeAllViews();
        }

        insertPoint.addView(calendar);


        if (selectionMode.getValue() == DateRangeCalendarView.SelectionMode.None.getValue()) {
            btn_Accept.setVisibility(View.GONE);
            rlReturn.setVisibility(View.INVISIBLE);
        } else {
            rlReturn.setVisibility(View.VISIBLE);
        }

        this.show();
    }

    //region Properties
    //region DisableDaysAgo -> Default = True
    public boolean isDisableDaysAgo() {
        return disableDaysAgo;
    }

    public void setDisableDaysAgo(boolean disableDaysAgo) {
        this.disableDaysAgo = disableDaysAgo;
    }
    //endregion

    //region Typeface -> Default = IranYekan
    public void setTypeface(Typeface typeface) {
        if (typeface != null) {
            this.typeface = typeface;
        }
    }
    //endregion

    public DateRangeCalendarView.SelectionMode getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(DateRangeCalendarView.SelectionMode selectionMode) {
        if (selectionMode != null) {
            this.selectionMode = selectionMode;
        }
    }
    //endregion

    //region CurrentDate -> Default = Today
    public PersianCalendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(PersianCalendar currentDate) {
        this.currentDate = currentDate;
    }
    //endregion

    //region MinDate -> Default = Today
    public PersianCalendar getMinDate() {
        return minDate;
    }

    public void setMinDate(PersianCalendar minDate) {
        this.minDate = minDate;
//        calendar.setMinDate(minDate);
    }
    //endregion

    //region MaxDate -> Default = Today + 1 year
    public PersianCalendar getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(PersianCalendar maxDate) {
        this.maxDate = maxDate;
//        calendar.setMaxDate(maxDate);
    }
    //endregion

    //region showGregorianDate -> Default = false
    public boolean isShowGregorianDate() {
        return showGregorianDate;
    }

    public void setShowGregorianDate(boolean showGregorianDate) {
        this.showGregorianDate = showGregorianDate;
    }
    //endregion

    //region timePicker -> Default = false
    public boolean isEnableTimePicker() {
        return enableTimePicker;
    }

    public void setEnableTimePicker(boolean enableTimePicker) {
        this.enableTimePicker = enableTimePicker;
    }
    //endregion


    //region Listener -> OnSingleDateSelected & OnRangeDateSelected
    //region OnSingleDateSelected -> Getter/Setter
    public DatePickerDialog.OnSingleDateSelectedListener getOnSingleDateSelectedListener() {
        return onSingleDateSelectedListener;
    }

    public void setOnSingleDateSelectedListener(DatePickerDialog.OnSingleDateSelectedListener onSingleDateSelectedListener) {
        this.onSingleDateSelectedListener = onSingleDateSelectedListener;
    }
    //endregion

    //region OnRangeDateSelected -> Getter/Setter
    public DatePickerDialog.OnRangeDateSelectedListener getOnRangeDateSelectedListener() {
        return onRangeDateSelectedListener;
    }

    public void setOnRangeDateSelectedListener(DatePickerDialog.OnRangeDateSelectedListener onRangeDateSelectedListener) {
        this.onRangeDateSelectedListener = onRangeDateSelectedListener;
    }
    //endregion
    //endregion

    public int getAcceptButtonColor() {
        return acceptButtonColor;
    }

    public void setAcceptButtonColor(int acceptButtonColor) {
        this.acceptButtonColor = acceptButtonColor;
    }

    public int getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public void setHeaderBackgroundColor(int headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor;
    }

    public int getWeekColor() {
        return weekColor;
    }

    public void setWeekColor(int weekColor) {
        this.weekColor = weekColor;
    }

    public int getRangeStripColor() {
        return rangeStripColor;
    }

    public void setRangeStripColor(int rangeStripColor) {
        this.rangeStripColor = rangeStripColor;
    }

    public int getSelectedDateCircleColor() {
        return selectedDateCircleColor;
    }

    public void setSelectedDateCircleColor(int selectedDateCircleColor) {
        this.selectedDateCircleColor = selectedDateCircleColor;
    }

    public int getSelectedDateColor() {
        return selectedDateColor;
    }

    public void setSelectedDateColor(int selectedDateColor) {
        this.selectedDateColor = selectedDateColor;
    }

    public int getDefaultDateColor() {
        return defaultDateColor;
    }

    public void setDefaultDateColor(int defaultDateColor) {
        this.defaultDateColor = defaultDateColor;
    }

    public int getDisableDateColor() {
        return disableDateColor;
    }

    public void setDisableDateColor(int disableDateColor) {
        this.disableDateColor = disableDateColor;
    }

    public int getRangeDateColor() {
        return rangeDateColor;
    }

    public void setRangeDateColor(int rangeDateColor) {
        this.rangeDateColor = rangeDateColor;
    }

    public int getHolidayColor() {
        return holidayColor;
    }

    public void setHolidayColor(int holidayColor) {
        this.holidayColor = holidayColor;
    }

    public int getTodayColor() {
        return todayColor;
    }

    public void setTodayColor(int todayColor) {
        this.todayColor = todayColor;
    }

    public float getTextSizeTitle() {
        return textSizeTitle;
    }

    public void setTextSizeTitle(float textSizeTitle) {
        this.textSizeTitle = textSizeTitle;
    }

    public float getTextSizeWeek() {
        return textSizeWeek;
    }

    public void setTextSizeWeek(float textSizeWeek) {
        this.textSizeWeek = textSizeWeek;
    }

    public float getTextSizeDate() {
        return textSizeDate;
    }

    public void setTextSizeDate(float textSizeDate) {
        this.textSizeDate = textSizeDate;
    }

    //endregion

    //endregion

    //region Listeners -> Interface
    public interface OnSingleDateSelectedListener {
        void onSingleDateSelected(PersianCalendar persianCalendar, Date date, boolean isSelectedMiladyDate);
    }

    public interface OnRangeDateSelectedListener {
        void onRangeDateSelected(PersianCalendar startPersianDate, PersianCalendar endPersianDate, Date startDate, Date endDate, boolean isSelectedMiladyDate);
    }

//    public interface OnMultipleDateSelectedListener {
//        void onMultipleDateSelected(ArrayList<PersianCalendar> startDate);
//    }
    //endregion
}
