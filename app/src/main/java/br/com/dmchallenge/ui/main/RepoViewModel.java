package br.com.dmchallenge.ui.main;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import br.com.dmchallenge.model.Repo;
import br.com.dmchallenge.repository.Repository;

public class RepoViewModel extends ViewModel {
    private static final String TAG = "RepoViewModel";

    private Repository repository;
    private MutableLiveData<ArrayList<Repo>> reposList = new MutableLiveData<>();
    //private LiveData<List<Repo>> reposList = null;

    @ViewModelInject
    public RepoViewModel(Repository repository) {
        this.repository = repository;
//        reposList = repository.getAlphabetizedRepos();
    }

    public MutableLiveData<ArrayList<Repo>> getReposList() {
        return reposList;
    }

}