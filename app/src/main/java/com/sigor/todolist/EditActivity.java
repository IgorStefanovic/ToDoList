package com.sigor.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    Button editButton;
    Button cancelButton;
    Button removeButton;
    EditText name;
    EditText description;
    String editName;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        initViews();

        String[] items = new String[]{"All", "Shopping", "Wishlist", "Work", "Personal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        editName = getIntent().getExtras().getString("name");

        final ListItem li = MainActivity.database.getTask(editName);

        name.setText(li.getName());
        description.setText(li.getDescription());
        spinner.setSelection(adapter.getPosition(li.getCategory()));

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().length() == 0) {
                    Toast.makeText(EditActivity.this, "You need to enter something or just click CANCEL", Toast.LENGTH_SHORT).show();
                } else {
                    MainActivity.database.edit(li, name.getText().toString().trim(), description.getText().toString().trim(), spinner.getSelectedItem().toString(), li.isFinished());
                    MainActivity.category();
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

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.database.delete(li.getName());
                MainActivity.category();
                finish();
            }
        });
    }

    void initViews() {
        editButton = (Button) findViewById(R.id.editButton);
        cancelButton = (Button) findViewById(R.id.editCancelButton);
        removeButton = (Button) findViewById(R.id.editRemoveButton);
        name = (EditText) findViewById(R.id.editName);
        description = (EditText) findViewById(R.id.editDescription);
        spinner = (Spinner) findViewById(R.id.spinner_edit);
    }
}
