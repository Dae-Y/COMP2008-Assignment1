package com.example.connectfourgame;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 *  MainActivityData is used to extend to the viewModel with LiveData with the suitable
 *      variables
 *  This allows the communication of data between fragments [Prac04].
 *  .
 *  To pass liveData between Activity we can use Intent, like how we did
 *      on prac03
 **/

public class MainActivityData extends ViewModel {
    public MutableLiveData<PlayerData[]> playerDataArr;
    public MutableLiveData<int[]> gameBoardSize;
    public MutableLiveData<Integer> fragmentState;

    public MainActivityData() {
        playerDataArr = new MutableLiveData<>();
        gameBoardSize = new MutableLiveData<>();
        fragmentState = new MutableLiveData<>();

        // Initialize with 2 PlayerData objects in the array
        PlayerData[] initialPlayerData = new PlayerData[2];
        initialPlayerData[0] = new PlayerData("Player_1", "Red", "Human", 0, false);
        initialPlayerData[1] = new PlayerData("Player_2", "Blue", "Robot", 0, true);
        playerDataArr.setValue(initialPlayerData);

        // Initialize gameBoardSize with an array of size 2
        gameBoardSize.setValue(new int[]{6, 5});
        fragmentState.setValue(0);
    }

    public PlayerData getPlayerData(int index) {
        PlayerData[] data = playerDataArr.getValue();
        if (data != null && index >= 0 && index < data.length) {
            return data[index];
        } else {
            throw new IndexOutOfBoundsException("Invalid index for getting player data");
        }
    }

    public int[] getGameBoardSize() {
        return gameBoardSize.getValue();
    }

    public int getFragmentState(){
        return fragmentState.getValue();
    }

    public void setPlayerData(int index, PlayerData playerData) {
        PlayerData[] currentData = playerDataArr.getValue();
        if (currentData != null && index >= 0 && index < currentData.length) {
            currentData[index] = playerData;
            playerDataArr.setValue(currentData); // Trigger update
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
}

