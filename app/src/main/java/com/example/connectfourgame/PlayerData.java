package com.example.connectfourgame;

/**
 *  playerName, Name of user [By default player_1 and player_2]
 *  playerColour, Colour of player
 *  profilePicture, Picture of player
 *  playerWinAmount, keep track of player win amount
 *  playerAI, Differentiate between, Human[false] and AI[true]
 **/
public class PlayerData {
    private String playerName;
    private String playerColour;
    private String profilePicture;
    private int playerWinAmount;
    private boolean playerAI;

    public PlayerData(String pName, String pColour, String pPicture, int pWinNum, boolean playerAI){
        playerName = pName;
        playerColour = pColour;
        profilePicture =  pPicture;
        playerWinAmount = pWinNum;
        this.playerAI = playerAI;
    }

    //Getters and setters
    public String getPlayerName() {
        return playerName;
    }
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerColour() {
        return playerColour;
    }
    public void setPlayerColour(String playerColour) {
        this.playerColour = playerColour;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getPlayerWinAmount() {
        return playerWinAmount;
    }
    public void setPlayerWinAmount(int playerWinAmount) {
        this.playerWinAmount = playerWinAmount;
    }
    public void addPlayerWinAmount(){this.playerWinAmount+=1;}

    public boolean getPlayerAI() {
        return playerAI;
    }
    public void setPlayerAI(boolean playerAI) {
        this.playerAI = playerAI;
    }

}
