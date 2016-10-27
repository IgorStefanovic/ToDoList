package com.sigor.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MakeTodo extends AppCompatActivity {

    Button addButton;
    Button cancelButton;
    EditText name;
    EditText description;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_todo);

        initViews();

        String[] items = new String[]{"All", "Shopping", "Wishlist", "Work", "Personal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() == 0) {
                    Toast.makeText(MakeTodo.this, "You need to enter something or just click CANCEL", Toast.LENGTH_SHORT).show();
                } else {
                    ListItem lItem = new ListItem(name.getText().toString().trim(), description.getText().toString().trim(), spinner.getSelectedItem().toString(), "false");
                    MainActivity.database.insert(lItem);
                    MainActivity.category();
                    //MainActivity.lAdapter.setTasks(MainActivity.database.readTasks());
                    finish();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    void initViews() {
        addButton = (Button) findViewById(R.id.addButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        name = (EditText) findViewById(R.id.name);
        description = (EditText) findViewById(R.id.description);
        spinner = (Spinner) findViewById(R.id.spinner);
    }
}
