package br.com.dmchallenge.utils;

import java.util.concurrent.TimeUnit;

abstract public class Constants {
    public static String CLIENT_ID = "25dd82cb116aebbe7a75";
    public static String CLIENT_SECRET = "84c6ae997ec541a421efa05ad4c1c8493ec3060f";
    public static String REDIRECT_URI = "dmchallenge://callback";
    public static long FRESH_TIMEOUT = TimeUnit.DAYS.toMillis(1);
    public static String BASE_URL_API_GITHUB = "https://api.github.com/";
    public static String BASE_URL_GITHUB = "https://github.com/";
    public static String MY_UNGUESSABLE_STRING = "Pellentesque";
    public static int REQUEST_GITHUB_LOGIN = 1;

}
