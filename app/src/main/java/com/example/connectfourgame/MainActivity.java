package com.example.connectfourgame;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class MainActivity extends AppCompatActivity {
    MainActivityData mainActivityDataViewModel;
    StartFragment startFragment = new StartFragment();
    GameFragment gameFragment = new GameFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    StatisticsFragment statsFragment = new StatisticsFragment();

    FrameLayout topFrameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainActivityDataViewModel = new ViewModelProvider(this).get(MainActivityData.class);

        topFrameLayout = findViewById(R.id.topFrameLayout);

        mainActivityDataViewModel.fragmentState.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(mainActivityDataViewModel.getFragmentState() == 0){
                    topFrameLayout.setVisibility(View.GONE);
                    loadStartFragment();
                }
                if(mainActivityDataViewModel.getFragmentState() == 1){
                    topFrameLayout.setVisibility(View.VISIBLE);
                    //TODO: Levan's part for the top Layout
                    loadGameBoard();
                }
                if(mainActivityDataViewModel.getFragmentState() == 2){
                    topFrameLayout.setVisibility(View.GONE);
                    loadStatisticsFragment();
                }
                if(mainActivityDataViewModel.getFragmentState() == 3){
                    topFrameLayout.setVisibility(View.GONE);
                    loadSettingFragment();
                }
            }
        });
    }

    private void loadStartFragment(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.bottomFrameLayout);
        if(frag == null){
            fm.beginTransaction().add(R.id.bottomFrameLayout, startFragment).commit();
        }else{
            fm.beginTransaction().replace(R.id.bottomFrameLayout, startFragment).commit();
        }
    }

    private void loadGameBoard(){
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.bottomFrameLayout);
        if (frag == null) {
            fm.beginTransaction().add(R.id.bottomFrameLayout, gameFragment).commit();
        } else {
            fm.beginTransaction().replace(R.id.bottomFrameLayout, gameFragment).commit();
        }
    }

    private void loadSettingFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.bottomFrameLayout);
        if(frag == null){
            fm.beginTransaction().add(R.id.bottomFrameLayout, settingsFragment).commit();
        }else{
            fm.beginTransaction().replace(R.id.bottomFrameLayout, settingsFragment).commit();
        }
    }

    private void loadStatisticsFragment() {
        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.bottomFrameLayout);
        if(frag == null){
            fm.beginTransaction().add(R.id.bottomFrameLayout, statsFragment).commit();
        }else{
            fm.beginTransaction().replace(R.id.bottomFrameLayout, statsFragment).commit();
        }
    }
}