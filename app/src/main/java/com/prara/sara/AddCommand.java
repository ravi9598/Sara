package com.prara.sara;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCommand extends AppCompatActivity {

    EditText edkey,edreply;
    SQLiteDatabase db;
    Button b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_command);

        db = openOrCreateDatabase("MINI_DB", Context.MODE_PRIVATE,null);
        edkey = (EditText) findViewById(R.id.editText);
        edreply = (EditText) findViewById(R.id.editText2);
        b = (Button) findViewById(R.id.add);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keystr = edkey.getText().toString().trim();
                String reply = edreply.getText().toString().trim();
                // Toast.makeText(getBaseContext(),key+" "+reply,Toast.LENGTH_SHORT).show();
                //if(keystr!="" && reply!="")
                try{
                    db.execSQL("insert into speech values('"+keystr+"','"+reply+"')");
                    Toast.makeText(getBaseContext(),"SUCCESS",Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } catch (Exception e){
                    Toast.makeText(AddCommand.this,"Command not added", Toast.LENGTH_SHORT).show();
                }
                //else Toast.makeText(AddCommand.this,"Fields can't be left empty",Toast.LENGTH_SHORT).show();
            }
        });

    }


}
