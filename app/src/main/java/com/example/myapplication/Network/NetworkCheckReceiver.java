package com.example.myapplication.Network;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.myapplication.Utils.MethodUtils;

public class NetworkCheckReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        View rootView = ((Activity) context).getWindow().getDecorView();
        MethodUtils.checkInternet(context,rootView);
    }
}
