package com.jiufang.fatherdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Date date = null; // 指定日期
    String date_cur;
    long millionSeconds = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatePicker datePicker = findViewById(R.id.tv_old_day);
        TextView tv_newday = findViewById(R.id.tv_newday);
        EditText editText = findViewById(R.id.tv_daycount);
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        Button button = findViewById(R.id.btn_cal);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(MainActivity.this);
                if (editText.getText().length() > 0) {
                    int days = Integer.parseInt(editText.getText().toString());
                    tv_newday.setText(init(days));

                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        millionSeconds = sdf.parse(datePicker.getYear() + "-" + (datePicker.getMonth() + 1) + "-" + datePicker.getDayOfMonth()).getTime();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private String init(int day) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日"); // 日期格式
        try {
            date = dateFormat.parse(dateFormat.format(new Date(millionSeconds)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date newDate = addDate(date, day); // 指定日期加上20天
        return dateFormat.format(newDate);
    }
    // 注意day 必须是long类型 否者会超精度影响结果

    public static Date addDate(Date date, long day) {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }


    public static void hideKeyboard(Activity activtiy) {
        View view = activtiy.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activtiy.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}