package ua.opu.pnit.mynotepad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements NotesAdapter.NotesAdapterListener {

    @BindView(R.id.notes_rv)
    RecyclerView mNotesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initWindowConfiguration();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mNotesRV.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mNotesRV.setAdapter(new NotesAdapter(this, this, MyNotepad.getInstance().notes));
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
    protected void onStart() {
        super.onStart();

        mNotesRV.setAdapter(new NotesAdapter(this, this, MyNotepad.getInstance().notes));
    }

    @Override
    public void onNoteEdit(int note_id) {
        Intent i = new Intent(this, NoteActivity.class);
        i.putExtra(MyNotepad.NOTE_ID_ARG, note_id);
        startActivity(i);
    }

    @Override
    public void onNoteDelete(int position) {
        MyNotepad.getInstance().notes.remove(position);

        if (!MyNotepad.getInstance().notes.isEmpty()) {
            mNotesRV.getAdapter().notifyItemRemoved(position);
            mNotesRV.getAdapter().notifyItemRangeChanged(position, MyNotepad.getInstance().notes.size() - position);
        } else {
            mNotesRV.setAdapter(new NotesAdapter(this, this, MyNotepad.getInstance().notes));
            mNotesRV.invalidate();
        }
    }
}
