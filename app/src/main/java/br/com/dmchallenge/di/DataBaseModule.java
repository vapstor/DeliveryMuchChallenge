package br.com.dmchallenge.di;

import android.app.Application;

import androidx.room.Room;

import javax.inject.Singleton;

import br.com.dmchallenge.room.RepoDAO;
import br.com.dmchallenge.room.RepoRoomDatabase;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DataBaseModule {

    @Provides
    @Singleton
    public static RepoRoomDatabase provideRepoDB(Application application) {
        return Room.databaseBuilder(application, RepoRoomDatabase.class, "repo_database")
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public static RepoDAO providePokeDao(RepoRoomDatabase repoRoomDatabase) {
        return repoRoomDatabase.repoDAO();
    }
}


