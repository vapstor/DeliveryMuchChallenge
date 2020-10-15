package br.com.dmchallenge.network;

import br.com.dmchallenge.model.LoggedInUser;
import br.com.dmchallenge.model.RepoResponse;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("repositories")
    Observable<RepoResponse> getRepos();

    @GET("user")
    Observable<ResponseBody> getLoggedUserInfo();

    @GET("users/{username}")
    Observable<LoggedInUser> getUserInfoByUsername(@Path("username") String username);
}
