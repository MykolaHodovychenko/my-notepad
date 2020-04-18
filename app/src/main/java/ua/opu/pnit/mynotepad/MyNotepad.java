package ua.opu.pnit.mynotepad;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import ua.opu.pnit.mynotepad.model.Note;

public class MyNotepad extends Application {

    public static final String NOTE_ID_ARG = "note_id";

    private static MyNotepad instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyNotepad getInstance() {
        return MyNotepad.instance;
    }
}
