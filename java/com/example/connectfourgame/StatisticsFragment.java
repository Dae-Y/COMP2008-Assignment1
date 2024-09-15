package com.example.connectfourgame;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple Statistics Fragment to illustrate the game statistics.
 * Use the factory method to
 * create an instance of this fragment.
 * Author: Minh
 */
public class StatisticsFragment extends Fragment {

    private View view;
    private MainActivityData viewModel;

    private TextView player1NameText, player2NameText;
    private TextView player1WinsText, player2WinsText;
    private TextView player1LossesText, player2LossesText;
    private TextView player1WinPercentageText, player2WinPercentageText;
    private TextView totalGamesText;

    public Button backBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

        initializeViews();
        setListeners();
        updateStatistics();

        return view;
    }

    private void initializeViews() {
        player1NameText = view.findViewById(R.id.player1NameText);
        player2NameText = view.findViewById(R.id.player2NameText);
        player1WinsText = view.findViewById(R.id.player1WinsText);
        player2WinsText = view.findViewById(R.id.player2WinsText);
        player1LossesText = view.findViewById(R.id.player1LossesText);
        player2LossesText = view.findViewById(R.id.player2LossesText);
        player1WinPercentageText = view.findViewById(R.id.player1WinPercentageText);
        player2WinPercentageText = view.findViewById(R.id.player2WinPercentageText);
        totalGamesText = view.findViewById(R.id.totalGamesText);
        backBtn = view.findViewById(R.id.backBtn);
    }

    private void setListeners() {
        backBtn.setOnClickListener(view -> viewModel.setFragmentState(0));
    }

    private void updateStatistics() {
        PlayerData player1 = viewModel.getPlayerData(0);
        PlayerData player2 = viewModel.getPlayerData(1);

        player1NameText.setText(player1.getPlayerName());
        player2NameText.setText(player2.getPlayerName());

        int player1Wins = viewModel.getPlayer1Wins();
        int player2Wins = viewModel.getPlayer2Wins();
        int totalGames = viewModel.getTotalGames();

        int player1Losses = totalGames - player1Wins;
        int player2Losses = totalGames - player2Wins;

        player1WinsText.setText(String.valueOf(player1Wins));
        player2WinsText.setText(String.valueOf(player2Wins));
        player1LossesText.setText(String.valueOf(player1Losses));
        player2LossesText.setText(String.valueOf(player2Losses));
        totalGamesText.setText(String.valueOf(totalGames));

        float player1WinPercentage = totalGames > 0 ? (float) player1Wins / totalGames * 100 : 0;
        float player2WinPercentage = totalGames > 0 ? (float) player2Wins / totalGames * 100 : 0;

        player1WinPercentageText.setText(String.format("%.1f%%", player1WinPercentage));
        player2WinPercentageText.setText(String.format("%.1f%%", player2WinPercentage));

        player1NameText.setBackgroundColor(Color.parseColor(player1.getPlayerColour()));
        player2NameText.setBackgroundColor(Color.parseColor(player2.getPlayerColour()));
    }
}