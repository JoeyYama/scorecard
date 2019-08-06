package com.jyamanouchi.golfscorebasic;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jyamanouchi.golfscorebasic.database.ParEntity;

import java.util.ArrayList;
import java.util.List;

public class ParActivity extends AppCompatActivity implements DataTransfer {

    public static final String EXTRA_PAR = "EXTRA_PAR";
    public static final String EXTRA_HOLE = "hole";
    public static final String EXTRA_REPLY_ID = "extra_data_id";

    private int lastPosition = 0;
    private List<ParEntity> listParEntity;
    private ScoreViewModel parViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Log.d("ParActivity", "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_par);

        RecyclerView mRecyclerView = findViewById(R.id.rv_par);
        final ParAdapter mAdapter = new ParAdapter(this, listParEntity);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        parViewModel = ViewModelProviders.of(this).get(ScoreViewModel.class);
        parViewModel.getPar().observe(this, new Observer<List<ParEntity>>()  {
            @Override
            public void onChanged(@Nullable final List<ParEntity> intPar) {
                // Update the cached copy of par in the adapter.
                //Log.d("ParActivity", "onChanged");
                mAdapter.updatePar(intPar);
            }
        });

        final Button mButtonManual = findViewById(R.id.btn_Manual);

        mButtonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                ArrayList<Integer> hole = new ArrayList<>();
                ArrayList<Integer> par = new ArrayList<>();

                int size = mRecyclerView.getAdapter().getItemCount();
                for(int i=0;i< size; i++) {
                    hole.add(i+1);
                    par.add(mAdapter.mParList.get(i).getPar());
                }

                replyIntent.putIntegerArrayListExtra(EXTRA_HOLE, hole);
                replyIntent.putIntegerArrayListExtra(EXTRA_PAR, par);
                replyIntent.putExtra(EXTRA_REPLY_ID, 0);
                //Log.d("ParActivity"," Button onClick");
                setResult(RESULT_OK, replyIntent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView mRecyclerView = findViewById(R.id.rv_par);
        mRecyclerView.getLayoutManager().scrollToPosition(lastPosition);
    }

    @Override
    protected void onPause() {
        super.onPause();
        RecyclerView mRecyclerView = findViewById(R.id.rv_par);
        lastPosition = ((LinearLayoutManager)mRecyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onChange(List<ParEntity> data) {
        //Log.d("ParActivity", data.toString());
        listParEntity = data;
    }
}
