package ua.opu.pnit.mynotepad.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ua.opu.pnit.mynotepad.model.Note;
import ua.opu.pnit.mynotepad.repository.db.AppDatabase;
import ua.opu.pnit.mynotepad.repository.retrofit.RetrofitController;

public class AppRepository {

    private Executor executor = Executors.newSingleThreadExecutor();
    private static AppRepository instance;
    private AppDatabase db;

    private RetrofitController retrofit;

    public static AppRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AppRepository(context);
        }
        return instance;
    }

    private AppRepository(Context context) {
        db = AppDatabase.getInstance(context);
        retrofit = new RetrofitController();
    }

    public LiveData<List<Note>> getAllNotes() {
        return db.noteDAO().getAll();
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

    public void checkToken(MutableLiveData<Boolean> authResult) {
        retrofit.checkToken(authResult);
    }

    public void loginAttempt(String username, String password, MutableLiveData<Boolean> loginResult) {
        retrofit.loginAttempt(username, password, loginResult);
    }
}
