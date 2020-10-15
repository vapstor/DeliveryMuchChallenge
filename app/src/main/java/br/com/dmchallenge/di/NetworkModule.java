package br.com.dmchallenge.di;

import androidx.lifecycle.MutableLiveData;

import javax.inject.Singleton;

import br.com.dmchallenge.network.ApiService;
import br.com.dmchallenge.network.AuthService;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static br.com.dmchallenge.utils.Constants.BASE_URL_API_GITHUB;
import static br.com.dmchallenge.utils.Constants.BASE_URL_GITHUB;

@Module
@InstallIn(ApplicationComponent.class)
public class NetworkModule {
    MutableLiveData<String> accessTokenLiveData = new MutableLiveData<>();

    @Provides
    public MutableLiveData<String> accessTokenLiveData() {
        return accessTokenLiveData;
    }

    @Provides
    @Singleton
    public static AuthService provideGithubAuthService() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_GITHUB)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(AuthService.class);
    }

    @Provides
    @Singleton
    public ApiService provideGithubApiService() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request.Builder ongoing = chain.request().newBuilder();
                    ongoing.addHeader("Accept", "application/json;");
                    if (getCacheAccessToken() != null) {
                        ongoing.addHeader("Authorization", "Bearer " + getCacheAccessToken());
                    }
                    return chain.proceed(ongoing.build());
                })
                .build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL_API_GITHUB)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Provides
    public String getCacheAccessToken() {
        if (accessTokenLiveData().getValue() != null && !accessTokenLiveData().getValue().equals("")) {
            return accessTokenLiveData.getValue();
        }
        return null;
    }
//
//    @Provides
//    public MutableLiveData<String> getAccessTokenLiveData() {
//        return accessTokenLiveData;
//    }
//
//    @Provides
//    public void setCacheAccessToken(String token) {
//        accessTokenLiveData.setValue(token);
//    }
}