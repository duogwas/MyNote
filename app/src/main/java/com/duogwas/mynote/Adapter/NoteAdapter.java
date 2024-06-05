package com.duogwas.mynote.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.duogwas.mynote.Model.Note;
import com.duogwas.mynote.R;

import java.text.SimpleDateFormat;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    List<Note> notes;
    Context context;

    public NoteAdapter(List<Note> noteList, Context context) {
        this.notes = noteList;
        this.context = context;
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(int position, int quantity);
    }

    private OnQuantityChangeListener quantityChangeListener;

    public void setQuantityChangeListener(OnQuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notes.get(position);
        if (note == null) {
            return;
        }

        holder.tvNoteTitle.setText(note.getTitle());
        holder.tvNoteContent.setText(note.getContent());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        holder.tvCreatedAt.setText(formatter.format(note.getCreated_at()));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNoteTitle, tvNoteContent, tvCreatedAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNoteTitle = itemView.findViewById(R.id.tvNoteTitle);
            tvNoteContent = itemView.findViewById(R.id.tvNoteContent);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);

        }
    }
}