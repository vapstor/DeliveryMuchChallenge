package br.com.dmchallenge.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import br.com.dmchallenge.room.MyRoomDatabase;
import br.com.dmchallenge.room.RepoDAO;
import br.com.dmchallenge.room.UserDAO;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {

    @Provides
    @Singleton
    public static MyRoomDatabase provideAppDB(Application application) {
        return MyRoomDatabase.getDatabase(application.getApplicationContext());
    }

    @Provides
    @Singleton
    public static RepoDAO provideRepoDao(MyRoomDatabase myRoomDatabase) {
        return myRoomDatabase.repoDAO();
    }

    @Provides
    @Singleton
    public static UserDAO provideUserDao(MyRoomDatabase myRoomDatabase) {
        return myRoomDatabase.userDAO();
    }
}


