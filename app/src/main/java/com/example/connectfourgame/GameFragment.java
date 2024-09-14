package com.example.connectfourgame;

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

            // Calculate button size based only on width (since it's more limiting in portrait)
            int totalWidth = getResources().getDisplayMetrics().widthPixels - (16 * 2);  // Adjust for margin
            int buttonSize = totalWidth / cols; // Ensure all buttons fit within width

            // Set grid layout height based on button size and rows
            ViewGroup.LayoutParams layoutParams = gridLayout.getLayoutParams();
            layoutParams.height = buttonSize * rows; // Set height to fit all buttons
            gridLayout.setLayoutParams(layoutParams);

            // Clear previous buttons
            gridLayout.removeAllViews();

            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    Button button = new Button(getContext());
                    button.setBackgroundResource(R.drawable.circular_slot); // Circular slot

                    GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                    params.width = buttonSize;
                    params.height = buttonSize;

                    params.rowSpec = GridLayout.spec(row);
                    params.columnSpec = GridLayout.spec(col);

                    params.setMargins(4, 4, 4, 4); // Margins between slots

                    button.setLayoutParams(params);

                    gridLayout.addView(button);
                }
            }
        }
    }

}
