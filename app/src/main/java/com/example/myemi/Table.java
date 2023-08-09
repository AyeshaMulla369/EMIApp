package com.example.myemi;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Table extends AppCompatActivity {

    private long sid;
    private float p ;
    private float r;
    private float n;
    private double e;
    private String name;
    DBhelper dBhelper;
    MyCalender calender;

    private long notid1;

    TableLayout tableLayout ;
    int rowSize;
    TableRow[] rows;
    TextView[] dates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        dBhelper = new DBhelper(this);
        sid = getIntent().getLongExtra("sid" , -1);
        p = dBhelper.getPKey(sid);
        n = dBhelper.getNKey(sid);
        r = dBhelper.getRKey(sid);

        tableLayout = findViewById(R.id.tableLayout);
        rowSize = Math.round(n) +1;
        rows = new TableRow[rowSize];
        dates = new TextView[rowSize];

        e=p*r*(Math.pow((1+r),n))/(Math.pow((1+r),n)-1);
        calender = new MyCalender();
        showTable();
        if(dBhelper.getNotId(sid) != 0)
            showDateTable();
    }

    private void showTable() {
        TableLayout tableLayout = findViewById(R.id.tableLayout);

        Log.d("SheetActivity", "showTable: Inside the new page");

        //row setup
        TextView[] srn = new TextView[rowSize];

        TextView[] principle = new TextView[rowSize];
        TextView[] payment = new TextView[rowSize];
        TextView[] interest = new TextView[rowSize];
        TextView[] prepayment = new TextView[rowSize];
        TextView[] balance = new TextView[rowSize];

        for (int i=0;i< rowSize ;i++)
        {
            srn[i] = new TextView(this);
            dates[i] = new TextView(this);
            principle[i] = new TextView(this);
            payment[i] = new TextView(this);
            interest[i] = new TextView(this);
            prepayment[i] = new TextView(this);
            balance[i] = new TextView(this);
        }

        srn[0].setText("Period");
        srn[0].setTypeface(srn[0].getTypeface() , Typeface.BOLD);
        dates[0].setText("Dates");
        dates[0].setTypeface(dates[0].getTypeface() , Typeface.BOLD);
        principle[0].setText("Principle");
        principle[0].setTypeface(srn[0].getTypeface() , Typeface.BOLD);
        payment[0].setText("Payment");
        payment[0].setTypeface(payment[0].getTypeface() , Typeface.BOLD);
        interest[0].setText("Interest");
        interest[0].setTypeface(srn[0].getTypeface() , Typeface.BOLD);
        prepayment[0].setText("Principle Repayment");
        prepayment[0].setTypeface(srn[0].getTypeface() , Typeface.BOLD);
        balance[0].setText("Balance");
        balance[0].setTypeface(payment[0].getTypeface() , Typeface.BOLD);


        double a ,b , c,d,f;
        a = p;
        b = e;
        c =p * r;
        d = b - c;
        f = a - d;
        for (int i=1;i< rowSize ;i++)
        {

            srn[i].setText(String.valueOf(i));
            principle[i].setText(String.valueOf(a));
            payment[i].setText(String.valueOf(b));
            interest[i].setText(String.valueOf(c));
            prepayment[i].setText(String.valueOf(d));
            if(f <=0) f=0;
            balance[i].setText(String.valueOf(f));

            a =f;
            c = a * r;
            d = b - c;
            f = a-d;
        }



        for (int i = 0; i <rowSize ; i++) {

            rows[i] = new TableRow(this);
            if(i%2 == 0)
                rows[i].setBackgroundColor(Color.parseColor("#EEEEEE"));
            else
                rows[i].setBackgroundColor(Color.parseColor("#E4E4E4"));

            srn[i].setPadding(16,16,16,16);
            dates[i].setPadding(16,16,16,16);
            principle[i].setPadding(16,16,16,16);
            payment[i].setPadding(16,16,16,16);
            interest[i].setPadding(16,16,16,16);
            prepayment[i].setPadding(16,16,16,16);
            balance[i].setPadding(16,16,16,16);
            rows[i].addView(srn[i]);
            rows[i].addView(dates[i]);
            rows[i].addView(principle[i]);
            rows[i].addView(payment[i]);
            rows[i].addView(interest[i]);
            rows[i].addView(prepayment[i]);
            rows[i].addView(balance[i]);

            tableLayout.addView(rows[i]);
        }

        tableLayout.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.noti, menu);

        if(dBhelper.getNotId(sid) != 0) {
            menu.findItem(R.id.notify).setEnabled(false);
        }else {
            menu.findItem(R.id.notify).setEnabled(true);
        }
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.notify:
                showCalender();
                item.setEnabled(false);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showDateTable() {
        long currnotid = dBhelper.getNotId(sid);
        String date ;
        for (int i=1;i< rowSize ;i++) {

            date = dBhelper.getDates(currnotid , sid);
            dates[i].setText(String.valueOf(date));
            currnotid+=1;
        }
    }

    private void showCalender() {
        calender.show(getSupportFragmentManager(), "");
        calender.setOnCalenderClickListener(this::onCalenderOkClickListener);
    }

    private void onCalenderOkClickListener(int year, int month, int day) {
        calender.setDate(year, month, day);
        notid1 = dBhelper.addNotification(sid, calender.getDate());
        int currMonth = month+1;
        int currYear = year;
        for(int i = 1;i< n;i++)
        {
            if(month >=12) {
                currMonth =1;
                currYear+=1;
            }
            calender.setDate(currYear , currMonth++,day);
            notid1 = dBhelper.addNotification(sid, calender.getDate());
        }

    }



}