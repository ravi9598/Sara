package com.prara.sara;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;
    TextView tv;
    TextToSpeech tts;
    Button b;
    static String temp="";
    static String temp2="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        b = (Button) findViewById(R.id.db);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DatabaseActivity.class));
            }
        });
        database = openOrCreateDatabase("MINI_DB", Context.MODE_PRIVATE, null);
        tv = (TextView) findViewById(R.id.textView_reply);

        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                tts.setLanguage(Locale.getDefault());
                //tts.speak("Hey! I'm MINI. Nice to meet you.",TextToSpeech.QUEUE_FLUSH,null);
                //wait_prompt(8,100);
            }
        });

        try{
            database.execSQL("create table speech(key varchar(20),answer varchar(20))");
            Toast.makeText(this,"SUCCESS", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            //db.execSQL("insert into speech values('hey','Hey! How you doin?')");
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeech(100);
            }
        });

        FloatingActionButton fab2 = findViewById(R.id.floatingActionButton);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddCommand.class));
            }
        });
    }

    public void promptSpeech(int reqCode){
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something!");

        try{
            startActivityForResult(intent,reqCode);
        }
        catch(ActivityNotFoundException a){
            Toast.makeText(this,"Your Phone Doesn't have Speech Recognizer",Toast.LENGTH_LONG).show();
        }

    }

    public void wait_prompt(int time, final int reqCode){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // this code will be executed after time seconds
                promptSpeech(reqCode);
            }
        }, time*1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);



        switch (requestCode){
            case 100 : if(resultCode==RESULT_OK && intent!=null) {
                ArrayList<String> result = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String check = result.get(0).toLowerCase();
                if (check.contains("class") || check.contains("glass")) {
                    Calendar calendar = Calendar.getInstance();
                    int day = calendar.get(Calendar.DAY_OF_WEEK);
                    if (check.contains("tomorrow")) day+=1;

                    switch (day){
                        case 1 : {
                            tts.speak("It's Sunday. You don't have any class. Have fun!",TextToSpeech.QUEUE_FLUSH,null);
                            tv.setText("It's Sunday. You don't have any class. Have fun!");
                            wait_prompt(3,100);
                        } break;

                        case 2 : {
                            tts.speak("It's Monday. You have \n1:00 - OOPS\n2:00 - Computer Networks\n3:00 - Computer Graphics\n4:00 - Data Mining",TextToSpeech.QUEUE_FLUSH,null);
                            tv.setText("It's Monday. You have \n1:00 - OOPS\n2:00 - Computer Networks\n3:00 - Computer Graphics\n4:00 - Data Mining");
                            wait_prompt(10,100);
                        } break;

                        case 3 : {
                            tts.speak("It's Tuesday. You have \n8:00 - Operating System\n9:00 - Data Mining\n1:00 - OOPS Lab\n3:00 - Computer Graphics\n4:00 - OOPS",TextToSpeech.QUEUE_FLUSH,null);
                            tv.setText("It's Tuesday. You have \n8:00 - Operating System\n9:00 - Data Mining\n1:00 - OOPS Lab\n3:00 - Computer Graphics\n4:00 - OOPS");
                            wait_prompt(10,100);
                        } break;

                        case 4 : {
                            tts.speak("It's Wednesday. You have \n8:00 - Operating System\n9:00 - OOPS\n11:00 - Operating System Lab\n3:00 - Computer Graphics\n4:00 - Computer Networks",TextToSpeech.QUEUE_FLUSH,null);
                            tv.setText("It's Wednesday. You have \n8:00 - Operating System\n9:00 - OOPS\n11:00 - Operating System Lab\n3:00 - Computer Graphics\n4:00 - Computer Networks");
                            wait_prompt(10,100);
                        } break;

                        case 5 : {
                            tts.speak("It's Thursday. You have \n8:00 - Computer Networks\n9:00 - Operating System\n10:00 - Data Mining\n11:00 - Computer Graphics\n1:00 - Computer Graphics Lab",TextToSpeech.QUEUE_FLUSH,null);
                            tv.setText("It's Thursday. You have \n8:00 - Computer Networks\n9:00 - Operating System\n10:00 - Data Mining\n11:00 - Computer Graphics\n1:00 - Computer Graphics Lab");
                            wait_prompt(10,100);
                        } break;

                        case 6 : {
                            tts.speak("It's Friday. You have \n8:00 - Data Mining\n9:00 - OOPS\n10:00 - Operating System\n11:00 - Computer Networks",TextToSpeech.QUEUE_FLUSH,null);
                            tv.setText("It's Friday. You have \n8:00 - Data Mining\n9:00 - OOPS\n10:00 - Operating System\n11:00 - Computer Networks");
                            wait_prompt(10,100);
                        } break;

                        case 7 : {
                            tts.speak("It's Saturday. You don't have any class. Have fun!",TextToSpeech.QUEUE_FLUSH,null);
                            tv.setText("It's Saturday. You don't have any class. Have fun!");
                            wait_prompt(3,100);
                        } break;
                    }
                } else {
                    try {
                        Cursor c = database.rawQuery("select answer from speech where key = '" + check + "'", null);
                        c.moveToNext();
                        String reply = c.getString(0);
                        tts.speak(reply, TextToSpeech.QUEUE_FLUSH, null, null);

                        tv.setText(reply);
                        wait_prompt(2, 100);
                    } catch (Exception e) {
                        tts.speak("I don't know. Say yes to add.", TextToSpeech.QUEUE_FLUSH, null, null);

                        temp = check;
                        tv.setText(temp + "\nI don't know. Say yes to add.");
                        //Toast.makeText(this,temp,Toast.LENGTH_SHORT).show();
                        wait_prompt(2, 101);
                    }
                }
            }
                break;

            case 101: if(resultCode==RESULT_OK && intent!=null){
                ArrayList<String> result101=intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String check101=result101.get(0).toLowerCase();
                if(check101.contains("ye")){

                    tts.speak("Okay. Go on let me hear that.",TextToSpeech.QUEUE_FLUSH,null,null);
                    tv.setText("Okay. Go on let me hear that.");
                    wait_prompt(2,102);
                }
                else{
                    tts.speak("Okay, I'll ask someone else.",TextToSpeech.QUEUE_FLUSH,null,null);
                    tv.setText("Okay. I'll ask someone else.");
                    wait_prompt(2,100);
                }

            }
                break;
            case 102:if(resultCode==RESULT_OK && intent!=null){
                //Toast.makeText(this,"temp : "+temp,Toast.LENGTH_SHORT).show();
                ArrayList<String> result102=intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String check102=result102.get(0).toLowerCase();
                temp2=check102;
                tv.setText(check102+"\nAre you sure?");
                tts.speak(check102+".Are you sure?",TextToSpeech.QUEUE_FLUSH,null,null);
                wait_prompt(4,103);
            }
                break;

            case 103 : if(resultCode==RESULT_OK && intent!=null) {
                //Toast.makeText(this,"temp : "+temp,Toast.LENGTH_SHORT).show();
                ArrayList<String> result103 = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String check102 = result103.get(0).toLowerCase();
                if(check102.contains("ye")){
                    try{
                        database.execSQL("insert into speech values('"+temp+"','"+temp2+"')");
                        tts.speak("Thank you",TextToSpeech.QUEUE_FLUSH,null,null);
                        tv.setText("Thank You!");
                        wait_prompt(2,100);
                        //Toast.makeText(this,temp+"\n"+check102,Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        tv.setText("Failed!");
                        tts.speak("Failed.",TextToSpeech.QUEUE_FLUSH,null,null);
                        wait_prompt(1,100);
                    }
                }
                else {
                    tts.speak("Want to try Again?",TextToSpeech.QUEUE_FLUSH,null,null);
                    tv.setText("Want to try Again?");
                    wait_prompt(2,101);
                }
            } break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_splash_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
