package ua.opu.pnit.mynotepad;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ua.opu.pnit.mynotepad.model.Note;
import ua.opu.pnit.mynotepad.repository.AppRepository;

public class NoteViewModel extends AndroidViewModel {

    private AppRepository repository;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application);
    }

    public Note getNoteById(int note_id) {
        return repository.getNoteById(note_id);
    }

    public void insertNote(Note note) {
        repository.insertNote(note);
    }
}
