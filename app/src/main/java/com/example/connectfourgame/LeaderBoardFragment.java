package com.example.connectfourgame;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;


public class LeaderBoardFragment extends Fragment {

    private void playerTypeTextView(PlayerData playerData, TextView playerType){
        if(playerData.getPlayerAI()){
            playerType.setText("Player 2 [BOT]");
        }else {
            playerType.setText("Player 2");
        }
    }

    private void playerImageChange(PlayerData playerData, ImageView imageView){
        Drawable drawable;
        switch (playerData.getProfilePicture().toLowerCase()){
            case "human":
                drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.human, null);
                break;
            case "robot":
                drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.robot, null);
                break;
            case "dave":
                drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dave, null);
                break;
            case "cat":
                drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.cat, null);
                break;
            case "dog":
                drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dog, null);
                break;
            default:
                drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_launcher_background, null);
        }
        imageView.setImageDrawable(drawable);
    }

    private void playerScore(PlayerData playerData, TextView score){
        score.setText(String.valueOf("Score\n"+playerData.getPlayerWinAmount()));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivityData viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
        View view = inflater.inflate(R.layout.fragment_leader_board, container, false);

        ImageView player1ImageView = view.findViewById(R.id.player1Img);
        ImageView player2ImageView = view.findViewById(R.id.player2Img);
        ImageView settingImageView = view.findViewById(R.id.settingIcon);

        TextView player1Type = view.findViewById(R.id.player1Type);
        TextView player1Name = view.findViewById(R.id.player1Name);
        TextView player1Score = view.findViewById(R.id.player1Score);
        TextView player2Type = view.findViewById(R.id.player2Type);
        TextView player2Name = view.findViewById(R.id.player2Name);
        TextView player2Score = view.findViewById(R.id.player2Score);

        viewModel.playerDataArr.observe(getViewLifecycleOwner(), playerData -> {
            PlayerData p1 = viewModel.getPlayer1();
            PlayerData p2 = viewModel.getPlayer2();

            player1Name.setText(String.valueOf(p1.getPlayerName()));
            player2Name.setText(String.valueOf(p2.getPlayerName()));

            playerTypeTextView(p2, player2Type);
            playerImageChange(p1, player1ImageView);
            playerImageChange(p2, player2ImageView);
            playerScore(p1, player1Score);
            playerScore(p2, player2Score);
        });

        settingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomMenu bottomMenu = new BottomMenu();
                bottomMenu.show(requireActivity().getSupportFragmentManager(), bottomMenu.getTag());
            }
        });

        return view;
    }
}