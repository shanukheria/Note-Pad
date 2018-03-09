package com.example.android.notepad;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> notes;
    static ArrayAdapter arrayAdapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         super.onOptionsItemSelected(item);
         if(item.getItemId()==R.id.add){
             Intent intent=new Intent(getApplicationContext(),Edit.class);
             startActivity(intent);

         }

return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.android.notepad", Context.MODE_PRIVATE);
        HashSet<String> set=(HashSet<String>) sharedPreferences.getStringSet("notes",null);
        if(set==null){
            notes.add("Example note");
        }else{
            notes=new ArrayList<String>(set);
        }


        setContentView(R.layout.activity_main);
        final ListView listView=(ListView) findViewById(R.id.list);

       arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,notes);
    listView.setAdapter(arrayAdapter);
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getApplicationContext(),Edit.class);
    intent.putExtra("NoteId",position);
startActivity(intent);


    }
});
listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
     new AlertDialog.Builder(MainActivity.this)
             .setIcon(android.R.drawable.ic_dialog_alert)
             .setTitle("Delete")
             .setMessage("Do you want to confirm delete")
             .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                 @Override
                 public void onClick(DialogInterface dialog, int which) {
                     notes.remove(position);
                     arrayAdapter.notifyDataSetChanged();
                     SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.example.android.notepad", Context.MODE_PRIVATE);
                     HashSet<String> set=new HashSet<>(MainActivity.notes);
                     sharedPreferences.edit().putStringSet("notes",set).apply();
                 }
             }).setNegativeButton("No",null).show();



        return true;
    }
});
    }

}
