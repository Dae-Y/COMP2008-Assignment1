package com.example.connectfourgame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
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

        Button backBtn = view.findViewById(R.id.backBtn);

        FrameLayout changeMapSizeFrameLayout = view.findViewById(R.id.gameBoardFrameLayout);
        FrameLayout gameModeFrameLayout = view.findViewById(R.id.gameModeFrameLayout);
        FrameLayout playerProfileFrameLayout = view.findViewById(R.id.playerProfileFrameLayout);

        changeGameBoardSizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeMapSizeFrameLayout.setVisibility(View.VISIBLE);
                gameModeFrameLayout.setVisibility(View.GONE);
                playerProfileFrameLayout.setVisibility(View.GONE);
                gameBoardFrameLayout();
            }
        });

        changeGameModeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameModeFrameLayout.setVisibility(View.VISIBLE);
                playerProfileFrameLayout.setVisibility(View.GONE);
                changeMapSizeFrameLayout.setVisibility(View.GONE);
                gameModeFrameLayout();
            }
        });

        changePlayerProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerProfileFrameLayout.setVisibility(View.VISIBLE);
                gameModeFrameLayout.setVisibility(View.GONE);
                changeMapSizeFrameLayout.setVisibility(View.GONE);
                playerProfileFrameLayout();
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

    private void playerProfileFrameLayout(){
        Fragment newFragment = new SettingFragmentChangePlayerProfile();
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.add(R.id.playerProfileFrameLayout, newFragment);
        transaction.commit();
    }
}