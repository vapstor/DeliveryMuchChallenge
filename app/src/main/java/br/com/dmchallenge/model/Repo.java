package br.com.dmchallenge.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "repo_table")
public class Repo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "owner_id")
    private String ownerId;
    @NonNull
    @ColumnInfo(name = "owner_avatar")
    private String ownerAvatar;
    @NonNull
    @ColumnInfo(name = "owner_name")
    private String ownerName;
    @NonNull
    @ColumnInfo(name = "repo_name")
    private String repoName;
    @ColumnInfo(name = "repo_description")
    private String repoDescription;

    public Repo(@NonNull String ownerId, @NonNull String ownerAvatar, @NonNull String ownerName, @NonNull String repoName, String repoDescription) {
        this.ownerAvatar = ownerAvatar;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.repoName = repoName;
        this.repoDescription = repoDescription;
    }

    @NonNull
    public String getOwnerId() {
        return ownerId;
    }

    @NonNull
    public String getOwnerName() {
        return ownerName;
    }

    @NonNull
    public String getRepoName() {
        return repoName;
    }

    public String getRepoDescription() {
        return repoDescription;
    }

    @NonNull
    public String getOwnerAvatar() {
        return this.ownerAvatar;
    }


}
