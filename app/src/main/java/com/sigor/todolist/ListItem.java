package com.sigor.todolist;

/**
 * Created by sigor on 25/10/2016.
 */

public class ListItem {
    String name;
    String description;
    String category;
    String finished;

    public ListItem(String name, String description, String category, String finished) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.finished = finished;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String isFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
