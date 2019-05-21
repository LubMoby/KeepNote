package com.example.keepnote;

import java.util.ArrayList;

public class NotesData {
    private ArrayList<String> listNotesData;

    public NotesData() {
        this.listNotesData = new ArrayList<>();
    }

    public ArrayList<String> getListNotesData() {
        return listNotesData;
    }

    public void setListNotesData(ArrayList<String> listNotesData) {
        this.listNotesData = listNotesData;
    }
}
