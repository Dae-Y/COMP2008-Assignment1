package com.example.connectfourgame.SubSettingFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import com.example.connectfourgame.MainActivityData;
import com.example.connectfourgame.PlayerData;
import com.example.connectfourgame.R;

public class SettingFragmentChangePlayerProfile extends Fragment {
    int playerNumber;
    PlayerData playerProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_change_player_profile, container, false);
        MainActivityData viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
        EditText playerName = view.findViewById(R.id.playerName);
        Spinner iconSpinner = view.findViewById(R.id.playerIconSpinner);
        Spinner colorSpinner = view.findViewById(R.id.playerColorSpinner);

        Button saveBtn = view.findViewById(R.id.saveBtn);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> colorSpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.playerColorChoices, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> iconSpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.playerIconChoices, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        colorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        iconSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        colorSpinner.setAdapter(colorSpinnerAdapter);
        iconSpinner.setAdapter(iconSpinnerAdapter);




        Bundle args = getArguments();
        if(args!=null){
            String player = args.getString("player_key");

            viewModel.playerDataArr.observe(getViewLifecycleOwner(), new Observer<PlayerData[]>() {
                @Override
                public void onChanged(PlayerData[] playerData) {
                    if(player.equals("p1")){
                        playerNumber = 0;
                        playerProfile = viewModel.getPlayerData(playerNumber);
                    }
                    if(player.equals("p2")){
                        playerNumber = 1;
                        playerProfile = viewModel.getPlayerData(playerNumber);
                    }
                    playerName.setText(playerProfile.getPlayerName());
                    int colorSpinnerPos = colorSpinnerAdapter.getPosition(playerProfile.getPlayerColour());
                    int iconSpinnerPos = iconSpinnerAdapter.getPosition(playerProfile.getProfilePicture());
                    colorSpinner.setSelection(colorSpinnerPos);
                    iconSpinner.setSelection(iconSpinnerPos);
                }
            });

            saveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    playerProfile.setPlayerName(String.valueOf(playerName.getText()));
                    playerProfile.setPlayerColour(String.valueOf(colorSpinner.getSelectedItem()));
                    playerProfile.setProfilePicture(String.valueOf(iconSpinner.getSelectedItem()));

                    viewModel.setPlayerData(playerNumber, playerProfile);
                }
            });
        }

        return view;
    }
}