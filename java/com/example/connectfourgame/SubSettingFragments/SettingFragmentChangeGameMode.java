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


public class SettingFragmentChangeGameMode extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_change_game_mode, container, false);

        MainActivityData viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

        Button pveBtn = view.findViewById(R.id.pveBtn);
        Button pvpBtn = view.findViewById(R.id.pvpBtn);



        viewModel.playerDataArr.observe(getViewLifecycleOwner(), new Observer<PlayerData[]>() {
            @Override
            public void onChanged(PlayerData[] playerData) {
                PlayerData temp = viewModel.getPlayerData(1);
                boolean vsBot = temp.getPlayerAI();

                pveBtn.setBackgroundColor(Color.parseColor("#6A7BBF"));
                pvpBtn.setBackgroundColor(Color.parseColor("#6A7BBF"));

                if(vsBot){
                    pveBtn.setBackgroundColor(Color.parseColor("#3B4A8B"));
                }else{
                    pvpBtn.setBackgroundColor(Color.parseColor("#3B4A8B"));
                }
            }
        });

        pveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerData tempPD = viewModel.getPlayerData(1);
                tempPD.setPlayerAI(true);
                viewModel.setPlayerData(1, tempPD);
            }
        });

        pvpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayerData tempPD = viewModel.getPlayerData(1);
                tempPD.setPlayerAI(false);
                viewModel.setPlayerData(1, tempPD);
            }
        });

        return view;
    }
}