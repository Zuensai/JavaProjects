package com.todo;

public class Todo {
    int id;
    String text;
    boolean checked;

    public Todo(int id, String text, boolean checked) {
        this.id = id;
        this.text = text;
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void toggleChecked() {
        if (checked == true) {
            checked = false;
        } else {
            checked = true;
        }
    }

    public String getText() {
        return text;
    }

    public boolean isChecked() {
        return checked;
    }

    public String toString() {
        String emoji;
        if (checked == true) {
            emoji = "✔️";
        } else {
            emoji = "✖️"; 
        }
        return id + ". " + text + " " + emoji;
    }
}

