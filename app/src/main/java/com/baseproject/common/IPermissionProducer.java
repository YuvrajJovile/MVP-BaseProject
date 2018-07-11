package com.baseproject.common;



public interface IPermissionProducer {
    void onReceivedPermissionStatus(int code, boolean isGranted);
}
