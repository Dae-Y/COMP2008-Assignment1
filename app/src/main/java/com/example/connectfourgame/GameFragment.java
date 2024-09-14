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

public class GameFragment extends Fragment {

    private MainActivityData viewModel;
    private GridLayout gridLayout;

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
        if (boardSize == null || boardSize.length != 2) {
            throw new IllegalArgumentException("Invalid board size");
        }

        int rows = boardSize[0];
        int cols = boardSize[1];

        if (rows == 6 && cols == 5) {
            return R.layout.fragment_game_small;  // Use 6x5 board
        } else if (rows == 7 && cols == 6) {
            return R.layout.fragment_game_med;    // Use 7x6 board
        } else if (rows == 8 && cols == 7) {
            return R.layout.fragment_game_large;  // Use 8x6 board
        } else {
            throw new IllegalArgumentException("Unsupported board size");
        }
    }

    private void setupSlotListeners() {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            Button slotButton = (Button) gridLayout.getChildAt(i);
            slotButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Change the color of the clicked slot
                    GradientDrawable background = (GradientDrawable) slotButton.getBackground();
                    background.setColor(getResources().getColor(R.color.clicked_slot_color));  // Set to your desired color
                }
            });
        }
    }
}
