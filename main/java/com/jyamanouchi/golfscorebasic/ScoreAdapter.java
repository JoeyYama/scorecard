package com.jyamanouchi.golfscorebasic;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jyamanouchi.golfscorebasic.database.ParEntity;
import com.jyamanouchi.golfscorebasic.database.ScoreEntity;

import java.util.List;

/**
 * This ScoreAdapter creates and binds ViewHolders, that hold the score,
 * to a RecyclerView to efficiently display data.
 */
public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>  {

    private final LayoutInflater mInflater;
    private List<ScoreEntity> mScoreList; // Cached copy of scores
    private List<ParEntity> mParList;
    private static ClickListener clickListener;
    private Context context;

    ScoreAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}

    @Override
    @NonNull
    public ScoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.score_layout, parent, false);
        return new ScoreViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreViewHolder holder, int position) {

        if(mScoreList != null && mParList != null) {
            ScoreEntity current = mScoreList.get(position);
            ParEntity current2 = mParList.get(position);
            holder.mHole.setText(String.valueOf(current.getHole()));
            holder.mPar.setText(String.valueOf(current2.getPar()));
            holder.mScore1.setText(String.valueOf(current.getScore1()));
            holder.mScore2.setText(String.valueOf(current.getScore2()));
            holder.mScore3.setText(String.valueOf(current.getScore3()));
            holder.mScore4.setText(String.valueOf(current.getScore4()));
        } else {
            holder.mHole.setText("Error");
        }

        if(position %2 == 1)
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.parseColor("#87CEEB"));
        }
    }

    public void setScores(List<ScoreEntity> scores){
        //Log.d("ScoreAdapter","setScores");
        mScoreList = scores;
        notifyDataSetChanged();
    }

    public void setPar(List<ParEntity> par) {
        //Log.d("ScoreAdapter", "setPar");
        mParList = par;
        notifyDataSetChanged();
    }
	
	// getItemCount() is called many times, and when it is first called,
    @Override
    public int getItemCount() {
        if (mScoreList != null)
            return mScoreList.size();
        else return 0;
    }

    public ScoreEntity getScoreAtPosition(int position) {
        return mScoreList.get(position);
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {
        private final TextView mHole, mPar, mScore1, mScore2, mScore3, mScore4;

        private ScoreViewHolder(View itemView) {
            super(itemView);
            mHole = (TextView) itemView.findViewById(R.id.tv_hole);
            mPar = (TextView) itemView.findViewById(R.id.tv_par);
            mScore1 = (TextView) itemView.findViewById(R.id.tv_score1);
            mScore2 = (TextView) itemView.findViewById(R.id.tv_score2);
            mScore3 = (TextView) itemView.findViewById(R.id.tv_score3);
            mScore4 = (TextView) itemView.findViewById(R.id.tv_score4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ScoreAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

}