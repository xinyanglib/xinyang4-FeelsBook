package com.xinyang4.ualberta.feelsbook;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    private Spinner selectEmotion;
    private EditText comment;
    private TextView date;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Calendar calendar = Calendar.getInstance();
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // get the emotion
        index = getIntent().getIntExtra("position",0);
        final Emotion emotion = DataManager.getInstance(this).getEmotions().get(index);

        // get views
        selectEmotion = findViewById(R.id.edit_emotion);
        comment = findViewById(R.id.edit_comment);
        date = findViewById(R.id.edit_text_date);

        // bind adapter to spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, Emotion.CATALOG);
        selectEmotion.setAdapter(spinnerAdapter);

        selectEmotion.setSelection(emotion.getTypeIndex());
        date.setText(emotion.getFormatedDate());
        comment.setText(emotion.getComment());

        calendar.setTime(emotion.getDate());

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i,i1,i2);
                emotion.setDate(calendar.getTime());
                date.setText(emotion.getFormatedDate());

            }
        },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR_OF_DAY,i);
                calendar.set(Calendar.MINUTE,i1);
                emotion.setDate(calendar.getTime());
                date.setText(emotion.getFormatedDate());
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),true);


        selectEmotion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                emotion.setType(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        comment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                emotion.setComment(editable.toString());
            }
        });

    }


    public void editDate(View view){
        datePickerDialog.show();
    }

    public void editTime(View view){
        timePickerDialog.show();
    }

    public void deleteEmotion(View view){
        DataManager.getInstance(this).getEmotions().remove(index);
        finish();
    }
}
