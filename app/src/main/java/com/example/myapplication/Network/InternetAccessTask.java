package com.example.myapplication.Network;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetAddress;

public class InternetAccessTask extends AsyncTask<Void,Void,Boolean> {

    private InternetAccessCallBack callback;
    public InternetAccessTask(InternetAccessCallBack callback){
        this.callback = callback;
    }
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            // Using Google's public DNS server
            InetAddress address = InetAddress.getByName("8.8.8.8");
            return address.isReachable(5000); // Ping the address with a timeout of 5 seconds
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        if (callback != null) {
            callback.onInternetAccessResult(aBoolean);
        }
    }
}
