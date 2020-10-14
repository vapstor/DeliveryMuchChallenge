package br.com.dmchallenge.network;

import br.com.dmchallenge.model.RepoResponse;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface RepoApiService {

    @GET("repositories")
    Observable<RepoResponse> getRepos();

}
