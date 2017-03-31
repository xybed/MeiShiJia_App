package lib.utils;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import lib.widget.MyDatePickDialog;

public class DatePickUtil {

    /**
     * 自定义天数
     *
     * @param arg
     * @return
     */
    public static String getDays(int arg) {
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, arg);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String towDaysBefore = sdf.format(cal1.getTime());
        return towDaysBefore;
    }

    /**
     * 两天前
     *
     * @return
     */
    public static String getTwoDaysBefore() {
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String towDaysBefore = sdf.format(cal1.getTime());
        return towDaysBefore;
    }

    //判断是周末
    public static boolean isWeekend(Calendar cal){
        return cal.get(Calendar.DAY_OF_WEEK)== Calendar.SATURDAY||cal.get(Calendar.DAY_OF_WEEK)== Calendar.SUNDAY;
    }
    /**
     * 一天后
     *
     * @return
     */
    public static String getOneDaysAfter() {
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String oneDaysAfter = sdf.format(cal1.getTime());
        return oneDaysAfter;
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getMondayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0)
            dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    @SuppressLint("SimpleDateFormat")
    public static String getSundayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int dayofweek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayofweek == 0)
            dayofweek = 7;
        c.add(Calendar.DATE, -dayofweek + 7);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(c.getTime());
    }

    @SuppressLint("SimpleDateFormat")
    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                System.out.println("dt1 在dt2前");
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                System.out.println("dt1在dt2后");
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 设置为今天的日期
     *
     * @param viewDate
     */
    public static void setToDayDate(TextView viewDate) {
        Calendar currDate = Calendar.getInstance();
        currDate.clear();
        currDate.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String headDate = df.format(new Date(currDate.getTimeInMillis()));
        viewDate.setText(headDate);
    }

    /**
     * 设置为今天的日期
     *
     * @param
     */
    public static String getToDayDate() {
        Calendar currDate = Calendar.getInstance();
        currDate.clear();
        currDate.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date(currDate.getTimeInMillis()));
    }

    /**
     * 设置今后的第几天的时间
     *
     * @param viewDate
     */
    public static String setDateAfter(TextView viewDate, int dayNum) {
        Calendar currDate = Calendar.getInstance();
        currDate.clear();
        currDate.setTimeInMillis(System.currentTimeMillis());
        currDate.add(Calendar.DAY_OF_MONTH, dayNum);//加dayNum天
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String headDate = df.format(new Date(currDate.getTimeInMillis()));
        viewDate.setText(headDate);
        return headDate;
    }

    /**
     * 日期控件
     *
     * @param mContext
     * @param curEdit         当前日期显示
     * @param dateSetListener 日期选择监听器
     */
    public static void datePicker(final Context mContext, final TextView curEdit, DatePickerDialog.OnDateSetListener dateSetListener) {

        String curDate = curEdit.getText().toString().trim();

        int year;
        int month;
        int day;

        if ("".equals(curDate)) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        } else {
            try {
                String[] dateStrs = curDate.split("-");
                year = Integer.parseInt(dateStrs[0]);
                month = Integer.parseInt(dateStrs[1]) - 1;
                day = Integer.parseInt(dateStrs[2]);
            } catch (Exception e) {
                // TODO: handle exception
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            }
        }

        MyDatePickDialog pickerDialog = new MyDatePickDialog(
                mContext, dateSetListener, year, month, day);
        pickerDialog.show();
    }

    /**
     * 日期控件
     * 只能选择 过去时间，操作今天时间为不合法时间
     * 用在：生日选择，治疗时间选择
     *
     * @param mContext
     * @param curEdit
     */
    public static void datePicker(final Context mContext, final TextView curEdit) {

        String curDate = curEdit.getText().toString().trim();

        int year;
        int month;
        int day;

        if ("".equals(curDate)) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            day = cal.get(Calendar.DAY_OF_MONTH);
        } else {
            try {
                String[] dateStrs = curDate.split("-");
                year = Integer.parseInt(dateStrs[0]);
                month = Integer.parseInt(dateStrs[1]) - 1;
                day = Integer.parseInt(dateStrs[2]);
            } catch (Exception e) {
                // TODO: handle exception
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());
                year = cal.get(Calendar.YEAR);
                month = cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
            }
        }

        DatePickerDialog pickerDialog = new DatePickerDialog(
                mContext, new OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                String month = monthOfYear + 1 + "";
                String day = dayOfMonth + "";
                if (monthOfYear + 1 < 10)
                    month = "0" + month;
                if (dayOfMonth < 10)
                    day = "0" + day;
                if (dateCompare(year, monthOfYear, dayOfMonth))
                    curEdit.setText(year + "-" + month + "-" + day);
                else {
                    ToastUtil.show("您选择的日期不合法");
                }
            }


        }, year, month, day);
        pickerDialog.show();
    }

    /**
     * 时间控件
     *
     * @param mContext
     * @param curEdit
     */
    public static void timePicker(Context mContext, final TextView curEdit) {

        String curTime = curEdit.getText().toString().trim();

        int hour;
        int minute;

        if ("".equals(curTime)) {
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());

            hour = cal.get(Calendar.HOUR_OF_DAY);
            minute = cal.get(Calendar.MINUTE);
        } else {
            try {
                String[] dateStrs = curTime.split(":");
                hour = Integer.parseInt(dateStrs[0]);
                minute = Integer.parseInt(dateStrs[1]);
            } catch (Exception e) {
                // TODO: handle exception
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(System.currentTimeMillis());

                hour = cal.get(Calendar.HOUR_OF_DAY);
                minute = cal.get(Calendar.MINUTE);
            }
        }

        TimePickerDialog pickerDialog = new TimePickerDialog(mContext, new OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker timePicker, int arg1, int arg2) {
                String month = arg1 + "";
                String day = arg2 + "";
                if (arg1 < 10)
                    month = "0" + month;
                if (arg2 < 10)
                    day = "0" + day;
                curEdit.setText(month + ":" + day + ":00");
            }
        }, hour, minute, true);
        pickerDialog.show();
    }

    /**
     * 日期比较
     * 判断日期是否大于当前的日期
     *
     * @param year
     * @param month
     * @param day
     * @return false代表是
     */
    public static boolean dateCompare(int year, int month, int day) {
        //判断日期是否大于当前的日期
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int current_year = cal.get(Calendar.YEAR);
        int current_month = cal.get(Calendar.MONTH);
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        if (year <= current_year) {
            if (year == current_year) {
                if (month <= current_month) {
                    if (month == current_month) {
                        if (day > current_day)
                            return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 比较出生日期是否大于17岁
     * @param year
     * @param month
     * @param day
     * @return false代表不合法（大于17岁）
     */
    public static boolean dateCompareWith17(int year, int month, int day){
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int current_year = cal.get(Calendar.YEAR);
        int current_month = cal.get(Calendar.MONTH);
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        if(year >= (current_year - 17)){
            if(year == (current_year - 17)){
                if(month >= current_month){
                    if(month == current_month){
                        if(day >= current_day){
                            return true;
                        }
                    }else{
                        return true;
                    }
                }
            }else{
                return true;
            }

        }
        return false;
    }

    /**
     * 判断日期是否是今天
     *
     * @param year
     * @param month
     * @param day
     * @return true 代表是
     */
    public static boolean isToday(int year, int month, int day) {
        //判断日期是否大于当前的日期
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        int current_year = cal.get(Calendar.YEAR);
        int current_month = cal.get(Calendar.MONTH);
        int current_day = cal.get(Calendar.DAY_OF_MONTH);
        return year == current_year && month == current_month && day == current_day;
    }

    /**
     *
     * 比较两个时间大小
     * @param date1
     * @param date2
     * @return   true：date1 <= date2   false:date1 > date2
     */
    public static boolean compare(Calendar date1, Calendar date2){
        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();
        return diff >= 0;
    }
}
