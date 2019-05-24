package com.example.keepnote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.keepnote.db.NoteEntity;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<NoteEntity> dataNote;
    private Context context;

    private Listener listener;

    interface Listener{
        void onClick(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(@NonNull CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }

    public NotesAdapter(Context context){
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return dataNote == null ? 0 : dataNote.size();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public void setItems(List<NoteEntity> dataNote) {
        this.dataNote = dataNote;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note_show, viewGroup, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        if (dataNote == null) {
            return;
        }

        CardView cardView = viewHolder.cardView;
        TextView noteText = (TextView) cardView.findViewById(R.id.item_text_show);

        final NoteEntity noteEntity = dataNote.get(position);
        int textLength = noteEntity.noteText.length();

        if(textLength != 0){
            int endIndexText = textLength < 100? textLength:99;
            noteText.setText(noteEntity.noteText.substring(0,endIndexText));
        }else {

            Toast.makeText(viewHolder.cardView.getContext(), "Пустая заметка в список не попала", Toast.LENGTH_SHORT).show();
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(noteEntity.id);
                }
            }
        });
    }
}
