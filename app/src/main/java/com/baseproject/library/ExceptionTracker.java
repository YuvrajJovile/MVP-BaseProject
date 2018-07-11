package com.baseproject.library;

/**
 * Created by guru on 28/04/2016.
 */
public class ExceptionTracker {

    public static void track(Exception exception) {
        //Crashlytics.logException(exception);
        exception.printStackTrace();
    }

    public static void track(String message) {
        //Crashlytics.log(message);
        //Log.e();
    }
}