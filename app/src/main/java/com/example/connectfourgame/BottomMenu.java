package com.example.connectfourgame;

import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class BottomMenu extends BottomSheetDialogFragment {
    View view;
    BottomSheetBehavior<View> bottomSheetBehavior;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_bottom_menu, container, false);
        MainActivityData viewModel = new ViewModelProvider(requireActivity()).get(MainActivityData.class);
        Button resetGameBtn = view.findViewById(R.id.resetBtn);
        Button backBtn = view.findViewById(R.id.backBtn);

        backBtn.setOnClickListener(view -> {
            viewModel.setFragmentState(0);
            dismiss();
        });

        resetGameBtn.setOnClickListener(view1 -> {

        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomSheetBehavior = BottomSheetBehavior.from((View) view.getParent());

        CoordinatorLayout layout = view.findViewById(R.id.bottomMenuLayout);
    }
}