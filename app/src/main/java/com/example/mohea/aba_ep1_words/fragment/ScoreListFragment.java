package com.example.mohea.aba_ep1_words.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohea.aba_ep1_words.Constant;
import com.example.mohea.aba_ep1_words.R;
import com.example.mohea.aba_ep1_words.RetryDialog;
import com.example.mohea.aba_ep1_words.WordParser;
import com.example.mohea.aba_ep1_words.adapter.ClientsListAdapter;
import com.example.mohea.aba_ep1_words.adapter.ScoreListAdapter;
import com.example.mohea.aba_ep1_words.listener.ScoreListClickListener;
import com.example.mohea.aba_ep1_words.set.ClientSet;
import com.example.mohea.aba_ep1_words.set.ScoreSet;
import com.example.mohea.aba_ep1_words.sql.SQLiteHelper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScoreListFragment extends Fragment {

    String name;
    long id;
    RecyclerView rvScoreList;

    public ScoreListFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_score_list,null);

        rvScoreList = view.findViewById(R.id.rv_score_list);
        rvScoreList.setAdapter(new ScoreListAdapter(getContext(),scoreListClickListener,id));
        rvScoreList.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rvScoreList.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));

        return view;
    }

    ScoreListClickListener scoreListClickListener = new ScoreListClickListener() {
        @Override
        public void scoreListClick(ScoreSet scoreSet) {
            new RetryDialog(getActivity()).show(scoreSet.getScoreId(),name);
        }

        @Override
        public void scoreListLongClick(final ScoreSet scoreSet) {

            new AlertDialog.Builder(getContext()).setTitle("기록 삭제").setMessage("해당 점수를 삭제하시겠습니까?")
                    .setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean result = new SQLiteHelper(getContext()).deleteScore(scoreSet.getScoreId());
                            if(result){
                                Toast.makeText(getContext(), name+"님의 해당 점수가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                rvScoreList.setAdapter(new ScoreListAdapter(getContext(),scoreListClickListener,id));
                            }else{
                                Toast.makeText(getContext(), "실패...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).create().show();
        }
    };

    public void setData(String name,long id){
        this.name = name;
        this.id = id;
    }

}
