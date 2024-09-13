package com.example.connectfourgame;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.connectfourgame.SubSettingFragments.SettingFragmentChangeGameBoardSize;
import com.example.connectfourgame.SubSettingFragments.SettingFragmentChangeGameMode;
import com.example.connectfourgame.SubSettingFragments.SettingFragmentChangePlayerProfile;

/**
 *  Fragment for setting
 *  Author : ACG
 */
public class SettingsFragment extends Fragment {
    LinearLayout playerProfileLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        MainActivityData viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

        Button changeGameBoardSizeBtn = view.findViewById(R.id.gameBoardChangeBtn);
        Button changeGameModeBtn = view.findViewById(R.id.changeGameModeBtn);
        Button changePlayerProfileBtn = view.findViewById(R.id.changePlayerProfileBtn);

        Button player1Btn = view.findViewById(R.id.player1Btn);
        Button player2Btn = view.findViewById(R.id.player2Btn);
        player1Btn.setBackgroundColor(Color.parseColor("#6A7BBF"));
        player2Btn.setBackgroundColor(Color.parseColor("#6A7BBF"));

        Button backBtn = view.findViewById(R.id.backBtn);

        FrameLayout changeMapSizeFrameLayout = view.findViewById(R.id.gameBoardFrameLayout);
        FrameLayout gameModeFrameLayout = view.findViewById(R.id.gameModeFrameLayout);
        FrameLayout player1FrameLayout = view.findViewById(R.id.player1FrameLayout);
        FrameLayout player2FrameLayout = view.findViewById(R.id.player2FrameLayout);

        playerProfileLinearLayout = view.findViewById(R.id.changePlayerProfileLinearLayout);

        /* Output Name */
        viewModel.playerDataArr.observe(getViewLifecycleOwner(), new Observer<PlayerData[]>() {
            @Override
            public void onChanged(PlayerData[] playerData) {
                PlayerData temp = viewModel.getPlayerData(0);
                player1Btn.setText(String.valueOf(temp.getPlayerName() + " [Player_1]"));
                temp = viewModel.getPlayerData(1);
                if(temp.getPlayerAI()){
                    player2Btn.setText(String.valueOf(temp.getPlayerName() + " [Player_2[BOT]]"));
                }else{
                    player2Btn.setText(String.valueOf(temp.getPlayerName() + " [Player_2]"));
                }
            }
        });

        /* SetOnClickListener group */
        changeGameBoardSizeBtn.setOnClickListener(view16 -> {
            changeMapSizeFrameLayout.setVisibility(View.VISIBLE);
            gameModeFrameLayout.setVisibility(View.GONE);
            playerProfileLinearLayout.setVisibility(View.GONE);
            gameBoardFrameLayout();
        });

        changeGameModeBtn.setOnClickListener(view15 -> {
            gameModeFrameLayout.setVisibility(View.VISIBLE);
            playerProfileLinearLayout.setVisibility(View.GONE);
            changeMapSizeFrameLayout.setVisibility(View.GONE);
            gameModeFrameLayout();
        });

        changePlayerProfileBtn.setOnClickListener(view14 -> {
            playerProfileLinearLayout.setVisibility(View.VISIBLE);
            gameModeFrameLayout.setVisibility(View.GONE);
            changeMapSizeFrameLayout.setVisibility(View.GONE);
        });

        player1Btn.setOnClickListener(view1 -> {
            player1FrameLayout.setVisibility(View.VISIBLE);
            player2FrameLayout.setVisibility(View.GONE);
            playerProfileFrameLayout("p1", 1);
            player1Btn.setBackgroundColor(Color.parseColor("#3B4A8B"));
            player2Btn.setBackgroundColor(Color.parseColor("#6A7BBF"));
        });

        player2Btn.setOnClickListener(view12 -> {
            player1FrameLayout.setVisibility(View.GONE);
            player2FrameLayout.setVisibility(View.VISIBLE);
            playerProfileFrameLayout("p2", 2);
            player2Btn.setBackgroundColor(Color.parseColor("#3B4A8B"));
            player1Btn.setBackgroundColor(Color.parseColor("#6A7BBF"));
        });

        backBtn.setOnClickListener(view13 -> viewModel.setFragmentState(0));

        return view;
    }

    private void gameBoardFrameLayout(){
        Fragment newFragment = new SettingFragmentChangeGameBoardSize();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.gameBoardFrameLayout, newFragment);
        transaction.commit();
    }

    private void gameModeFrameLayout(){
        Fragment newFragment = new SettingFragmentChangeGameMode();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.gameModeFrameLayout, newFragment);
        transaction.commit();
    }

    private void playerProfileFrameLayout(String player, int playerNumber){
        Fragment newFragment = new SettingFragmentChangePlayerProfile();
        Bundle bundle = new Bundle();
        String frameLayoutId = "player" + playerNumber + "FrameLayout";
        int res = getResources().getIdentifier(frameLayoutId, "id", getContext().getPackageName());

        bundle.putString("player_key", player);
        newFragment.setArguments(bundle);
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();

        transaction.add(res, newFragment);
        transaction.commit();
    }
}