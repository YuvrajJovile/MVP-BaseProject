package com.baseproject.reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.baseproject.library.Log;

public class NetworkReceiver extends BroadcastReceiver {

    public interface OnNetWorkChangeLister {
        public void networkChanged(boolean isOn);
    }

    private static OnNetWorkChangeLister mListener;

    public static void setListener(OnNetWorkChangeLister listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.CONNECTED) {
                Log.d("Network", "Internet ");
                if (mListener != null)
                    mListener.networkChanged(true);
            } else if (networkInfo != null && networkInfo.getDetailedState() == NetworkInfo.DetailedState.DISCONNECTED) {
                Log.d("Network", "No internet :(");
                if (mListener != null)
                    mListener.networkChanged(false);
            }
        }

    }

}