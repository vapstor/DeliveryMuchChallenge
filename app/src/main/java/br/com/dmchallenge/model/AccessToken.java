package br.com.dmchallenge.model;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import javax.inject.Inject;


public class AccessToken {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;

    @Inject
    AccessToken() {
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }



    //Necessário para converter o objeto em uma lista de objetos e armazenar na base de dados
    @TypeConverter
    public static AccessToken storedStringToMyObjects(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return null;
        }
        Type listType = new TypeToken<AccessToken>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String myObjectsToStoredString(AccessToken myAccessToken) {
        Gson gson = new Gson();
        return gson.toJson(myAccessToken);
    }

}
