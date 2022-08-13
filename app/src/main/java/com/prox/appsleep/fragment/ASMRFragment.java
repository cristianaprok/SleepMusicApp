package com.prox.appsleep.fragment;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.prox.appsleep.R;
import com.prox.appsleep.adapter.ItemCategoryAdpater;
import com.prox.appsleep.database.dbCategoryIconModel.CategoryIconDatabase;
import com.prox.appsleep.database.dbCategoryIconModel.CategoryIconViewModel;
import com.prox.appsleep.itemClick.OnItemClickListener;
import com.prox.appsleep.model.CategoryIconModel;
import com.prox.appsleep.service.PlayMusicService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ASMRFragment extends Fragment implements OnItemClickListener {

    public RecyclerView rcvItemRain;
    private ItemCategoryAdpater adpater;
    public List<CategoryIconModel> categoryIconModelList;
    private CategoryIconModel categoryIconModel;

    public ImageView ivResume;
    public NestedScrollView bottomRemote;
    private BottomSheetBehavior bottomSheetBehavior;

    private PlayMusicService playMusicService;
    private boolean isServiceConnected;

    public LinearLayout bgCategory;

    public CategoryIconViewModel categoryIconViewModel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asmr, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvItemRain = view.findViewById(R.id.rcvItemASMR);
        bottomRemote = view.findViewById(R.id.bottom_sheet_remote);
        bgCategory = view.findViewById(R.id.bgCategory);
        categoryIconModelList = new ArrayList<>();

        adpater = new ItemCategoryAdpater(categoryIconModelList, this);

        rcvItemRain.setAdapter(adpater);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        rcvItemRain.setLayoutManager(gridLayoutManager);

        categoryIconViewModel = new ViewModelProvider(requireActivity()).get(CategoryIconViewModel.class);
        categoryIconViewModel.getCategoryIconASMR().observe(requireActivity(), new Observer<List<CategoryIconModel>>() {
            @Override
            public void onChanged(List<CategoryIconModel> categoryIconModelList) {
                adpater.setCategoryIconModel(categoryIconModelList);
            }
        });
    }

    private void playMusic(CategoryIconModel categoryIconModel) {
        Intent intent = new Intent(getActivity(), PlayMusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("listSong", categoryIconModel);
        intent.putExtras(bundle);
        requireActivity().startService(intent);
    }

    @Override
    public void itemClickListener(CategoryIconModel categoryIconModel) {
        playMusic(categoryIconModel);
    }
}
