package ua.opu.pnit.mynotepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ua.opu.pnit.mynotepad.model.Note;

public class MainActivity extends AppCompatActivity implements NotesAdapter.NotesAdapterListener {

    @BindView(R.id.notes_rv)
    RecyclerView mNotesRV;

    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initViewModel();
        initWindowConfiguration();
        initRecyclerView();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        LiveData<List<Note>> notes = mViewModel.getData();
        notes.observe(this, data -> {
            mNotesRV.setAdapter(new NotesAdapter(this, this, data));
        });
    }

    private void initRecyclerView() {
        mNotesRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    }

    public void initWindowConfiguration() {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(Color.TRANSPARENT);

        CollapsingToolbarLayout ctb = findViewById(R.id.toolbar_layout);
        ctb.setCollapsedTitleTypeface(ResourcesCompat.getFont(this, R.font.mont));
        ctb.setExpandedTitleTypeface(ResourcesCompat.getFont(this, R.font.mont));
    }

    @OnClick(R.id.fab)
    public void fabClick() {
        Intent i = new Intent(this, NoteActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onNoteEdit(int note_id) {
        Intent i = new Intent(this, NoteActivity.class);
        i.putExtra(MyNotepad.NOTE_ID_ARG, note_id);
        startActivity(i);
    }

    @Override
    public void onNoteDelete(int id) {
        mViewModel.deleteNote(id);
    }
}
