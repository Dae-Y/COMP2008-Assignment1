package com.example.connectfourgame.SubSettingFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.connectfourgame.MainActivityData;
import com.example.connectfourgame.PlayerData;
import com.example.connectfourgame.R;

import java.util.ArrayList;
import java.util.List;

public class SettingFragmentChangePlayerProfile extends Fragment {
    int playerNumber = 0;
    PlayerData playerProfile;
    String player;

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
        Spinner playerProfileSpinner = view.findViewById(R.id.playerProfileSpinner);
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
            player = args.getString("player_key");

            viewModel.playerDataArr.observe(getViewLifecycleOwner(), new Observer<List<PlayerData>>() {
                @Override
                public void onChanged(List<PlayerData> playerData) {
                    List<String> nameList = new ArrayList<>();
                    int i = 0;
                    for (PlayerData tempPd :
                            viewModel.getPlayerArr()) {
                        nameList.add(i+ " : "+tempPd.getPlayerName());
                        i++;
                    }
                    ArrayAdapter<String> playerProfileAdapter = new ArrayAdapter<>(getContext(),
                            android.R.layout.simple_spinner_item, nameList);
                    playerProfileAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    playerProfileSpinner.setAdapter(playerProfileAdapter);


                    if(player.equals("p1")){
                        playerNumber = viewModel.getActivePlayerProfile()[0];
                        playerProfile = viewModel.getPlayerData(playerNumber);
                    }
                    if(player.equals("p2")){
                        playerNumber = viewModel.getActivePlayerProfile()[1];
                        playerProfile = viewModel.getPlayerData(playerNumber);
                    }
                    if (player.equals("np")){
                        playerNumber = viewModel.getPlayerProfileCount();
                        playerProfile = new PlayerData("newPlayer", "Red", "Human", 0, false);
                        playerProfileSpinner.setVisibility(View.GONE);
                        saveBtn.setText("Add Profile");
                    }else{
                        playerProfileSpinner.setVisibility(View.VISIBLE);
                        saveBtn.setText("Save");
                    }

                    playerProfileSpinner.setSelection(playerNumber);
                    playerName.setText(playerProfile.getPlayerName());
                    int colorSpinnerPos = colorSpinnerAdapter.getPosition(playerProfile.getPlayerColour());
                    int iconSpinnerPos = iconSpinnerAdapter.getPosition(playerProfile.getProfilePicture());
                    colorSpinner.setSelection(colorSpinnerPos);
                    iconSpinner.setSelection(iconSpinnerPos);
                }
            });

            viewModel.activePlayers.observe(getViewLifecycleOwner(), new Observer<int[]>() {
                @Override
                public void onChanged(int[] ints) {
                    if(player.equals("p1")){
                        playerNumber = viewModel.getActivePlayerProfile()[0];
                        playerProfile = viewModel.getPlayerData(playerNumber);
                        playerProfile.setPlayerAI(false);
                    }
                    if(player.equals("p2")){
                        playerNumber = viewModel.getActivePlayerProfile()[1];
                        playerProfile = viewModel.getPlayerData(playerNumber);
                    }

                    playerName.setText(playerProfile.getPlayerName());
                    int colorSpinnerPos = colorSpinnerAdapter.getPosition(playerProfile.getPlayerColour());
                    int iconSpinnerPos = iconSpinnerAdapter.getPosition(playerProfile.getProfilePicture());
                    colorSpinner.setSelection(colorSpinnerPos);
                    iconSpinner.setSelection(iconSpinnerPos);
                }
            });

            saveBtn.setOnClickListener(view1 -> {
                playerProfile.setPlayerName(String.valueOf(playerName.getText()));
                playerProfile.setPlayerColour(String.valueOf(colorSpinner.getSelectedItem()));
                playerProfile.setProfilePicture(String.valueOf(iconSpinner.getSelectedItem()));

                if(player.equals("np")){
                    viewModel.addProfile(playerProfile);
                    Toast.makeText(getContext(), "New player profile added!", Toast.LENGTH_SHORT).show();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().remove(this).commit();
                    super.onDestroy();
                }else{
                    viewModel.setPlayerData(playerNumber, playerProfile);
                }
            });
        }

        playerProfileSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedItem = adapterView.getItemAtPosition(i).toString();
                String numberBeforeColon = selectedItem.split(" : ")[0];
                int playerId = Integer.parseInt(numberBeforeColon);

                if(Integer.parseInt(numberBeforeColon) != viewModel.getActivePlayerProfile()[0] &&
                        Integer.parseInt(numberBeforeColon) != viewModel.getActivePlayerProfile()[1]){
                    int[] tempCurrentPlayers = viewModel.getActivePlayerProfile();

                    if(player.equals("p1")){
                        tempCurrentPlayers[0] = playerId;
                        viewModel.setActivePlayers(tempCurrentPlayers[0], tempCurrentPlayers[1]);
                    }
                    if(player.equals("p2")){
                        tempCurrentPlayers[1] = playerId;
                        viewModel.setActivePlayers(tempCurrentPlayers[0], tempCurrentPlayers[1]);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return view;
    }
}