package com.xinyang4.ualberta.feelsbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.Comparator;


public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private Spinner selectEmotion;
    private EditText comment;
    private AlertDialog.Builder statisticDialog;
    private ArrayAdapter<String> statisticAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataManager dm = DataManager.getInstance(this);

        // get views
        listView = findViewById(R.id.emotionList);
        selectEmotion = findViewById(R.id.selectyEmotion);
        comment = findViewById(R.id.comment);

        // bind adapter to spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Emotion.CATALOG);
        selectEmotion.setAdapter(spinnerAdapter);


        // bind adapter to listview
        final EmotionListAdapter listAdapter = new EmotionListAdapter(this,
                R.layout.item_emotion,dm.getEmotions());
        listView.setAdapter(listAdapter);

        // set onclick
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                listAdapter.select(i);
                listAdapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        statisticDialog = new AlertDialog.Builder(this);
        statisticDialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        statisticAdapter =  new ArrayAdapter<String>(this, android.R.layout.select_dialog_item);
        statisticDialog.setAdapter(statisticAdapter,null);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_statistic:
                int count;
                statisticAdapter.clear();
                for (int i = 0; i < Array.getLength(Emotion.CATALOG); ++i){
                    count = 0;
                    for (Emotion emotion : DataManager.getInstance(this).getEmotions()){
                        if (emotion.getTypeIndex() == i){
                            count += 1;
                        }
                    }
                    statisticAdapter.add(Emotion.CATALOG[i] + ": " + String.valueOf(count));
                }
                statisticDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EmotionListAdapter adapter =  ((EmotionListAdapter) listView.getAdapter());
        adapter.select(-1);
        adapter.notifyDataSetChanged();
    }

    public void addEmotion(View view){
        DataManager.getInstance(this).getEmotions().add(new Emotion(
                selectEmotion.getSelectedItemPosition(),
                comment.getText().toString()
        ));

        ((EmotionListAdapter) listView.getAdapter()).notifyDataSetChanged();
        comment.setText("");
        comment.clearFocus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        DataManager.saveToFile(this);
    }

    //    public void openHistory(View view)
//    {
//        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
//        startActivity(intent);
//    }
//
//    public void click(View view)
//    {
//        Toast.makeText(getApplicationContext(), "You Click This Button", Toast.LENGTH_SHORT).show();
//    }

//    public void getFeeling(View view)
//    {
//        RadioButton radioButton;
//        if(view.getId() == R.id.sadness)
//            radioButton = findViewById(R.id.sadness);
//        else if(view.getId() == R.id.joy)
//            radioButton = findViewById(R.id.joy);
//        else if(view.getId() == R.id.love)
//            radioButton = findViewById(R.id.love);
//        else if(view.getId() == R.id.fear)
//            radioButton = findViewById(R.id.fear);
//        else if(view.getId() == R.id.anger)
//            radioButton = findViewById(R.id.anger);
//        else if(view.getId() == R.id.surprise)
//            radioButton = findViewById(R.id.surprise);
//        else
//            throw new NullPointerException();
//        feeling = radioButton.getText().toString();
//        //Log.d("Test", feeling.toString());
//    }

//
//    public void getInformation()
//    {
//        EditText simpleEditText = (EditText) findViewById(R.id.comment);
//        comment = simpleEditText.getText().toString();
//        date = new Date();
//
//    }

}
