package br.com.dmchallenge.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import br.com.dmchallenge.model.LoggedInUser;
import br.com.dmchallenge.model.Repo;

@Dao
public interface UserDAO {

    // LiveData is a data holder class that can be observed within a given lifecycle.
    // Always holds/caches latest version of data. Notifies its active observers when the
    // data has changed. Since we are getting all the contents of the database,
    // we are notified whenever any of the database contents have changed.
    @Query("SELECT * FROM user")
    LiveData<LoggedInUser> getLoggedUser();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LoggedInUser user);


}