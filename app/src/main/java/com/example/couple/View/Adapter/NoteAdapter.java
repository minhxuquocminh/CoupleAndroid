package com.example.couple.View.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.couple.Base.Handler.IOFileBase;
import com.example.couple.Base.View.DialogBase;
import com.example.couple.R;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    Context context;
    int layout;
    List<String> notes;

    public NoteAdapter(Context context, int layout, List<String> notes) {
        this.context = context;
        this.notes = notes;
        this.layout = layout;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNote;

        public NoteViewHolder(View itemView) {
            super(itemView);

            tvNote = itemView.findViewById(R.id.tvNote);

        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout, parent, false);
        return new NoteViewHolder(view);
    }

    @SuppressLint({"NotifyDataSetChanged", "RecyclerView"})
    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        String note = notes.get(position);
        holder.tvNote.setText(note);
        holder.tvNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String title = "Xóa?";
                String mesage = "Bạn có muốn xóa ghi chú '" + note + "' không?";
                DialogBase.showWithConfirmation(context, title, mesage, () -> {
                    String data = "";
                    for (int i = 0; i < notes.size(); i++) {
                        if (i != position) {
                            data += notes.get(i) + "===";
                        }
                    }
                    IOFileBase.saveDataToFile(context, "note.txt", data, 0);
                    notes.remove(position);
                    notifyDataSetChanged();
                });
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
