package ua.opu.pnit.mynotepad;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import ua.opu.pnit.mynotepad.model.Note;

@AllArgsConstructor
public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {

    public interface NotesAdapterListener {
        void onNoteEdit(int note_id);

        void onNoteDelete(int position);
    }

    private Context context;
    private NotesAdapterListener listener;
    private List<Note> list;

    class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView daysago;
        TextView title;
        TextView text;
        ImageView edit;
        ImageView delete;


        NoteViewHolder(@NonNull View view) {
            super(view);

            daysago = view.findViewById(R.id.daysago);
            title = view.findViewById(R.id.title);
            text = view.findViewById(R.id.text);

            edit = view.findViewById(R.id.edit);
            delete = view.findViewById(R.id.delete);
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int pos) {
        View view;

        if (list.size() == 0) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_no_notes, viewGroup, false);
        } else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note, viewGroup, false);
        }

        return new NoteViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int pos) {

        if (list.size() == 0)
            return;

        Note note = list.get(pos);

        holder.daysago.setText(getDaysAgo(note.getDateUpdate()));
        holder.title.setText(note.getTitle());
        holder.text.setText(note.getContents());

        holder.edit.setOnClickListener(view -> {
            listener.onNoteEdit(note.getId());
        });

        holder.delete.setOnClickListener(view -> {
            listener.onNoteDelete(pos);
        });
    }

    @Override
    public int getItemCount() {
        return Math.max(1, list.size());
    }

    private String getDaysAgo(Date date) {

        Calendar publish = Calendar.getInstance();
        publish.setTime(date);

        long msDiff = Calendar.getInstance().getTimeInMillis() - publish.getTimeInMillis();
        long daysDiff = TimeUnit.MILLISECONDS.toSeconds(msDiff);

        if (daysDiff < 5) {
            return "a moment ago";
        } else if (daysDiff < 60) {
            return "" + daysDiff + " second(-s) ago";
        } else if (daysDiff < 3600) {
            return "" + TimeUnit.MILLISECONDS.toMinutes(msDiff) + " minute(-s) ago";
        } else if (daysDiff < (3600 * 24)) {
            return "" + TimeUnit.MILLISECONDS.toHours(msDiff) + " hours(-s) ago";
        } else {
            return "" + TimeUnit.MILLISECONDS.toDays(msDiff) + " day(-s) ago";
        }
    }
}
