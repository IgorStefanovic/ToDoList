package com.sigor.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by sigor on 27/10/2016.
 */

public class SQLClass extends SQLiteOpenHelper {

    public SQLClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public SQLClass(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tasks (Name text, Description text, Category text, Finished text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert (ListItem listItem) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("Name", listItem.getName());
        values.put("Description", listItem.getDescription());
        values.put("Category", listItem.getCategory());
        values.put("Finished",  listItem.isFinished());

        db.insert("tasks", null, values);
    }

    public int getCount() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("tasks", null, null, null, null, null, null, null);

        return cursor.getCount();
    }

    public ArrayList<ListItem> readTasks() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("tasks", null, null, null, null, null, null, null);

        ArrayList<ListItem> tasks = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int index = cursor.getColumnIndex("Name");
            String name = cursor.getString(index);

            index = cursor.getColumnIndex("Description");
            String description = cursor.getString(index);

            index = cursor.getColumnIndex("Category");
            String category = cursor.getString(index);

            index = cursor.getColumnIndex("Finished");
            String finished = cursor.getString(index);

            ListItem listItem = new ListItem(name, description, category, finished);

            tasks.add(listItem);
        }

        return tasks;
    }

    public ListItem getTask(String name) {
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM tasks WHERE Name='" + name + "'";
        Cursor cursor = db.rawQuery(q, null);

        cursor.moveToFirst();

        int index = cursor.getColumnIndex("Name");
        String nameL = cursor.getString(index);

        index = cursor.getColumnIndex("Description");
        String description = cursor.getString(index);

        index = cursor.getColumnIndex("Category");
        String category = cursor.getString(index);

        index = cursor.getColumnIndex("Finished");
        String finished = cursor.getString(index);

        ListItem listItem = new ListItem(name, description, category, finished);

        return listItem;
    }

    public ArrayList<ListItem> readTasksFinished(String finish) {
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM tasks WHERE Finished='" + finish + "'";

        Cursor cursor = db.rawQuery(q, null);

        ArrayList<ListItem> tasks = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int index = cursor.getColumnIndex("Name");
            String name = cursor.getString(index);

            index = cursor.getColumnIndex("Description");
            String description = cursor.getString(index);

            index = cursor.getColumnIndex("Category");
            String category1 = cursor.getString(index);

            index = cursor.getColumnIndex("Finished");
            String finished = cursor.getString(index);

            ListItem listItem = new ListItem(name, description, category1, finished);

            tasks.add(listItem);
        }

        return tasks;
    }

    public ArrayList<ListItem> readTasksCategory(String category) {
        SQLiteDatabase db = getReadableDatabase();
        String q = "SELECT * FROM tasks WHERE Category='" + category + "'";

        Cursor cursor = db.rawQuery(q, null);

        ArrayList<ListItem> tasks = new ArrayList<>();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            int index = cursor.getColumnIndex("Name");
            String name = cursor.getString(index);

            index = cursor.getColumnIndex("Description");
            String description = cursor.getString(index);

            index = cursor.getColumnIndex("Category");
            String category1 = cursor.getString(index);

            index = cursor.getColumnIndex("Finished");
            String finished = cursor.getString(index);

            ListItem listItem = new ListItem(name, description, category1, finished);

            tasks.add(listItem);
        }

        return tasks;
    }

    public void delete(String name) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tasks", "Name=?", new String[]{name});
    }

    public void edit(ListItem li, String name, String description, String category, String finished) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues ct = new ContentValues();

        ct.put("Name", name);
        ct.put("Description", description);
        ct.put("Category", category);
        ct.put("Finished", finished);

        db.update("tasks", ct, "Name=?", new String[]{li.getName()});
    }
}
