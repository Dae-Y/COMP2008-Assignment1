package com.example.connectfourgame;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.*;

import java.util.List;

public class StatisticsFragment extends Fragment {

    private MainActivityData viewModel;
    private TextView totalGamesTextView;
    private RecyclerView playerStatsRecyclerView;
    private PlayerStatsAdapter playerStatsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        totalGamesTextView = view.findViewById(R.id.totalGamesPlayed);
        playerStatsRecyclerView = view.findViewById(R.id.playerStatsRecyclerView);
        Button backButton = view.findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> viewModel.setFragmentState(0)); // Go back to StartFragment

        setupRecyclerView();
        updateStatistics();

        return view;
    }

    private void setupRecyclerView() {
        playerStatsAdapter = new PlayerStatsAdapter();
        playerStatsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        playerStatsRecyclerView.setAdapter(playerStatsAdapter);
    }

    private void updateStatistics() {
        int totalGames = viewModel.getTotalGames();
        totalGamesTextView.setText("Total Games Played: " + totalGames);

        List<PlayerData> players = viewModel.getPlayerArr();
        playerStatsAdapter.setPlayers(players, totalGames);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateStatistics(); // Refresh statistics when fragment resumes
    }

    private static class PlayerStatsAdapter extends RecyclerView.Adapter<PlayerStatsAdapter.PlayerStatsView> {

        private List<PlayerData> players;
        private int totalGames;

        public void setPlayers(List<PlayerData> players, int totalGames) {
            this.players = players;
            this.totalGames = totalGames;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public PlayerStatsView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_stat_box, parent, false);
            return new PlayerStatsView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlayerStatsView holder, int position) {
            PlayerData player = players.get(position);
            holder.addInfo(player, totalGames);
        }

        @Override
        public int getItemCount() {
            return players != null ? players.size() : 0;
        }

        static class PlayerStatsView extends RecyclerView.ViewHolder {
            TextView playerNameTextView, winsTextView, lossesTextView, winPercentageTextView;

            public PlayerStatsView(@NonNull View itemView) {
                super(itemView);
                playerNameTextView = itemView.findViewById(R.id.playerName);
                winsTextView = itemView.findViewById(R.id.wins);
                lossesTextView = itemView.findViewById(R.id.losses);
                winPercentageTextView = itemView.findViewById(R.id.winPercentage);
            }

            public void addInfo(PlayerData player, int totalGames) {
                playerNameTextView.setText(player.getPlayerName());
                int wins = player.getPlayerWinAmount();
                int losses = totalGames - wins;
                double winPercentage = totalGames > 0 ? (double) wins / totalGames * 100 : 0;

                winsTextView.setText("Wins: " + wins);
                lossesTextView.setText("Losses: " + losses);
                winPercentageTextView.setText(String.format("Win %%: %.1f%%", winPercentage));
            }
        }
    }
}
