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
import com.example.connectfourgame.PlayerData;
import com.example.connectfourgame.R;

import java.util.List;


public class SettingFragmentChangeGameMode extends Fragment {
    Button pveBtn;
    Button pvpBtn;

    private void changeBtnColor(MainActivityData viewModel){
        int activeProfile = viewModel.getActivePlayerProfile()[1];
        PlayerData temp = viewModel.getPlayerData(activeProfile);
        boolean vsBot = temp.getPlayerAI();

        pveBtn.setBackgroundColor(Color.parseColor("#6A7BBF"));
        pvpBtn.setBackgroundColor(Color.parseColor("#6A7BBF"));

        if(vsBot){
            pveBtn.setBackgroundColor(Color.parseColor("#3B4A8B"));
        }else{
            pvpBtn.setBackgroundColor(Color.parseColor("#3B4A8B"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_change_game_mode, container, false);

        MainActivityData viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

        pveBtn = view.findViewById(R.id.pveBtn);
        pvpBtn = view.findViewById(R.id.pvpBtn);

        viewModel.playerDataArr.observe(getViewLifecycleOwner(), new Observer<List<PlayerData>>() {
            @Override
            public void onChanged(List<PlayerData> playerData) {
                changeBtnColor(viewModel);
            }
        });

        viewModel.activePlayers.observe(getViewLifecycleOwner(), new Observer<int[]>() {
            @Override
            public void onChanged(int[] ints) {
                changeBtnColor(viewModel);
            }
        });

        pveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int activeProfile = viewModel.getActivePlayerProfile()[1];
                PlayerData tempPD = viewModel.getPlayerData(activeProfile);
                tempPD.setPlayerAI(true);
                viewModel.setPlayerData(activeProfile, tempPD);
            }
        });

        pvpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int activeProfile = viewModel.getActivePlayerProfile()[1];
                PlayerData tempPD = viewModel.getPlayerData(activeProfile);
                tempPD.setPlayerAI(false);
                viewModel.setPlayerData(activeProfile, tempPD);
            }
        });

        return view;
    }
}