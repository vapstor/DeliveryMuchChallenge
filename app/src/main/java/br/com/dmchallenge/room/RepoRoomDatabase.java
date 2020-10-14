package br.com.dmchallenge.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import br.com.dmchallenge.model.Repo;

@Database(entities = {Repo.class}, version = 1, exportSchema = false)
public abstract class RepoRoomDatabase extends RoomDatabase {


    public abstract RepoDAO repoDAO();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile RepoRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RepoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RepoRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RepoRoomDatabase.class, "repo_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     * <p>
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                RepoDAO dao = INSTANCE.repoDAO();
                dao.deleteAll();

                Repo repoExample = new Repo("1", "Hello", "Hello", "Hello", "Hello");
                dao.insert(repoExample);
                Repo otherRepoExample = new Repo("2", "Word", "Word", "Word", "Word");
                dao.insert(otherRepoExample);
            });
        }
    };
}
