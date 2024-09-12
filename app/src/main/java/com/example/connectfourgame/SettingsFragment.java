package com.example.connectfourgame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 *  Fragment for setting
 *  Author : ACG
 */
public class SettingsFragment extends Fragment {
    private LinearLayout changeGameBoardSize_LL;
    private LinearLayout changeGameMode_LL;
    private LinearLayout changePlayerProfile_LL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        MainActivityData viewModel;
        Button changeGameBoardSizeBtn;
        Button changeGameModeBtn;
        Button changePlayerProfileBtn;

        Button backBtn;

        view = inflater.inflate(R.layout.fragment_settings, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

        changeGameBoardSizeBtn = view.findViewById(R.id.gameBoardChangeBtn);
        changeGameModeBtn = view.findViewById(R.id.changeGameModeBtn);
        changePlayerProfileBtn = view.findViewById(R.id.changePlayerProfileBtn);

        backBtn = view.findViewById(R.id.backBtn);

        changeGameBoardSize_LL = view.findViewById(R.id.gameBoardChangeLinearLayout);
        changeGameMode_LL = view.findViewById(R.id.changeGameModeLinearLayout);
        changePlayerProfile_LL = view.findViewById(R.id.changePlayerProfileLinearLayout);

        changeGameBoardSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeGameBoardSize_LL.setVisibility(View.VISIBLE);
                changeGameMode_LL.setVisibility(View.GONE);
                changePlayerProfile_LL.setVisibility(View.GONE);
            }
        });

        changeGameModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeGameMode_LL.setVisibility(View.VISIBLE);
                changeGameBoardSize_LL.setVisibility(View.GONE);
                changePlayerProfile_LL.setVisibility(View.GONE);
            }
        });

        changePlayerProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePlayerProfile_LL.setVisibility(View.VISIBLE);
                changeGameMode_LL.setVisibility(View.GONE);
                changeGameBoardSize_LL.setVisibility(View.GONE);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setFragmentState(0);
            }
        });

        return view;
    }
}