package com.example.couple.View.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.couple.Base.Handler.FirebaseBase;
import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.Base.View.WidgetBase;
import com.example.couple.Model.Display.Prediction;
import com.example.couple.R;
import com.example.couple.View.PredictionBridge.MonthlyPredictionBridgeActivity;
import com.example.couple.View.PredictionBridge.WeeklyPredictionBridgeActivity;

import java.io.Serializable;
import java.util.List;

public class PredictionBridgeAdapter extends
        RecyclerView.Adapter<PredictionBridgeAdapter.PredictionBridgeViewHolder> {
    Context context;
    int layout;
    List<Prediction> predictionList;

    public PredictionBridgeAdapter(Context context, int layout, List<Prediction> predictionList) {
        this.context = context;
        this.predictionList = predictionList;
        this.layout = layout;
    }

    public class PredictionBridgeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimeName;
        TextView tvUpdatedDayNumber;
        TextView tvNumbers;
        TextView tvCopy;

        public PredictionBridgeViewHolder(View itemView) {
            super(itemView);

            tvTimeName = itemView.findViewById(R.id.tvTimeName);
            tvUpdatedDayNumber = itemView.findViewById(R.id.tvUpdatedDayNumber);
            tvNumbers = itemView.findViewById(R.id.tvNumbers);
            tvCopy = itemView.findViewById(R.id.tvCopy);
        }
    }

    @Override
    public PredictionBridgeAdapter.PredictionBridgeViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout, parent, false);

        PredictionBridgeAdapter.PredictionBridgeViewHolder viewHolder =
                new PredictionBridgeAdapter.PredictionBridgeViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PredictionBridgeAdapter.PredictionBridgeViewHolder holder, int position) {
        Prediction pb = predictionList.get(position);
        holder.tvTimeName.setText(pb.toTimeName());
        holder.tvUpdatedDayNumber.setText(pb.getLastUpdate().countUp() + " ngày");
        String numbersStr = "";
        String triadsStr = pb.getTriads();
        numbersStr = triadsStr.equals("") ? "" : "(Càng: ";
        numbersStr += triadsStr;
        numbersStr += triadsStr.equals("") ? "" : ") ";
        numbersStr += pb.getNumberArray().getNumbers();
        holder.tvNumbers.setText(numbersStr);
        String finalNumbersStr = numbersStr;

        holder.tvCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WidgetBase.copyToClipboard(context, "numbers", finalNumbersStr);
                Toast.makeText(context, "Đã copy vào clipboard !", Toast.LENGTH_SHORT).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetBase.isInternetAvailable(context)) {
                    if (pb.getType() == 1) {
                        Intent intent = new Intent(context, WeeklyPredictionBridgeActivity.class);
                        intent.putExtra("POSITION", position);
                        intent.putExtra("PB", (Serializable) pb);
                        context.startActivity(intent);
                    } else {
                        Intent intent = new Intent(context, MonthlyPredictionBridgeActivity.class);
                        intent.putExtra("POSITION", position);
                        intent.putExtra("PB", (Serializable) pb);
                        context.startActivity(intent);
                    }
                } else {
                    Toast.makeText(context, "Bạn đang offline.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Xóa?")
                        .setMessage("Bạn có muốn xóa cầu dự đoán này không?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (InternetBase.isInternetAvailable(context)) {
                                    String objName = pb.getType() == 1 ? "weekly" : "monthly";
                                    FirebaseBase fb = new FirebaseBase(objName);
                                    fb.removeObject(pb.getId());
                                    predictionList.remove(position);
                                    notifyDataSetChanged();
                                } else {
                                    Toast.makeText(context, "Bạn đang offline.", Toast.LENGTH_SHORT).show();
                                }

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
        return predictionList.size();
    }
}
