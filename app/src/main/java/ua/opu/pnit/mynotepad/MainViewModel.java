package ua.opu.pnit.mynotepad;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ua.opu.pnit.mynotepad.model.Note;
import ua.opu.pnit.mynotepad.repository.AppRepository;

public class MainViewModel extends AndroidViewModel {

    private AppRepository repository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application);
    }

    public List<Note> getAllNotes() {
        return repository.getAllNotes();
    }

    public void deleteNote(int note_id) {
        repository.deleteNoteById(note_id);
    }
}
