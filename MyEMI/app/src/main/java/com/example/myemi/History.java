package com.example.myemi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<EmiItems> emiItems = new ArrayList<>();
    private DBhelper dbHelper;

    int view;
    Float p,n,r;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        dbHelper = new DBhelper(this);
        loadData();

        Intent it = getIntent();
            p  = it.getFloatExtra("P", -1);
            n = it.getFloatExtra("N",-1);
            r = it.getFloatExtra("R",-1);

        if(p> 0 && r>0 && n> 0)
        {
            Log.d("Page info", "P = "+p+" n= "+n+" r="+r);
            addDialog(p,n,r);
        }


        recyclerView = findViewById(R.id.student_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListAdapter(this , emiItems);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(position-> gotoItemActivity(position));
    }

    private void addDialog(float p , float n , float r) {
        Log.d("Dialog info", "Create a dialog ");
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager(),MyDialog.ADD_DIALOG);
        dialog.setListener((name )->{
            addClass(name,p,n,r);
        });
    }

    private void addClass(String name , float p , float n , float r) {
        long sid = dbHelper.addClass(p,n,r,name);
        EmiItems emiItem = new EmiItems(sid, name , p,n,r );
        emiItems.add(emiItem);
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "saved", Toast.LENGTH_SHORT).show();
    }

    private void gotoItemActivity(int position) {
        Intent i = new Intent(this , Table.class);
        i.putExtra("sid",emiItems.get(position).getSid());
        startActivity(i);
    }

    private void loadData() {

        Cursor cursor = dbHelper.getClassTable();
        emiItems.clear();
        while(cursor.moveToNext())
        {
            long sid = cursor.getLong(cursor.getColumnIndexOrThrow(DBhelper.STATUS_ID));
            String name = cursor.getString(cursor.getColumnIndexOrThrow(DBhelper.STATUS_NAME_KEY));
            float p = cursor.getFloat(cursor.getColumnIndexOrThrow(DBhelper.P_KEY));
            float n = cursor.getFloat(cursor.getColumnIndexOrThrow(DBhelper.N_KEY));
            float r = cursor.getFloat(cursor.getColumnIndexOrThrow(DBhelper.R_KEY));

            emiItems.add(new EmiItems(sid , name ,p,n,r));
        }
        cursor.close();
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case 0:
                showUpdateDialog(item.getGroupId());
                break;
            case 1:
                deleteClass(item.getGroupId());
        }
        return super.onContextItemSelected(item);
    }

    private void showUpdateDialog(int position) {
        MyDialog dialog = new MyDialog();
        dialog.show(getSupportFragmentManager() , MyDialog.UPDATE_DIALOG);
        dialog.setListener((name)->{
            updateTheClass(position , name);
        });
    }

    private void updateTheClass(int position, String name) {
        dbHelper.updateClass(emiItems.get(position).getSid() , name);
        emiItems.get(position).setName(name);
        adapter.notifyItemChanged(position);
    }
    private void deleteClass(int position) {
        dbHelper.deleteClass(emiItems.get(position).getSid());
        emiItems.remove(position);
        adapter.notifyItemRemoved(position);
    }


}