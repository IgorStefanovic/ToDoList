package com.sigor.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    public static ListViewAdapter lAdapter;
    public static ListView mListView;
    public static SQLClass database;
    FloatingActionButton fab;
    public static boolean all = true;
    public static boolean finished = false;
    public static boolean shopping = false;
    public static boolean wishlist = false;
    public static boolean work = false;
    public static boolean personal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MakeTodo.class);
                startActivity(intent);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem listItem = (ListItem) lAdapter.getItem(position);
                if (!finished && listItem.isFinished().equals("false")) {
                    Intent intent = new Intent(MainActivity.this, EditActivity.class);
                    intent.putExtra("name", listItem.getName());
                    startActivity(intent);
                }
            }
        });

        mListView.setAdapter(lAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.finished) {
            all = false;
            finished = true;
            shopping = false;
            wishlist = false;
            work = false;
            personal = false;
            lAdapter.setTasks(database.readTasksFinished("true"));
        } else if (id == R.id.shopping) {
            all = false;
            finished = false;
            shopping = true;
            wishlist = false;
            work = false;
            personal = false;
            lAdapter.setTasks(database.readTasksCategory("Shopping"));
        } else if (id == R.id.wishlist) {
            all = false;
            finished = false;
            shopping = false;
            wishlist = true;
            work = false;
            personal = false;
            lAdapter.setTasks(database.readTasksCategory("Wishlist"));
        } else if (id == R.id.work) {
            all = false;
            finished = false;
            shopping = false;
            wishlist = false;
            work = true;
            personal = false;
            lAdapter.setTasks(database.readTasksCategory("Work"));
        } else if (id == R.id.personal) {
            all = false;
            finished = false;
            shopping = false;
            wishlist = false;
            work = false;
            personal = true;
            lAdapter.setTasks(database.readTasksCategory("Personal"));
        } else {
            all = true;
            finished = false;
            shopping = false;
            wishlist = false;
            work = false;
            personal = false;
            lAdapter.setTasks(database.readTasks());
        }

        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.list_view) {
            if (!finished) {
                getMenuInflater().inflate(R.menu.menu_list, menu);
            } else {
                getMenuInflater().inflate(R.menu.menu_finished, menu);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if (item.getItemId() == R.id.edit) {
            ListItem listItem = (ListItem) lAdapter.getItem(info.position);
            if (listItem.isFinished().equals("false")) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("name", listItem.getName());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Can't edit that task, it's already done", Toast.LENGTH_SHORT).show();
            }
        } else if (item.getItemId() == R.id.done) {
            ListItem listItem = (ListItem) lAdapter.getItem(info.position);
            if (listItem.isFinished().equals("false")) {
                database.edit(listItem, listItem.getName(), listItem.getDescription(), listItem.getCategory(), "true");
                listItem.setFinished("true");
                category();
                Toast.makeText(this, "Task " + listItem.getName() + " done", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Can't do that, task is already done", Toast.LENGTH_SHORT).show();
            }
        } else {
            final ListItem listItem = (ListItem) lAdapter.getItem(info.position);;
            if (listItem.isFinished().equals("false")) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("Are you sure you want to delete a task that is unfinished?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        database.delete(listItem.getName());
                        category();
                        dialog.dismiss();
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            } else {
                database.delete(listItem.getName());
                category();
            }
        }
        return true;
    }

    private void initViews() {
        database = new SQLClass(this, "tasks", null, 1);
        lAdapter = new ListViewAdapter(this);
        mListView = (ListView) findViewById(R.id.list_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        registerForContextMenu(mListView);
        lAdapter.setTasks(database.readTasks());
    }

    public static void category() {
        if (shopping){
            lAdapter.setTasks(database.readTasksCategory("Shopping"));
        } else if (wishlist) {
            lAdapter.setTasks(database.readTasksCategory("Wishlist"));
        } else if (work) {
            lAdapter.setTasks(database.readTasksCategory("Work"));
        } else if (personal) {
            lAdapter.setTasks(database.readTasksCategory("Personal"));
        } else {
            lAdapter.setTasks(database.readTasks());
        }
    }
}
