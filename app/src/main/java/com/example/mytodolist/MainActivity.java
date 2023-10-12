package com.example.mytodolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listview;
    Button addbtn;
    EditText edittask;
    ArrayList<String> arrtask=new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview=findViewById(R.id.list);
        addbtn=findViewById(R.id.addbtn);
        edittask=findViewById(R.id.addtask);

        arrtask=filehelper.readdata(this);

        adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1,arrtask);
listview.setAdapter(adapter);
addbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String taskname=edittask.getText().toString();
        arrtask.add(taskname);
        edittask.setText("");
        filehelper.writedata(arrtask,getApplicationContext());
        adapter.notifyDataSetChanged();



    }
});
listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);

        builder.setTitle("Delete");
        builder.setMessage("Are you Sure you want to delete this task?");
        builder.setCancelable(false);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               arrtask.remove(position);
               adapter.notifyDataSetChanged();
               filehelper.writedata(arrtask,getApplicationContext());
               dialog.cancel();
            }
        });
        AlertDialog alertDialog =builder.create();
        alertDialog.show();


    }
});
    }
}