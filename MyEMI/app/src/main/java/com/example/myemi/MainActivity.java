package com.example.myemi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    float p , t ,r;
    DBhelper dBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final EditText P=(EditText)findViewById(com.example.myemi.R.id.principle);
        final EditText R=(EditText)findViewById(com.example.myemi.R.id.rate);
        final EditText N=(EditText)findViewById(com.example.myemi.R.id.tenure);

        final EditText emi=(EditText)findViewById(com.example.myemi.R.id.editText5);
        dBHelper = new DBhelper(this);
        p = 0;
        t = 0;
        r = 0;


        emi.setVisibility(View.GONE);
        Button emibtn=(Button)findViewById(com.example.myemi.R.id.button);
        emibtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String loan=P.getText().toString();
                String rate=R.getText().toString();
                String tenure=N.getText().toString();
                if(TextUtils.isEmpty(loan))
                {
                    P.setError("Enter loan amount");
                    return;
                }
                if(TextUtils.isEmpty(rate))
                {
                    R.setError("Enter rate");
                    return;
                }
                if(TextUtils.isEmpty(tenure))
                {
                    N.setError("Enter the loan tenure");
                    return;
                }
                p=Float.parseFloat(loan);
                r=Float.parseFloat(rate)/1200;
                t=Float.parseFloat(tenure);

                double e=p*r*(Math.pow((1+r),t))/(Math.pow((1+r),t)-1);
                emi.setVisibility(View.VISIBLE);
                emi.setText(String.valueOf(e));


            }
        });

        Button reset=(Button)findViewById(com.example.myemi.R.id.button2);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                P.setText("");
                R.setText("");
                N.setText("");
                emi.setText("");
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.emiusermenu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.history:
                historyShow();
                return true;
            case R.id.save:
                saving();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saving() {
        Intent it = new Intent(this , History.class);

            it.putExtra("P",p);
            it.putExtra("N",t);
            it.putExtra("R",r);

        startActivity(it);
    }

    private void historyShow() {

        Intent it = new Intent(this , History.class);
        it.putExtra("P",0);
        it.putExtra("N",0);
        it.putExtra("R",0);
        startActivity(it);

    }

}