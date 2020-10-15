package br.com.dmchallenge.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;

@Entity(tableName = "user")
public class LoggedInUser {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String userId;

    @SerializedName("username")
    @ColumnInfo(name = "username")
    private String username;

    @SerializedName("name")
    @ColumnInfo(name = "display_name")
    private String displayName;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String email;

    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    private String avatarUrl;

    @ColumnInfo(name = "access_token")
    @TypeConverters(AccessToken.class)
    private AccessToken accessToken;

    @Inject
    public LoggedInUser(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public AccessToken getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(AccessToken accessToken) {
        this.accessToken = accessToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

}