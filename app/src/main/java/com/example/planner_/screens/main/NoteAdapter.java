package com.example.planner_.screens.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SortedList;

import com.example.planner_.App;
import com.example.planner_.R;
import com.example.planner_.model.NoteModel;
import com.example.planner_.screens.note.Note;
import com.example.planner_.screens.note.NoteEditor;

import java.util.List;

import io.reactivex.Notification;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private SortedList<NoteModel> sortedList;

    public NoteAdapter() {

        sortedList = new SortedList<>(NoteModel.class, new SortedList.Callback<NoteModel>() {
            @Override
            public int compare(NoteModel o1, NoteModel o2) {
                return (int) (o2.timestamp - o1.timestamp);
            }

            @Override
            public void onChanged(int position, int count) {
                notifyItemRangeChanged(position, count);
            }

            @Override
            public boolean areContentsTheSame(NoteModel oldItem, NoteModel newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areItemsTheSame(NoteModel item1, NoteModel item2) {
                return item1.id == item2.id;
            }

            @Override
            public void onInserted(int position, int count) {
                notifyItemRangeInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                notifyItemRangeRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                notifyItemMoved(fromPosition, toPosition);
            }
        });
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(sortedList.get(position));
    }

    @Override
    public int getItemCount() {
        return sortedList.size();
    }

    public void setItems(List<NoteModel> notes) {
        sortedList.replaceAll(notes);
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView noteText;
        View delete;

        NoteModel note;
        private static final String EXTRA_NOTE = "NoteEditor.EXTRA_NOTE";

        public NoteViewHolder(@NonNull final View itemView) {
            super(itemView);

            noteText = itemView.findViewById(R.id.note_text);
            delete = itemView.findViewById(R.id.delete_note);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("text", noteText.getText());
                    intent.putExtra("Extra", EXTRA_NOTE);
                    view.getContext().startActivity(intent);

                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    App.getInstance().getNoteDao().delete(note);
                }
            });
        }

        public void bind(NoteModel note) {
            this.note = note;

            noteText.setText(note.text);
        }
    }
}