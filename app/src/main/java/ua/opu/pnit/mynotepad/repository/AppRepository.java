package ua.opu.pnit.mynotepad.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ua.opu.pnit.mynotepad.model.Note;

public class AppRepository {

    private Executor executor = Executors.newSingleThreadExecutor();
    private static AppRepository instance;
    private AppDatabase db;

    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }
        return instance;
    }

    private AppRepository(Context context) {
        db = AppDatabase.getInstance(context);
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<List<Note>> result = es.submit(() -> db.noteDAO().getAll());

        try {
            notes = result.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        es.shutdown();

        return notes;
    }

    public void deleteNoteById(int note_id) {
        executor.execute(() -> db.noteDAO().deleteNoteById(note_id));
    }

    public void insertNote(Note note) {
        executor.execute(() -> db.noteDAO().insertNote(note));
    }

    public Note getNoteById(int note_id) {
        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<Note> result = es.submit(() -> db.noteDAO().getNoteById(note_id));

        try {
            return result.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            es.shutdown();
        }
    }
}
