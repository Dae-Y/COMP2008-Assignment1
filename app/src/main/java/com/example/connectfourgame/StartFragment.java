package com.example.connectfourgame;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 *  StartFragment will be the first thing display when opening the app,
 *  .
 *  It helps to bridge the connection between the game, statistic and settings
 **/
public class StartFragment extends Fragment {
    private View view;
    private MainActivityData viewModel;

    private Button startGameBtn;
    private Button statisticBtn;
    private Button settingBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_start, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

        startGameBtn = view.findViewById(R.id.startGameBtn);
        statisticBtn = view.findViewById(R.id.statisticBtn);
        settingBtn = view.findViewById(R.id.settingsBtn);


        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "clicked and fragmentState change to 1 for startGame", Toast.LENGTH_LONG).show();
                viewModel.setFragmentState(1);
            }
        });

        return view;
    }
}