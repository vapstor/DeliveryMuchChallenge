package br.com.dmchallenge.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import br.com.dmchallenge.adapter.RepoAdapter;
import br.com.dmchallenge.databinding.RepoFragmentBinding;
import br.com.dmchallenge.model.Repo;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RepoFragment extends Fragment {
    private final String TAG = "RepoFragment";

    private RepoViewModel repoViewModel;
    private RepoFragmentBinding binding;
    private RepoAdapter adapter;
    private ArrayList<Repo> reposList;

    public static RepoFragment newInstance() {
        return new RepoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = RepoFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        repoViewModel = new ViewModelProvider(this).get(RepoViewModel.class);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void observeData() {
        repoViewModel.getReposList().observe(getViewLifecycleOwner(), pokemons -> {
            Log.e(TAG, "onChanged: " + pokemons.size());
            adapter.updateList(pokemons);
        });
    }

    private void initRecyclerView() {
        binding.reposRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RepoAdapter(getContext(), reposList);
        binding.reposRecyclerView.setAdapter(adapter);
    }

}