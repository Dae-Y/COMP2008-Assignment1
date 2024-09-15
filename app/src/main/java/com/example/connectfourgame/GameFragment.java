package com.example.connectfourgame;

import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class GameFragment extends Fragment {

    private MainActivityData viewModel;
    private GridLayout gridLayout;
    private int turnCounter = 1;  // Start at 1 for Player 1's turn
    private boolean isBot;  // True = AI, False = Human Player
    private boolean gameFinished = false;  // Track if the game is over
    private boolean gameDraw = false;
    private final Set<Button> occupiedSlots = new HashSet<>();
    private int[][] p1Arr, p2Arr;  // Arrays to track the game board for Player 1 and Player 2
    private int winner = -1;  // Track the winner, -1 means no winner
    private int player1Color, player2Color; // Global player color

    public GameFragment() {
        // Required empty public constructor
    }

    public static GameFragment newInstance() {
        return new GameFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
        isBot = viewModel.getPlayer2().getPlayerAI();  // Check if Player 2 is AI

        // Initialize the board tracking arrays
        int[] boardSize = viewModel.getGameBoardSize();
        p1Arr = new int[boardSize[0]][boardSize[1]];
        p2Arr = new int[boardSize[0]][boardSize[1]];
        viewModel.setP1Arr(p1Arr);
        viewModel.setP2Arr(p2Arr);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int[] boardSize = viewModel.getGameBoardSize();
        int layoutId = getLayoutForBoardSize(boardSize);
        View view = inflater.inflate(layoutId, container, false);
        gridLayout = view.findViewById(R.id.grid_layout);

        // Getting the active player 1 and 2 color preference.
        player1Color = getPlayerColor(viewModel.getPlayer1().getPlayerColour());
        player2Color = getPlayerColor(viewModel.getPlayer2().getPlayerColour());

        setupSlotListeners(boardSize);
        return view;
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        p1Arr = viewModel.getP1Arr();
        p2Arr = viewModel.getP2Arr();
    }

    // Helper method to select the correct layout based on board size
    private int getLayoutForBoardSize(int[] boardSize) {
        int rows = boardSize[0];
        int cols = boardSize[1];
        if (rows == 6 && cols == 5) {
            return R.layout.fragment_game_small;  // Use 6x5 board
        } else if (rows == 7 && cols == 6) {
            return R.layout.fragment_game_med;    // Use 7x6 board
        } else {
            return R.layout.fragment_game_large;  // Use 8x6 board
        }
    }

    private void setupSlotListeners(int[] boardSize) {
        final int columnCount = gridLayout.getColumnCount();
        final int rows = boardSize[0];
        final int cols = boardSize[1];
        final int turnMax = rows * cols; // maximum number of turns the game can get
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final int index = i;
            final Button slotButton = (Button) gridLayout.getChildAt(i);

            slotButton.setOnClickListener(v -> {
                if (gameDraw) {
                    Toast.makeText(getContext(), "Game finished. It's a draw.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (gameFinished) {
                    Toast.makeText(getContext(), "Game finished. Player " + winner + " won.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int column = index % columnCount;
                int row = findAvailableRowInColumn(column);

                if (row == -1) {
                    Toast.makeText(getContext(), "Invalid move, Column is Full!", Toast.LENGTH_SHORT).show();
                } else {
                    Button selectedSlotButton = (Button) gridLayout.getChildAt(row * columnCount + column);

                    if (turnCounter % 2 != 0) {  // Player 1's turn
                        setSlotColor(selectedSlotButton, player1Color);
                        p1Arr[row][column] = 1;  // Mark Player 1's move in p1Arr
                        viewModel.p1Arr = p1Arr;
                    } else {  // Player 2's turn (AI or Human)
                        setSlotColor(selectedSlotButton, player2Color);
                        p2Arr[row][column] = 1;  // Mark Player 2's move in p2Arr
                        viewModel.p2Arr = p2Arr;
                    }

                    occupiedSlots.add(selectedSlotButton);

                    int playerNum = turnCounter % 2 != 0 ? 1 : 2;  // Determine which player made the move


                    // Check for a win after each move
                    if (isWinning(playerNum)) {
                        gameFinished = true;
                        winner = playerNum;  // Store the winner
                        Toast.makeText(getContext(), "Player " + playerNum + " wins!", Toast.LENGTH_LONG).show();

                        if(playerNum == 1){
                            viewModel.getPlayer1().addPlayerWinAmount();
                        }else{
                            viewModel.getPlayer2().addPlayerWinAmount();
                        }
                        return;  // Stop further moves
                    }

                    turnCounter++;

                    if (isBot && turnCounter % 2 == 0) {
                        handleAIMoveWithDelay();
                    }

                    if (turnCounter == turnMax+1) {
                        Toast.makeText(getContext(), "It's a draw!.", Toast.LENGTH_SHORT).show();
                        gameDraw = true;
                    }
                }
            });
        }
    }

    private void handleAIMoveWithDelay() {
        int delay = new Random().nextInt(500) + 500;  // Random delay 0.5-1 seconds
        new Handler().postDelayed(this::handleAIMove, delay);
    }

    private void handleAIMove() {
        List<Integer> availableColumns = new ArrayList<>();
        int columns = gridLayout.getColumnCount();

        for (int col = 0; col < columns; col++) {
            if (findAvailableRowInColumn(col) != -1) {
                availableColumns.add(col);
            }
        }

        if (!availableColumns.isEmpty()) {
            int randomColumn = availableColumns.get(new Random().nextInt(availableColumns.size()));
            int availableRow = findAvailableRowInColumn(randomColumn);
            Button aiChosenSlot = (Button) gridLayout.getChildAt(availableRow * gridLayout.getColumnCount() + randomColumn);

            setSlotColor(aiChosenSlot, player2Color);  // AI's color
            p2Arr[availableRow][randomColumn] = 1;  // Mark AI's move in p2Arr
            occupiedSlots.add(aiChosenSlot);

            if (isWinning(2)) {
                gameFinished = true;
                Toast.makeText(getContext(), "Player 2 (AI) wins!", Toast.LENGTH_LONG).show();
                winner = 2;
            } else {
                turnCounter++;
            }
        }
    }

    // Find the lowest available row in a specific column
    private int findAvailableRowInColumn(int column) {
        int rows = gridLayout.getRowCount();
        for (int row = rows - 1; row >= 0; row--) {
            Button slotButton = (Button) gridLayout.getChildAt(row * gridLayout.getColumnCount() + column);
            if (!isSlotOccupied(slotButton)) {
                return row;
            }
        }
        return -1;  // Column full
    }

    private boolean isSlotOccupied(Button slotButton) {
        return occupiedSlots.contains(slotButton);
    }

    private void setSlotColor(Button slotButton, int colorResId) {
        GradientDrawable background = (GradientDrawable) slotButton.getBackground();
        background.setColor(getResources().getColor(colorResId));
    }

    // Check if a player has won
    private boolean isWinning(int pNum) {
        int[][] board = pNum == 1 ? p1Arr : p2Arr;
        endGame(pNum);

        // Check horizontal, vertical, and diagonal wins
        return (checkHorizontalWin(board) || checkVerticalWin(board) || checkDiagonalWin(board));
    }

    private boolean checkHorizontalWin(int[][] board) {
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length - 3; col++) {
                if (board[row][col] == 1 && board[row][col + 1] == 1 &&
                        board[row][col + 2] == 1 && board[row][col + 3] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkVerticalWin(int[][] board) {
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row < board.length - 3; row++) {
                if (board[row][col] == 1 && board[row + 1][col] == 1 &&
                        board[row + 2][col] == 1 && board[row + 3][col] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean checkDiagonalWin(int[][] board) {
        // Check \ diagonal (bottom-left to top-right)
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == 1 && board[row + 1][col + 1] == 1 &&
                        board[row + 2][col + 2] == 1 && board[row + 3][col + 3] == 1) {
                    return true;
                }
            }
        }

        // Check / diagonal (top-left to bottom-right)
        for (int row = 3; row < board.length; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == 1 && board[row - 1][col + 1] == 1 &&
                        board[row - 2][col + 2] == 1 && board[row - 3][col + 3] == 1) {
                    return true;
                }
            }
        }

        return false;
    }

    private void endGame(int wonPlayer) {
        gameFinished = true;
        winner = wonPlayer;
        viewModel.increaseTotalGames();

        if (wonPlayer == 1) {
            viewModel.increasePlayerWins(viewModel.getActivePlayerProfile()[0]);
        } else if (wonPlayer == 2) {
            viewModel.increasePlayerWins(viewModel.getActivePlayerProfile()[1]);
        }

        Toast.makeText(getContext(), "Player " + wonPlayer + " wins!", Toast.LENGTH_LONG).show();
    }

    //Assign color code according to the player's color
    private int getPlayerColor(String playerColor) {
        int colorTemp;
        switch (playerColor){
            case "Red":
                colorTemp = R.color.red;
                break;
            case "Blue":
                colorTemp = R.color.blue;
                break;
            case "Gold":
                colorTemp = R.color.gold;
                break;
            case "Green":
                colorTemp = R.color.green;
                break;
            case "Orange":
                colorTemp = R.color.orange;
                break;
        default:
            colorTemp = R.color.default_slot_color;
        }
        return colorTemp;
    }

}
