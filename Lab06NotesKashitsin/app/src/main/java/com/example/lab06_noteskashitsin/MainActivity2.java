package com.example.lab06_noteskashitsin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity2 extends AppCompatActivity {

    EditText Title;
    EditText Content;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Title = findViewById(R.id.txt_title);
        Content = findViewById(R.id.txt_content);

        Intent i = getIntent();
        pos = i.getIntExtra("my-note-index", -1);
        Title.setText(i.getStringExtra("my-note-title"));
        Content.setText(i.getStringExtra("my-note-content"));
    }

    public void onCancel(View v)//Кашицын,393
    {

        finish();
    }

    public void onSave(View v)
    {
        Intent i = new Intent();
        i.putExtra("my-note-index", pos);
        i.putExtra("my-note-title", Title.getText().toString());
        i.putExtra("my-note-content", Content.getText().toString());

        setResult(RESULT_OK, i);
        finish();
    }
}