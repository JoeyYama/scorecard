package com.jyamanouchi.golfscorebasic;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jyamanouchi.golfscorebasic.database.ParEntity;

import java.util.List;

public class ParAdapter extends RecyclerView.Adapter<ParAdapter.ParViewHolder> {

    public List<ParEntity> mParList;
    ScoreViewModel parViewModel;
    Context context;

    public ParAdapter(Context context, List<ParEntity> mParList) {
        //Log.d("ParAdapter", "constructor");
        this.context = context;
        this.mParList = mParList;
    }

    @NonNull
    @Override
    public ParAdapter.ParViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.par_layout, parent, false);
        ParViewHolder vh = new ParViewHolder(v, new MyCustomEditTextListener());
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ParViewHolder holder, int position) {
        holder.mHole.setText(String.valueOf(holder.getAdapterPosition() + 1));
        if (mParList != null) {
            holder.myCustomEditTextListener.updatePosition(holder.getAdapterPosition());
            ParEntity current = mParList.get(position);
            holder.mPar.setText(String.valueOf(current.getPar()));
        } else {
            holder.mPar.setText(String.valueOf(4)); //default par to 4
        }

        //Alternate line colors
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#87CEEB"));
        }
    }

    @Override
    public int getItemCount() {
        if (mParList != null)
            return mParList.size();
        else return 0;
    }

    class ParViewHolder extends RecyclerView.ViewHolder {
        private final TextView mHole;
        private final EditText mPar;
        private MyCustomEditTextListener myCustomEditTextListener;

        private ParViewHolder(View itemView, MyCustomEditTextListener myCustomEditTextListener) {
            super(itemView);
            mHole = itemView.findViewById(R.id.tv_hole);
            mPar = itemView.findViewById(R.id.et_par);
            this.myCustomEditTextListener = myCustomEditTextListener;
            this.mPar.addTextChangedListener(myCustomEditTextListener);
        }
    }

    private class MyCustomEditTextListener implements TextWatcher {
        private int position;

        public void updatePosition(int position) {
            this.position = position;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            // no op
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            //Log.d("ParAdapter", "onTextChanged");
            //If a character has been entered, save the information

            if (charSequence.length() > 0) {
                if (mParList.get(position).getPar() != Integer.parseInt(charSequence.toString())) {
                    mParList.get(position).setPar(Integer.parseInt(charSequence.toString()));
                }

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            // no op
        }
    }

    public void updatePar(List<ParEntity> parList) {
        mParList = parList;
        //Log.d("ParAdapter", "updatePar");
        notifyDataSetChanged();
    }

    public List<ParEntity> getPar() {
        return mParList;
    }

}
