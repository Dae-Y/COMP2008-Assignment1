package com.example.connectfourgame;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 *  MainActivityData is used to extend to the viewModel with LiveData with the suitable
 *      variables
 *  This allows the communication of data between fragments [Prac04].
 *  .
 *  To pass liveData between Activity we can use Intent, like how we did
 *      on prac03
 **/

public class MainActivityData extends ViewModel {
    public MutableLiveData<List<PlayerData>> playerDataArr;
    public MutableLiveData<int[]> gameBoardSize;
    public MutableLiveData<Integer> fragmentState;
    public MutableLiveData<int[]> activePlayers;

    public MainActivityData() {
        playerDataArr = new MutableLiveData<>();
        gameBoardSize = new MutableLiveData<>();
        fragmentState = new MutableLiveData<>();
        activePlayers = new MutableLiveData<>();

        // Initialize with 2 PlayerData objects in the array
        List<PlayerData> initialPlayerData = new ArrayList<>();
        initialPlayerData.add(new PlayerData("Player_1", "Red", "Human", 0, false));
        initialPlayerData.add(new PlayerData("Player_2", "Blue", "Robot", 0, true));
        playerDataArr.setValue(initialPlayerData);

        // Initialize gameBoardSize with an array of size 2
        gameBoardSize.setValue(new int[]{6, 5});
        fragmentState.setValue(0);
        activePlayers.setValue(new int[]{0, 1});
    }

    public PlayerData getPlayerData(int index) {
        List<PlayerData> data = playerDataArr.getValue();
        if (data != null && index >= 0 && index < data.size()) {
            return data.get(index);
        } else {
            throw new IndexOutOfBoundsException("Invalid index for getting player data");
        }
    }

    // Getters added for readability
    public PlayerData getPlayer1() {
        return getPlayerData(getActivePlayerProfile()[0]);  // Player 1 is at index 0
    }

    public PlayerData getPlayer2() {
        return getPlayerData(getActivePlayerProfile()[1]);  // Player 2 is at index 1
    }


    public List<PlayerData> getPlayerArr(){ return  playerDataArr.getValue();}

    public int[] getGameBoardSize() {
        return gameBoardSize.getValue();
    }

    public int getFragmentState(){
        return fragmentState.getValue();
    }

    public int[] getActivePlayerProfile(){ return  activePlayers.getValue();}

    public int getPlayerProfileCount() {
        List<PlayerData> tempArr = playerDataArr.getValue();
        if(tempArr != null){
            return tempArr.size();
        }
        return 0;
    }

    public void setPlayerData(int index, PlayerData playerData) {
        List<PlayerData> currentData = playerDataArr.getValue();
        if (currentData != null && index >= 0 && index < currentData.size()) {
            currentData.set(index,playerData);
            playerDataArr.setValue(currentData);
        } else {
            throw new IndexOutOfBoundsException("Invalid index for setting player data");
        }
    }

    public void setGameBoardSize(int row, int col) {
        gameBoardSize.setValue(new int[]{row, col});
    }

    public void setFragmentState(int state){
        fragmentState.setValue(state);
    }

    public void setActivePlayers(int p1, int p2){
        activePlayers.setValue(new int[]{p1, p2});
    }

    public void addProfile(PlayerData newPlayerProfile){
        List<PlayerData> currentData = playerDataArr.getValue();
        if(currentData != null){
            currentData.add(newPlayerProfile);
            playerDataArr.setValue(currentData);
        }
    }

}

