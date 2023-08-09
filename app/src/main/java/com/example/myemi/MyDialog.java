package com.example.myemi;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class MyDialog extends DialogFragment {

    public static final String ADD_DIALOG = "addStudent";
    public static final String UPDATE_DIALOG = "updateStudent";

    OnClickListener listener;
    private String name;

    public MyDialog(String name) {

        this.name = name;
    }

    public MyDialog() {

    }

    public interface OnClickListener{
        void onClick(String text1);
    }

    public void setListener(OnClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = null;
        if(getTag().equals(ADD_DIALOG)) dialog = getAddClassDialog();
        if(getTag().equals(UPDATE_DIALOG)) dialog = getUpdatedClassDialog();


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        return dialog;
    }

    private Dialog getUpdatedClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);

        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Uodate Loan Name");


        EditText name_edit = view.findViewById(R.id.edt01);


        name_edit.setHint("EMI Loan Name");

        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);
        add.setText("Updated");

        name_edit.setText(name);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String name = name_edit.getText().toString();
            listener.onClick(name);

        });

        return builder.create();

    }


    private Dialog getAddClassDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog, null);

        builder.setView(view);

        TextView title = view.findViewById(R.id.titleDialog);
        title.setText("Add new Loan Name");


        EditText name_edit = view.findViewById(R.id.edt01);


        name_edit.setHint("EMI Loan Name");

        Button cancel = view.findViewById(R.id.cancel_btn);
        Button add = view.findViewById(R.id.add_btn);

        cancel.setOnClickListener(v -> dismiss());
        add.setOnClickListener(v -> {
            String name = name_edit.getText().toString();
            listener.onClick( name);
        });

        return builder.create();

    }

}
