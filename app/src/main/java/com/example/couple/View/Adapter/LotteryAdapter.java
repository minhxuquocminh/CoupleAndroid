package com.example.couple.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.couple.Model.Origin.Lottery;
import com.example.couple.R;

import java.util.List;

public class LotteryAdapter extends RecyclerView.Adapter<LotteryAdapter.LotteryViewHolder> {
    Context context;
    int layout;
    List<Lottery> lotteries;

    public LotteryAdapter(Context context, int layout, List<Lottery> lotteries) {
        this.context = context;
        this.lotteries = lotteries;
        this.layout = layout;
    }

    public class LotteryViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;
        TextView tvCharacters;
        TextView tvJackpots;
        TextView tvFirst;
        TextView tvSecond1;
        TextView tvSecond2;
        TextView tvThird1;
        TextView tvThird2;
        TextView tvThird3;
        TextView tvThird4;
        TextView tvThird5;
        TextView tvThird6;
        TextView tvFour1;
        TextView tvFour2;
        TextView tvFour3;
        TextView tvFour4;
        TextView tvFifth1;
        TextView tvFifth2;
        TextView tvFifth3;
        TextView tvFifth4;
        TextView tvFifth5;
        TextView tvFifth6;
        TextView tvSixth1;
        TextView tvSixth2;
        TextView tvSixth3;
        TextView tvSeventh1;
        TextView tvSeventh2;
        TextView tvSeventh3;
        TextView tvSeventh4;


        public LotteryViewHolder(View itemView) {
            super(itemView);

            tvDate = itemView.findViewById(R.id.tvDate);
            tvCharacters = itemView.findViewById(R.id.tvCharacters);
            tvJackpots = itemView.findViewById(R.id.tvJackpots);
            tvFirst = itemView.findViewById(R.id.tvFirst);
            tvSecond1 = itemView.findViewById(R.id.tvSecond1);
            tvSecond2 = itemView.findViewById(R.id.tvSecond2);
            tvThird1 = itemView.findViewById(R.id.tvThird1);
            tvThird2 = itemView.findViewById(R.id.tvThird2);
            tvThird3 = itemView.findViewById(R.id.tvThird3);
            tvThird4 = itemView.findViewById(R.id.tvThird4);
            tvThird5 = itemView.findViewById(R.id.tvThird5);
            tvThird6 = itemView.findViewById(R.id.tvThird6);
            tvFour1 = itemView.findViewById(R.id.tvFour1);
            tvFour2 = itemView.findViewById(R.id.tvFour2);
            tvFour3 = itemView.findViewById(R.id.tvFour3);
            tvFour4 = itemView.findViewById(R.id.tvFour4);
            tvFifth1 = itemView.findViewById(R.id.tvFifth1);
            tvFifth2 = itemView.findViewById(R.id.tvFifth2);
            tvFifth3 = itemView.findViewById(R.id.tvFifth3);
            tvFifth4 = itemView.findViewById(R.id.tvFifth4);
            tvFifth5 = itemView.findViewById(R.id.tvFifth5);
            tvFifth6 = itemView.findViewById(R.id.tvFifth6);
            tvSixth1 = itemView.findViewById(R.id.tvSixth1);
            tvSixth2 = itemView.findViewById(R.id.tvSixth2);
            tvSixth3 = itemView.findViewById(R.id.tvSixth3);
            tvSeventh1 = itemView.findViewById(R.id.tvSeventh1);
            tvSeventh2 = itemView.findViewById(R.id.tvSeventh2);
            tvSeventh3 = itemView.findViewById(R.id.tvSeventh3);
            tvSeventh4 = itemView.findViewById(R.id.tvSeventh4);
        }
    }

    @Override
    public LotteryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(layout, parent, false);

        LotteryViewHolder lotteryViewHolder = new LotteryViewHolder(view);

        return lotteryViewHolder;
    }

    @Override
    public void onBindViewHolder(LotteryViewHolder holder, final int position) {
        Lottery lottery = lotteries.get(position);
        holder.tvDate.setText(lottery.getTimeShow());
        holder.tvCharacters.setText(lottery.getCharacters());

        List<String> lotteryString=lottery.getLottery();
        holder.tvJackpots.setText(lotteryString.get(0));
        holder.tvFirst.setText(lotteryString.get(1));
        holder.tvSecond1.setText(lotteryString.get(2));
        holder.tvSecond2.setText(lotteryString.get(3));
        holder.tvThird1.setText(lotteryString.get(4));
        holder.tvThird2.setText(lotteryString.get(5));
        holder.tvThird3.setText(lotteryString.get(6));
        holder.tvThird4.setText(lotteryString.get(7));
        holder.tvThird5.setText(lotteryString.get(8));
        holder.tvThird6.setText(lotteryString.get(9));
        holder.tvFour1.setText(lotteryString.get(10));
        holder.tvFour2.setText(lotteryString.get(11));
        holder.tvFour3.setText(lotteryString.get(12));
        holder.tvFour4.setText(lotteryString.get(13));
        holder.tvFifth1.setText(lotteryString.get(14));
        holder.tvFifth2.setText(lotteryString.get(15));
        holder.tvFifth3.setText(lotteryString.get(16));
        holder.tvFifth4.setText(lotteryString.get(17));
        holder.tvFifth5.setText(lotteryString.get(18));
        holder.tvFifth6.setText(lotteryString.get(19));
        holder.tvSixth1.setText(lotteryString.get(20));
        holder.tvSixth2.setText(lotteryString.get(21));
        holder.tvSixth3.setText(lotteryString.get(22));
        holder.tvSeventh1.setText(lotteryString.get(23));
        holder.tvSeventh2.setText(lotteryString.get(24));
        holder.tvSeventh3.setText(lotteryString.get(25));
        holder.tvSeventh4.setText(lotteryString.get(26));

    }

    @Override
    public int getItemCount() {
        return lotteries.size();
    }

}
