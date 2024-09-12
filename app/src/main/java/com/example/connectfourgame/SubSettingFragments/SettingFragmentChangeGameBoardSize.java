package com.example.connectfourgame.SubSettingFragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.connectfourgame.MainActivityData;
import com.example.connectfourgame.R;

public class SettingFragmentChangeGameBoardSize extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_change_game_board_size, container, false);

        MainActivityData viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

        Button smallMapBtn = view.findViewById(R.id.changeSize_SBtn);
        Button mediumMapBtn = view.findViewById(R.id.changeSize_MBtn);
        Button largeMapBtn = view.findViewById(R.id.changeSize_LBtn);

        viewModel.gameBoardSize.observe(getViewLifecycleOwner(), new Observer<int[]>() {
            @Override
            public void onChanged(int[] ints) {
                int row, col;
                row = viewModel.getGameBoardSize()[0];
                col = viewModel.getGameBoardSize()[1];

                smallMapBtn.setBackgroundColor(Color.parseColor("#6A7BBF"));
                mediumMapBtn.setBackgroundColor(Color.parseColor("#6A7BBF"));
                largeMapBtn.setBackgroundColor(Color.parseColor("#6A7BBF"));

                if(row == 6 && col == 5){
                    smallMapBtn.setBackgroundColor(Color.parseColor("#3B4A8B"));
                }
                if(row == 7 && col == 6){
                    mediumMapBtn.setBackgroundColor(Color.parseColor("#3B4A8B"));
                }
                if(row == 8 && col == 7){
                    largeMapBtn.setBackgroundColor(Color.parseColor("#3B4A8B"));
                }
            }
        });

        smallMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setGameBoardSize(6, 5);
            }
        });

        mediumMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setGameBoardSize(7, 6);
            }
        });

        largeMapBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setGameBoardSize(8, 7);
            }
        });
        return view;
    }
}