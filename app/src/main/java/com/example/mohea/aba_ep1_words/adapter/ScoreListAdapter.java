package com.example.mohea.aba_ep1_words.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.WordParser;
import com.example.mohea.aba_ep1_words.listener.ClientListClickListener;
import com.example.mohea.aba_ep1_words.listener.ScoreListClickListener;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.set.ScoreSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.ArrayList;

public class ScoreListAdapter extends RecyclerView.Adapter<ScoreListAdapter.ScoreListViewHolder> {

    private Context context;
    private ScoreListClickListener scoreListClickListener;
    private ArrayList<ScoreSet> scoreSetArrayList;


    public ScoreListAdapter(Context context, ScoreListClickListener scoreListClickListener,long id) {
        this.context = context;
        this.scoreListClickListener = scoreListClickListener;
        scoreSetArrayList = new SQLiteHelper(context).readScore(id);
    }

    class ScoreListViewHolder extends RecyclerView.ViewHolder{

        TextView tvScoreListNum;
        TextView tvScoreListDate;
        TextView tvScoreListElement;
        TextView tvScoreListScore;

        ImageView[] ivScoreListPosition = new ImageView[3];

        private ScoreListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvScoreListNum = itemView.findViewById(R.id.tv_score_list_num);
            tvScoreListDate = itemView.findViewById(R.id.tv_score_list_date);
            tvScoreListElement = itemView.findViewById(R.id.tv_score_list_element);
            tvScoreListScore = itemView.findViewById(R.id.tv_score_list_score);

            ivScoreListPosition = new ImageView[3];
            ivScoreListPosition[0] = itemView.findViewById(R.id.iv_score_list_position_start);
            ivScoreListPosition[1] = itemView.findViewById(R.id.iv_score_list_position_center);
            ivScoreListPosition[2] = itemView.findViewById(R.id.iv_score_list_position_end);
        }
    }

    @NonNull
    @Override
    public ScoreListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.score_list_item,null);
        return new ScoreListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreListViewHolder scoreListViewHolder, final int i) {

        scoreListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scoreListClickListener.scoreListClick(scoreSetArrayList.get(i));
            }
        });

        scoreListViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                scoreListClickListener.scoreListLongClick(scoreSetArrayList.get(i));
                return true;
            }
        });

        ScoreSet scoreSet = scoreSetArrayList.get(i);

        String date = DateFormat.format("yyyy-MM-dd    kk:mm",scoreSet.getScoreId()).toString();

        String subcode = scoreSet.getSubCode();

        int position = Integer.parseInt(subcode.charAt(0)+"");

        String scoreData = scoreSet.getScore();
        int scorePoint = scoreData.indexOf(',');
        String score = scoreData.substring(0,scorePoint);

        String element = new WordParser().subcodeParsing(subcode);

        switch (position){
            case 1:
                scoreListViewHolder.ivScoreListPosition[0].setImageResource(R.drawable.check_green);
                break;
            case 2:
                scoreListViewHolder.ivScoreListPosition[1].setImageResource(R.drawable.check_green);
                break;
            case 3:
                scoreListViewHolder.ivScoreListPosition[2].setImageResource(R.drawable.check_green);
                break;
            case 4:
                scoreListViewHolder.ivScoreListPosition[0].setImageResource(R.drawable.check_green);
                scoreListViewHolder.ivScoreListPosition[1].setImageResource(R.drawable.check_green);
                break;
            case 5:
                scoreListViewHolder.ivScoreListPosition[0].setImageResource(R.drawable.check_green);
                scoreListViewHolder.ivScoreListPosition[2].setImageResource(R.drawable.check_green);
                break;
            case 6:
                scoreListViewHolder.ivScoreListPosition[1].setImageResource(R.drawable.check_green);
                scoreListViewHolder.ivScoreListPosition[2].setImageResource(R.drawable.check_green);
                break;
            case 7:
                scoreListViewHolder.ivScoreListPosition[0].setImageResource(R.drawable.check_green);
                scoreListViewHolder.ivScoreListPosition[1].setImageResource(R.drawable.check_green);
                scoreListViewHolder.ivScoreListPosition[2].setImageResource(R.drawable.check_green);
                break;
        }
        scoreListViewHolder.tvScoreListNum.setText((i+1)+".");
        scoreListViewHolder.tvScoreListDate.setText(date);
        scoreListViewHolder.tvScoreListElement.setText(element);
        scoreListViewHolder.tvScoreListScore.setText(score);


    }

    @Override
    public int getItemCount() {
        return scoreSetArrayList.size();
    }
}
