package com.example.keepnote;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private ArrayList<String> listNote;
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

    public NotesAdapter(ArrayList<String> listNote) {
        this.listNote = listNote;
    }

    @Override
    public int getItemCount() {
        return listNote.size();
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public void setItems(ArrayList<String> listNote) {
        this.listNote = listNote;
        notifyDataSetChanged();
    }

    public void clearItems() {
        this.listNote.clear();
        notifyDataSetChanged();
    }

    public void editItem(int id, String textNote) {
        this.listNote.set(id, textNote);
        notifyDataSetChanged();
    }


    public void deleteItem(int id) {
        this.listNote.remove(id);
        notifyDataSetChanged();
    }

    public void addItem(String text) {
        this.listNote.add(text);
        notifyDataSetChanged();
    }

    public String getCurrentText(int id){
        String text = "";
        if(listNote.size() > id){
            text = listNote.get(id);
        }
        return text;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_note_show, viewGroup, false);
        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        CardView cardView = viewHolder.cardView;
        TextView noteText = (TextView) cardView.findViewById(R.id.item_text_show);
        String text = listNote.get(i);
        int textLength = text.length();
        if(!text.isEmpty()){
            int endIndexText = textLength < 100? text.length():99;
            noteText.setText(listNote.get(i).substring(0,endIndexText));
        }else {

            Toast.makeText(viewHolder.cardView.getContext(), "Пустая заметка в список не попала", Toast.LENGTH_SHORT).show();
        }
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null){
                    listener.onClick(i);
                }
            }
        });

    }

}
