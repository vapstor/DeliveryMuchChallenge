package br.com.dmchallenge.repository;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import br.com.dmchallenge.model.Repo;
import br.com.dmchallenge.model.RepoResponse;
import br.com.dmchallenge.network.RepoApiService;
import br.com.dmchallenge.room.RepoDAO;
import br.com.dmchallenge.room.RepoRoomDatabase;
import io.reactivex.rxjava3.core.Observable;

public class Repository {

    private RepoDAO repoDAO;
    private RepoApiService apiService;

    @Inject
    public Repository(RepoDAO repoDAO, RepoApiService apiService) {
        this.repoDAO = repoDAO;
        this.apiService = apiService;
    }

    public Observable<RepoResponse> getRepos() {
        return apiService.getRepos();
    }

//    public void insertPokemon(Pokemon pokemon){
//        repoDAO.insertPokemon(pokemon);
//    }
//
//    public void deletePokemon(String pokemonName){
//        repoDAO.deletePokemon(pokemonName);
//    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    void insert(Repo repo) {
        RepoRoomDatabase.databaseWriteExecutor.execute(() -> repoDAO.insert(repo));
    }

    public void deleteAll() {
        repoDAO.deleteAll();
    }

    public LiveData<List<Repo>> getAlphabetizedRepos() {
        return repoDAO.getAlphabetizedRepos();
    }
}
