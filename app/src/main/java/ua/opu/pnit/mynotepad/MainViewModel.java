package ua.opu.pnit.mynotepad;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ua.opu.pnit.mynotepad.model.Note;
import ua.opu.pnit.mynotepad.repository.AppRepository;

public class MainViewModel extends AndroidViewModel {

    private AppRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        repository = AppRepository.getInstance(application);
    }

    public void deleteNote(int note_id) {
        repository.deleteNoteById(note_id);
    }

    public LiveData<List<Note>> getData() {
        return repository.getAllNotes();
    }
}
