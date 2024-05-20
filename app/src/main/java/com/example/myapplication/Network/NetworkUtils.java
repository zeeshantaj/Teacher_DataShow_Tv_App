package com.example.myapplication.Network;

public class NetworkUtils {
    public static void hasInternetAccess(InternetAccessCallBack callback) {
        new InternetAccessTask(callback).execute();
    }
}

