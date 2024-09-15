package com.example.connectfourgame;

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
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class GameFragment extends Fragment {

    private MainActivityData viewModel;
    private GridLayout gridLayout;
    private int turnCounter = 1;  // Start at 1 for Player 1's turn
    private boolean isBot;  // True = AI, False = Human Player
    private boolean gameFinished = false;  // Track if the game is over
    // Set to keep track of the buttons that have already been clicked
    private final Set<Button> occupiedSlots = new HashSet<>();

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
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int[] boardSize = viewModel.getGameBoardSize();
        int layoutId = getLayoutForBoardSize(boardSize);
        View view = inflater.inflate(layoutId, container, false);
        gridLayout = view.findViewById(R.id.grid_layout);
        setupSlotListeners(boardSize);
        return view;
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
                if(turnCounter == turnMax) {
                    Toast.makeText(getContext(), "Game finished. It's a draw.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (gameFinished) {
                    Toast.makeText(getContext(), "Game finished. Player " + ((turnCounter+1 % 2 == 0) ? "1" : "2") + " won.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int column = index % columnCount;
                int row = findAvailableRowInColumn(column);

                if (row == -1) {
                    Toast.makeText(getContext(), "Invalid move, Column is Full!", Toast.LENGTH_SHORT).show();
                } else {
                    Button selectedSlotButton = (Button) gridLayout.getChildAt(row * columnCount + column);

                    if (turnCounter % 2 != 0) {
                        setSlotColor(selectedSlotButton, R.color.gold);
                    } else {
                        setSlotColor(selectedSlotButton, R.color.red);
                    }

                    occupiedSlots.add(selectedSlotButton);

                    int playerNum = turnCounter % 2 == 0 ? 1 : 2;

                    // Check for a win after each move
                    if (isWinning(playerNum)) {
                        gameFinished = true;
                        Toast.makeText(getContext(), "Player " + playerNum + " wins!", Toast.LENGTH_LONG).show();
                        return;  // Stop further moves
                    }

                    turnCounter++;

                    if (isBot && turnCounter % 2 == 0) {
                        handleAIMoveWithDelay();
                    }
                }
            });
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

            setSlotColor(aiChosenSlot, R.color.red);  // AI's color
            occupiedSlots.add(aiChosenSlot);

            if (isWinning(2)) {
                gameFinished = true;
                Toast.makeText(getContext(), "Player 2 (AI) wins!", Toast.LENGTH_LONG).show();
            }
            else{
                turnCounter++;
            }
        }
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
        int rows = gridLayout.getRowCount();
        int cols = gridLayout.getColumnCount();
        int[][] board = new int[rows][cols];

        boolean isWin = false;

        // Fill the board array with current state
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                Button slotButton = (Button) gridLayout.getChildAt(row * cols + col);
                if (isSlotOccupied(slotButton)) {
                    GradientDrawable background = (GradientDrawable) slotButton.getBackground();
                    int color = background.getColor().getDefaultColor();
                    if (color == getResources().getColor(R.color.gold)) {
                        board[row][col] = 1;  // Player 1's color
                    } else if (color == getResources().getColor(R.color.red)) {
                        board[row][col] = 2;  // Player 2's (or AI's) color
                    } else {
                        board[row][col] = 0;
                    }
                } else {
                    board[row][col] = 0;
                }
            }
        }

        // Check for a win for the specified player
        isWin = checkHorizontalWin(board, pNum) || isWin;
        isWin = checkVerticalWin(board, pNum) || isWin;
        isWin = checkDiagonalWin(board, pNum) || isWin;

        return isWin;
    }

    // Check for horizontal 4-in-a-row for a specific player
    private boolean checkHorizontalWin(int[][] board, int pNum) {
        boolean isWin = false;
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length - 3; col++) {
                if (board[row][col] == pNum &&
                        board[row][col + 1] == pNum &&
                        board[row][col + 2] == pNum &&
                        board[row][col + 3] == pNum) {
                    isWin = true;
                    break;
                }
            }
            if (isWin) break;
        }
        return isWin;
    }

    // Check for vertical 4-in-a-row for a specific player
    private boolean checkVerticalWin(int[][] board, int pNum) {
        boolean isWin = false;
        for (int col = 0; col < board[0].length; col++) {
            for (int row = 0; row < board.length - 3; row++) {
                if (board[row][col] == pNum &&
                        board[row + 1][col] == pNum &&
                        board[row + 2][col] == pNum &&
                        board[row + 3][col] == pNum) {
                    isWin = true;
                    break;
                }
            }
            if (isWin) break;
        }
        return isWin;
    }

    // Check for diagonal 4-in-a-row for a specific player (both directions)
    private boolean checkDiagonalWin(int[][] board, int pNum) {
        boolean isWin = false;

        // Check for \ diagonal
        for (int row = 0; row < board.length - 3; row++) {
            for (int col = 0; col < board[0].length - 3; col++) {
                if (board[row][col] == pNum &&
                        board[row + 1][col + 1] == pNum &&
                        board[row + 2][col + 2] == pNum &&
                        board[row + 3][col + 3] == pNum) {
                    isWin = true;
                    break;
                }
            }
            if (isWin) break;
        }

        if (!isWin) {
            // Check for / diagonal
            for (int row = 3; row < board.length; row++) {
                for (int col = 0; col < board[0].length - 3; col++) {
                    if (board[row][col] == pNum &&
                            board[row - 1][col + 1] == pNum &&
                            board[row - 2][col + 2] == pNum &&
                            board[row - 3][col + 3] == pNum) {
                        isWin = true;
                        break;
                    }
                }
                if (isWin) break;
            }
        }

        return isWin;
    }









} // END GAME FRAGMENT