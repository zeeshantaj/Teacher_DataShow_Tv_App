package com.example.myapplication.Connectivity_Check;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URL;

public class InternetAccessTask extends AsyncTask<Void,Void,Boolean> {

    private InternetAccessCallBack callback;
    public InternetAccessTask(InternetAccessCallBack callback){
        this.callback = callback;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            HttpURLConnection urlc = (HttpURLConnection) (new URL("https://www.google.com").openConnection());
            urlc.setRequestProperty("User-Agent", "Test");
            urlc.setRequestProperty("Connection", "close");
            urlc.setConnectTimeout(1500);
            urlc.connect();
            return (urlc.getResponseCode() == 200);
        } catch (IOException e) {
            Log.e("NetworkUtils", "Error checking internet connection", e);
        }
        return false;
//        try {
//            Socket socket = new Socket();
//            socket.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
//            socket.close();
//            return true;
//        } catch (IOException e) {
//            Log.e("NetworkUtils", "Error checking internet connection", e);
//            return false;
//        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (callback != null) {
            callback.onInternetAccessResult(aBoolean);
        }
    }
}
