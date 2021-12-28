package com.example.lab06_noteskashitsin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Note> adp;
    int sel = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//Кашицын,393
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adp = new ArrayAdapter<Note>(this, android.R.layout.simple_list_item_1);
        ListView lst = findViewById(R.id.lst_notes);
        lst.setAdapter(adp);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sel = position;
            }
        });
    }

    @Override
    protected  void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if (data != null)
        {
            int pos = data.getIntExtra("my-note-index", -1);
            String title = data.getStringExtra("my-note-title");
            String content = data.getStringExtra("my-note-content");

            Note n = adp.getItem(pos);
            n.title = title;
            n.content = content;

            adp.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void onNewClick(View v)//Кашицын,393
    {
        Note n = new Note();
        n.title = "";
        n.content = "";

        adp.add(n);
        int pos = adp.getPosition(n);

        Intent i = new Intent(this, MainActivity2.class);
        i.putExtra("my-note-index", pos);
        i.putExtra("my-note-title", n.title);
        i.putExtra("my-note-content", n.content);

        startActivityForResult(i, 555);
    }

    public void onEditClick(View v)
    {
        if (sel != -1) {
            Note n = adp.getItem(sel);
            Intent i = new Intent(this, MainActivity2.class);
            i.putExtra("my-note-index", adp.getPosition(n));
            i.putExtra("my-note-title", n.title);
            i.putExtra("my-note-content", n.content);
            startActivityForResult(i, 555);
            sel = -1;
        }
    }

    public void onDelClick(View v)//Кашицын,393
    {
        if (sel != -1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dlg = builder.create();
            dlg.setTitle("Вы действительно хотите удалить эту заметку?");
            dlg.setView(dialogRemove(dlg));
            dlg.show();

        }
    }
    LinearLayout createLL()
    {
        LinearLayout linearLayout = new LinearLayout(getBaseContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return linearLayout;
    }

    Button addButton(String str)
    {
        Button button = new Button(this);
        button.setText(str);
        return button;
    }

    LinearLayout dialogRemove(AlertDialog dlg)//Кашицын,393
    {
        LinearLayout linearLayout = createLL();
        Button butYes = addButton("Да");
        butYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note n = adp.getItem(sel);
                adp.remove(n);
                dlg.cancel();
                sel = -1;
            }
        });
        Button butNo = addButton("Отмена");
        butNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlg.cancel();
            }
        });
        linearLayout.addView(butYes);
        linearLayout.addView(butNo);
        return  linearLayout;
    }

}