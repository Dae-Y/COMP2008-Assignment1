package com.example.connectfourgame;

import android.os.Bundle;
import android.view.Gravity;
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
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        gridLayout = view.findViewById(R.id.grid_layout);
        setupGrid();
        return view;
    }

    private void setupGrid() {
        int[] boardSize = viewModel.getGameBoardSize();
        if (boardSize != null) {
            int rows = boardSize[0];
            int cols = boardSize[1];

            gridLayout.setRowCount(rows);
            gridLayout.setColumnCount(cols);

            // Calculate button size based on GridLayout size
            int buttonSize = getResources().getDimensionPixelSize(R.dimen.button_size); // Define button size in dimens.xml

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    Button button = new Button(getContext());
                    GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                            GridLayout.spec(row, 1f), GridLayout.spec(col, 1f));
                    params.width = buttonSize;
                    params.height = buttonSize;
                    button.setLayoutParams(params);
                    button.setText(" ");
                    button.setGravity(Gravity.CENTER);
//                    button.setBackgroundResource(); // Set circular background

                    // Set button click listener
                    button.setOnClickListener(v -> {
                        // Handle button click
                    });

                    gridLayout.addView(button);
                }
            }
        }
    }
}
