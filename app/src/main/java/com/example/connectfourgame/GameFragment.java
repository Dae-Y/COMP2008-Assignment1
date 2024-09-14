package com.example.connectfourgame;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
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
        // Initialize ViewModel
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);

        // Retrieve whether Player 1 is playing against AI or another human
        isBot = viewModel.getPlayer2().getPlayerAI();  // Assuming you have this in the ViewModel
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        int[] boardSize = viewModel.getGameBoardSize();
        int layoutId = getLayoutForBoardSize(boardSize);  // Determine which layout to use

        // Inflate the correct layout
        View view = inflater.inflate(layoutId, container, false);

        gridLayout = view.findViewById(R.id.grid_layout);
        setupSlotListeners();  // Call the method to handle clicks
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

    private void setupSlotListeners() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final Button slotButton = (Button) gridLayout.getChildAt(i);
            slotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isSlotOccupied(slotButton)) {
                        if (turnCounter % 2 != 0) {  // Player 1 (always human)
                            setSlotColor(slotButton, R.color.gold);  // Player 1's color
                            occupiedSlots.add(slotButton);
                            turnCounter++;

                            if (isBot) {
                                handleAIMove();  // Call AI after Player 1's move if playing against AI
                            }
                        } else if (!isBot) {  // Player 2 (if not playing against AI)
                            setSlotColor(slotButton, R.color.red);  // Player 2's color
                            occupiedSlots.add(slotButton);
                            turnCounter++;
                        }
                    }
                }
            });
        }
    }

    private void handleAIMove() {
        List<Button> availableSlots = new ArrayList<>();

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button slotButton = (Button) gridLayout.getChildAt(i);
            if (!isSlotOccupied(slotButton)) {
                availableSlots.add(slotButton);  // Add to available list
            }
        }

        if (!availableSlots.isEmpty()) {
            // AI randomly picks an available slot
            int randomIndex = new Random().nextInt(availableSlots.size());
            Button aiChosenSlot = availableSlots.get(randomIndex);

            setSlotColor(aiChosenSlot, R.color.red);  // AI's color
            occupiedSlots.add(aiChosenSlot);  // Mark the slot as occupied
            turnCounter++;
        }
    }

    private boolean isSlotOccupied(Button slotButton) {
        return occupiedSlots.contains(slotButton);
    }

    private void setSlotColor(Button slotButton, int colorResId) {
        GradientDrawable background = (GradientDrawable) slotButton.getBackground();
        background.setColor(getResources().getColor(colorResId));
    }
}
