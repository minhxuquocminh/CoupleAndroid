package com.example.couple.View.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.couple.Base.Handler.IOFileBase;
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

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvNote;

        public NoteViewHolder(View itemView) {
            super(itemView);

            tvNote = itemView.findViewById(R.id.tvNote);

        }
    }

    @Override
    public NoteViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout, parent, false);

        NoteViewHolder viewHolder = new NoteViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String note = notes.get(position);
        holder.tvNote.setText(note);
        holder.tvNote.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa?")
                        .setMessage("Bạn có muốn xóa ghi chú '" + note + "' không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String data = "";
                                for (int i = 0; i < notes.size(); i++) {
                                    if (i != position) {
                                        data += notes.get(i) + "===";
                                    }
                                }
                                IOFileBase.saveDataToFile(context, "note.txt", data, 0);
                                notes.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
