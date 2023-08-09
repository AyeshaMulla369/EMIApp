package com.example.myemi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MyCalender extends DialogFragment {
    Calendar calendar = Calendar.getInstance();

    public interface OnCalenderOkClickListener{
        void onClick(int year , int month , int day);
    }
    public OnCalenderOkClickListener onCalenderOkClickListener;

    public void setOnCalenderClickListener(OnCalenderOkClickListener onCalenderOkClickListener){
        this.onCalenderOkClickListener = onCalenderOkClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(),((view, year, month, dayOfMonth) ->{
            onCalenderOkClickListener.onClick(year , month , dayOfMonth);
        }),calendar.get(Calendar.YEAR) ,calendar.get(Calendar.MONTH) ,calendar.get(Calendar.DAY_OF_MONTH)  );
    }

    void setDate(int year , int month , int day){

        calendar.set(Calendar.YEAR , year);
        calendar.set(Calendar.MONTH , month);
        calendar.set(Calendar.DAY_OF_MONTH , day);
        calendar.set(Calendar.HOUR_OF_DAY, 9); // Set the desired hour
        calendar.set(Calendar.MINUTE, 0); // Set the desired minute
        calendar.set(Calendar.SECOND, 0);

    }

    String getDate(){
        return DateFormat.format("dd.MM.yyyy", calendar).toString();
    }
}

