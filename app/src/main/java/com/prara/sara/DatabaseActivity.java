package com.prara.sara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DatabaseActivity extends AppCompatActivity {

    ListView lv;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);

        db = openOrCreateDatabase("MINI_DB", Context.MODE_PRIVATE,null);
        ArrayList<String> dblist = new ArrayList<>();
        Cursor c = db.rawQuery("select * from speech",null);
        //c.moveToNext();
        while(c.moveToNext()){
            dblist.add(c.getString(0)+" "+c.getString(1));
            //Toast.makeText(getBaseContext(),c.getString(0)+c.getString(1),Toast.LENGTH_SHORT).show();
        }
        lv = (ListView) findViewById(R.id.lvdb);
        registerForContextMenu(lv);

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,dblist);
        lv.setAdapter(adapter);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }
}
