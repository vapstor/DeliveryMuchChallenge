package br.com.dmchallenge.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import br.com.dmchallenge.adapter.RepoAdapter;
import br.com.dmchallenge.databinding.RepoFragmentBinding;
import br.com.dmchallenge.model.Repo;
import br.com.dmchallenge.utils.ItemClickSupport;
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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        repoViewModel = new ViewModelProvider(this).get(RepoViewModel.class);

        initRecyclerView();
        observeData();
        setUpItemTouchHelper();
    }

    private void setUpItemTouchHelper() {
        ItemClickSupport.addTo(binding.reposRecyclerView).setOnItemClickListener((recyclerView, position, v) -> {
            Toast.makeText(getContext(), "Elemento " + position + " Clidado!", Toast.LENGTH_SHORT).show();
        });
    }

    private void observeData() {
        repoViewModel.getReposList().observe(getViewLifecycleOwner(), repos -> {
            Log.e(TAG, "onChanged: " + repos.size());
            adapter.updateList(repos);
        });
    }

    private void initRecyclerView() {
        binding.reposRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RepoAdapter(getContext(), reposList);
        binding.reposRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}